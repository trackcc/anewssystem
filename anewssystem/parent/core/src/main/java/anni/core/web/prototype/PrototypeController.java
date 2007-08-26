package anni.core.web.prototype;


/*
 * Copyright 2002-2006 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.BeanUtils;

import org.springframework.util.Assert;

import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.LastModified;
import org.springframework.web.servlet.mvc.multiaction.InternalPathMethodNameResolver;
import org.springframework.web.servlet.mvc.multiaction.MethodNameResolver;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;
import org.springframework.web.util.NestedServletException;


/**
 * Controller implementation that allows multiple request types to be
 * handled by the same class. Subclasses of this class can handle several
 * different types of request with methods of the form
 *
 * <pre>
 * (ModelAndView | Map | void) actionName(HttpServletRequest request, HttpServletResponse response);</pre>
 *
 * May take a third parameter HttpSession in which an existing session will be required,
 * or a third parameter of an arbitrary class that gets treated as command
 * (i.e. an instance of the class gets created, and request parameters get bound to it)
 *
 * <p>These methods can throw any kind of exception, but should only let propagate
 * those that they consider fatal, or which their class or superclass is prepared to
 * catch by implementing an exception handler.
 *
 * <p>When returning just a {@link Map} instance view name translation will be used to generate
 * the view name. The configured {@link org.springframework.web.servlet.RequestToViewNameTranslator}
 * will be used to determine the view name.
 *
 * <p>When returning <code>void</code> a return value of <code>null</code> is assumed
 * meaning that the handler method is responsible for writing the response directly to
 * the supplied {@link HttpServletResponse}.
 *
 * <p>This model allows for rapid coding, but loses the advantage of compile-time
 * checking. It is similar to a Struts 1.1 DispatchAction, but more sophisticated.
 * Also supports delegation to another object.
 *
 * <p>An implementation of the MethodNameResolver interface defined in this package
 * should return a method name for a given request, based on any aspect of the request,
 * such as its URL or an "action" parameter. The actual strategy can be configured
 * via the "methodNameResolver" bean property, for each MultiActionController.
 *
 * <p>The default MethodNameResolver is InternalPathMethodNameResolver; further included
 * strategies are PropertiesMethodNameResolver and ParameterMethodNameResolver.
 *
 * <p>Subclasses can implement custom exception handler methods with names such as:
 *
 * <pre>
 * ModelAndView anyMeaningfulName(HttpServletRequest request, HttpServletResponse response, ExceptionClass exception);
 * </pre>
 *
 * The third parameter can be any subclass or Exception or RuntimeException.
 *
 * <p>There can also be an optional lastModified method for handlers, of signature:
 *
 * <pre>
 * long anyMeaningfulNameLastModified(HttpServletRequest request)</pre>
 *
 * If such a method is present, it will be invoked. Default return from getLastModified
 * is -1, meaning that the content must always be regenerated.
 *
 * <p>Note that method overloading isn't allowed.
 *
 * <p>See also the description of the workflow performed by
 * {@link AbstractController the superclass} (in that section of the class
 * level Javadoc entitled 'workflow').
 *
 * <p><b>Note:</b> For maximum data binding flexibility, consider direct usage
 * of a ServletRequestDataBinder in your controller method, instead of relying
 * on a declared command argument. This allows for full control over the entire
 * binder setup and usage, including the invocation of Validators and the
 * subsequent evaluation of binding/validation errors.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Colin Sampaleanu
 * @author Rob Harrop
 * @author Lingo
 * @since 2007-04-07
 * @see MethodNameResolver
 * @see InternalPathMethodNameResolver
 * @see PropertiesMethodNameResolver
 * @see ParameterMethodNameResolver
 * @see org.springframework.web.servlet.mvc.LastModified#getLastModified
 * @see org.springframework.web.bind.ServletRequestDataBinder
 */
public class PrototypeController extends AbstractController
    implements LastModified {
    /**
     * logger.
     */
    private static Log logger = LogFactory.getLog(PrototypeController.class);

    /** Suffix for last-modified methods. */
    public static final String LAST_MODIFIED_METHOD_SUFFIX = "LastModified";

    /** Default command name used for binding command objects: "command". */
    public static final String DEFAULT_COMMAND_NAME = "command";

    /** Log category to use when no mapped handler is found for a request. */
    public static final String PAGE_NOT_FOUND_LOG_CATEGORY = "org.springframework.web.servlet.PageNotFound";

    /** Additional logger to use when no mapped handler is found for a request. */
    private static Log pageNotFoundLogger = LogFactory.getLog(PAGE_NOT_FOUND_LOG_CATEGORY);

    /**
     * 请求.
     */
    protected HttpServletRequest request;

    /**
     * 响应.
     */
    protected HttpServletResponse response;

    /**
     * 会话.
     */
    protected HttpSession session;

    /**
     * ServletContext.
     */
    protected ServletContext context;

    /**
     * ModelAndView.
     */
    protected ModelAndView mv;

    /**
     * Helper object that knows how to return method names from incoming requests.
     * Can be overridden via the methodNameResolver bean property.
     */
    private MethodNameResolver methodNameResolver = new InternalPathMethodNameResolver();

    /** List of Validators to apply to commands. */
    private Validator[] validators;

    /** Object we'll invoke methods on. Defaults to this. */
    private Object delegate;

    /** Methods, keyed by name. */
    private Map handlerMethodMap = new HashMap();

    /** LastModified methods, keyed by handler method name (without LAST_MODIFIED_SUFFIX). */
    private Map lastModifiedMethodMap = new HashMap();

    /** Methods, keyed by exception class. */
    private Map exceptionHandlerMap = new HashMap();

    /**
     * Constructor for MultiActionController that looks for handler methods
     * in the present subclass. Caches methods for quick invocation later.
     * This class's use of reflection will impose little overhead at runtime.
     */
    public PrototypeController() {
        this.delegate = this;
        registerHandlerMethods(this.delegate);

        // We'll accept no handler methods found here - a delegate might be set later on.
    }

    /**
     * Constructor for MultiActionController that looks for handler methods in delegate,
     * rather than a subclass of this class. Caches methods for quick invocation later.
     * @param delegateIn handler object. This doesn't need to implement any particular
     * interface, as everything is done using reflection.
     */
    public PrototypeController(Object delegateIn) {
        setDelegate(delegateIn);
    }

    /**
     * @see MultiActionController#handleRequest.
     * @param requestIn request
     * @param responseIn respnonseIn
     * @return ModelAndView mv
     * @throws Exception 异常
     */
    @Override
    protected ModelAndView handleRequestInternal(
        HttpServletRequest requestIn, HttpServletResponse responseIn)
        throws Exception {
        request = requestIn;
        response = responseIn;
        session = request.getSession();
        context = session.getServletContext();
        mv = new ModelAndView();

        try {
            String methodName = this.methodNameResolver
                .getHandlerMethodName(request);
            invokeNamedMethod(methodName);

            return mv;
        } catch (NoSuchRequestHandlingMethodException ex) {
            return handleNoSuchRequestHandlingMethod(ex, request, response);
        }
    }

    /**
     * Invokes the named method.
     * <p>Uses a custom exception handler if possible; otherwise, throw an
     * unchecked exception; wrap a checked exception or Throwable.
     *
     * @param methodName 方法名
     * @throws Exception 异常
     */
    protected final void invokeNamedMethod(String methodName)
        throws Exception {
        Method method = (Method) this.handlerMethodMap.get(methodName);

        if (method == null) {
            throw new NoSuchRequestHandlingMethodException(methodName,
                getClass());
        }

        try {
            method.invoke(this.delegate, new Object[0]);
        } catch (InvocationTargetException ex) {
            logger.error("error occur when invoke : " + method,
                ex.getCause());
            // The handler method threw an exception.
            this.mv = handleException(request, response,
                    ex.getTargetException());
        } catch (Exception ex) {
            logger.error("other exception : ", ex);
            // The binding process threw an exception.
            this.mv = handleException(request, response, ex);
        }
    }

    /**
     * Set the method name resolver that this class should use.
     * Allows parameterization of handler method mappings.
     *
     * @param methodNameResolverIn 方法名处理器
     */
    public final void setMethodNameResolver(
        MethodNameResolver methodNameResolverIn) {
        this.methodNameResolver = methodNameResolverIn;
    }

    /**
     * Return the MethodNameResolver used by this class.
     *
     * @return MethodNameResovler 方法名处理器
     */
    public final MethodNameResolver getMethodNameResolver() {
        return this.methodNameResolver;
    }

    /**
     * Set the Validators for this controller.
     * The Validator must support the specified command class.
     *
     * @param validatorsIn Validator
     */
    public final void setValidators(Validator[] validatorsIn) {
        this.validators = validatorsIn;
    }

    /**
     * Return the Validators for this controller.
     *
     * @return Validator[] 校验器
     */
    public final Validator[] getValidators() {
        return validators;
    }

    /**
     * Set the delegate used by this class. The default is <code>this</code>,
     * assuming that handler methods have been added by a subclass.
     * <p>This method does not get invoked once the class is configured.
     * @param delegateIn an object containing handler methods
     */
    public final void setDelegate(Object delegateIn) {
        Assert.notNull(delegateIn, "Delegate must not be null");
        this.delegate = delegateIn;
        registerHandlerMethods(this.delegate);

        // There must be SOME handler methods.
        if (this.handlerMethodMap.isEmpty()) {
            throw new IllegalStateException(
                "No handler methods in class [" + this.delegate.getClass()
                + "]");
        }
    }

    /**
     * Registers all handlers methods on the delegate object.
     *
     * @param delegateIn delegate
     */
    private void registerHandlerMethods(Object delegateIn) {
        this.handlerMethodMap.clear();
        this.lastModifiedMethodMap.clear();
        this.exceptionHandlerMap.clear();

        // Look at all methods in the subclass, trying to find
        // methods that are validators according to our criteria
        Method[] methods = delegateIn.getClass().getMethods();

        for (int i = 0; i < methods.length; i++) {
            // We're looking for methods with given parameters.
            Method method = methods[i];

            if (isHandlerMethod(method)) {
                registerHandlerMethod(method);
                registerLastModifiedMethodIfExists(delegateIn, method);
            }
        }

        // Now look for exception handlers.
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];

            if (isExceptionHandlerMethod(method)) {
                // Have an exception handler
                registerExceptionHandlerMethod(method);
            }
        }
    }

    /**
     * Is the supplied method a valid handler method?
     * <p>Does not consider <code>Controller.handleRequest</code> itself
     * as handler method (to avoid potential stack overflow).
     *
     * @param method 方法
     * @return boolean 是否是handler方法
     */
    private boolean isHandlerMethod(Method method) {
        Class returnType = method.getReturnType();

        if (void.class.equals(returnType)) {
            Class[] parameterTypes = method.getParameterTypes();

            return ((parameterTypes.length == 0)
            || (parameterTypes.length == 3));
        }

        return false;
    }

    /**
     * Is the supplied method a valid exception handler method?
     *
     * @param method 方法
     * @return boolean 是否是异常处理方法
     */
    private boolean isExceptionHandlerMethod(Method method) {
        return (isHandlerMethod(method)
        && (method.getParameterTypes().length == 3)
        && Throwable.class.isAssignableFrom(method.getParameterTypes()[2]));
    }

    /**
     * Registers the supplied method as a request handler.
     *
     * @param method 方法
     */
    private void registerHandlerMethod(Method method) {
        if (logger.isDebugEnabled()) {
            logger.debug("Found action method [" + method + "]");
        }

        this.handlerMethodMap.put(method.getName(), method);
    }

    /**
     * Registers a LastModified handler method for the supplied handler method
     * if one exists.
     *
     * @param delegateIn delegate
     * @param method 方法
     */
    private void registerLastModifiedMethodIfExists(Object delegateIn,
        Method method) {
        // Look for corresponding LastModified method.
        try {
            Method lastModifiedMethod = delegateIn.getClass()
                                                  .getMethod(method.getName()
                    + LAST_MODIFIED_METHOD_SUFFIX,
                    new Class[] {HttpServletRequest.class});
            // Put in cache, keyed by handler method name.
            this.lastModifiedMethodMap.put(method.getName(),
                lastModifiedMethod);

            if (logger.isDebugEnabled()) {
                logger.debug(
                    "Found last modified method for action method ["
                    + method + "]");
            }
        } catch (NoSuchMethodException ex) {
            //logger.error(ex, ex);
            logger.debug(ex);

            // No last modified method. That's ok.
        }
    }

    /**
     * Registers the supplied method as an exception handler.
     *
     * @param method 注册的异常处理方法
     */
    private void registerExceptionHandlerMethod(Method method) {
        this.exceptionHandlerMap.put(method.getParameterTypes()[2], method);

        if (logger.isDebugEnabled()) {
            logger.debug("Found exception handler method [" + method + "]");
        }
    }

    //---------------------------------------------------------------------
    // Implementation of LastModified
    //---------------------------------------------------------------------

    /**
     * Try to find an XXXXLastModified method, where XXXX is the name of a handler.
     * Return -1, indicating that content must be updated, if there's no such handler.
     *
     * @param requestIn request
     * @return long last modified
     * @see org.springframework.web.servlet.mvc.LastModified#getLastModified(HttpServletRequest)
     */
    public long getLastModified(HttpServletRequest requestIn) {
        try {
            String handlerMethodName = this.methodNameResolver
                .getHandlerMethodName(requestIn);
            Method lastModifiedMethod = (Method) this.lastModifiedMethodMap
                .get(handlerMethodName);

            //logger.info(lastModifiedMethodMap);
            //logger.info(lastModifiedMethod);
            if (lastModifiedMethod != null) {
                try {
                    // invoke the last-modified method
                    Long wrappedLong = (Long) lastModifiedMethod.invoke(this.delegate,
                            new Object[] {requestIn});

                    return wrappedLong.longValue();
                } catch (Exception ex) {
                    // We encountered an error invoking the last-modified method.
                    // We can't do anything useful except log this, as we can't throw an exception.
                    logger.error("Failed to invoke last-modified method",
                        ex);
                }
            } // if we had a lastModified method for this request
        } catch (NoSuchRequestHandlingMethodException ex) {
            logger.error(ex, ex);

            // No handler method for this request. This shouldn't happen, as this
            // method shouldn't be called unless a previous invocation of this class
            // has generated content. Do nothing, that's OK: We'll return default.
        }

        return -1L;
    }

    //---------------------------------------------------------------------
    // Implementation of AbstractController
    //---------------------------------------------------------------------

    /**
     * Handle the case where no request handler method was found.
     * <p>The default implementation logs a warning and sends an HTTP 404 error.
     * Alternatively, a fallback view could be chosen, or the
     * NoSuchRequestHandlingMethodException could be rethrown as-is.
     * @param ex the NoSuchRequestHandlingMethodException to be handled
     * @param requestIn current HTTP request
     * @param responseIn current HTTP response
     * @return a ModelAndView to render, or <code>null</code> if handled directly
     * @throws Exception an Exception that should be thrown as result of the servlet request
     */
    protected ModelAndView handleNoSuchRequestHandlingMethod(
        NoSuchRequestHandlingMethodException ex,
        HttpServletRequest requestIn, HttpServletResponse responseIn)
        throws Exception {
        pageNotFoundLogger.warn(ex.getMessage());
        responseIn.sendError(HttpServletResponse.SC_NOT_FOUND);

        return null;
    }

    /**
     * Create a new command object of the given class.
     * <p>This implementation uses <code>BeanUtils.instantiateClass</code>,
     * so commands need to have public no-arg constructors.
     * Subclasses can override this implementation if desired.
     *
     * @param clazz command的类型
     * @return Object command
     * @throws Exception if the command object could not be instantiated
     * @see org.springframework.beans.BeanUtils#instantiateClass(Class)
     */
    protected Object newCommandObject(Class clazz) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("Must create new command of class ["
                + clazz.getName() + "]");
        }

        return BeanUtils.instantiateClass(clazz);
    }

    /**
     * Bind request parameters onto the given command bean.
     * @param requestIn request from which parameters will be bound
     * @param command command object, that must be a JavaBean
     * @throws Exception in case of invalid state or arguments
     */
    protected void bind(HttpServletRequest requestIn, Object command)
        throws Exception {
        logger.debug(
            "Binding request parameters onto MultiActionController command");

        ServletRequestDataBinder binder = createBinder(requestIn, command);
        binder.bind(requestIn);

        if (this.validators != null) {
            for (int i = 0; i < this.validators.length; i++) {
                if (this.validators[i].supports(command.getClass())) {
                    ValidationUtils.invokeValidator(this.validators[i],
                        command, binder.getBindingResult());
                }
            }
        }

        binder.closeNoCatch();
    }

    /**
     * Create a new binder instance for the given command and request.
     * <p>Called by <code>bind</code>. Can be overridden to plug in custom
     * ServletRequestDataBinder subclasses.
     * <p>Default implementation creates a standard ServletRequestDataBinder,
     * and invokes <code>initBinder</code>. Note that <code>initBinder</code>
     * will not be invoked if you override this method!
     * @param requestIn current HTTP request
     * @param command the command to bind onto
     * @return the new binder instance
     * @throws Exception in case of invalid state or arguments
     * @see #bind
     * @see #initBinder
     */
    protected ServletRequestDataBinder createBinder(
        HttpServletRequest requestIn, Object command) throws Exception {
        ServletRequestDataBinder binder = new ServletRequestDataBinder(command,
                getCommandName(command));
        initBinder(requestIn, binder);

        return binder;
    }

    /**
     * Return the command name to use for the given command object.
     * Default is "command".
     * @param command the command object
     * @return the command name to use
     * @see #DEFAULT_COMMAND_NAME
     */
    protected String getCommandName(Object command) {
        return DEFAULT_COMMAND_NAME;
    }

    /**
     * Initialize the given binder instance, for example with custom editors.
     * Called by <code>createBinder</code>.
     * <p>This method allows you to register custom editors for certain fields of your
     * command class. For instance, you will be able to transform Date objects into a
     * String pattern and back, in order to allow your JavaBeans to have Date properties
     * and still be able to set and display them in an HTML interface.
     * <p>Default implementation is empty.
     * <p>Note: the command object is not directly passed to this method, but it's available
     * via {@link org.springframework.validation.DataBinder#getTarget()}
     * @param requestIn current HTTP request
     * @param binder new binder instance
     * @throws Exception in case of invalid state or arguments
     * @see #createBinder
     * @see org.springframework.validation.DataBinder#registerCustomEditor
     * @see org.springframework.beans.propertyeditors.CustomDateEditor
     */
    protected void initBinder(HttpServletRequest requestIn,
        ServletRequestDataBinder binder) throws Exception {
        initBinder((ServletRequest) requestIn, binder);
    }

    /**
     * Initialize the given binder instance, for example with custom editors.
     * @deprecated since Spring 2.0:
     * use <code>initBinder(HttpServletRequest, ServletRequestDataBinder)</code> instead
     *
     * @param requestIn request
     * @param binder ServletRequestDataBinder
     * @throws Exception 异常
     */
    protected void initBinder(ServletRequest requestIn,
        ServletRequestDataBinder binder) throws Exception {
    }

    /**
     * Determine the exception handler method for the given exception.
     * Can return null if not found.
     * @return a handler for the given exception type, or <code>null</code>
     * @param exception the exception to handle
     */
    protected Method getExceptionHandler(Throwable exception) {
        Class exceptionClass = exception.getClass();

        if (logger.isDebugEnabled()) {
            logger.debug("Trying to find handler for exception class ["
                + exceptionClass.getName() + "]");
        }

        Method handler = (Method) this.exceptionHandlerMap.get(exceptionClass);

        //logger.info(exceptionHandlerMap);
        //logger.info(handler);
        while ((handler == null)
                && !exceptionClass.equals(Throwable.class)) {
            if (logger.isDebugEnabled()) {
                logger.debug(
                    "Trying to find handler for exception superclass ["
                    + exceptionClass.getName() + "]");
            }

            exceptionClass = exceptionClass.getSuperclass();
            handler = (Method) this.exceptionHandlerMap.get(exceptionClass);
        }

        return handler;
    }

    /**
     * We've encountered an exception which may be recoverable
     * (InvocationTargetException or HttpSessionRequiredException).
     * Allow the subclass a chance to handle it.
     * @param requestIn current HTTP request
     * @param responseIn current HTTP response
     * @param ex the exception that got thrown
     * @return a ModelAndView to render the response
     * @throws Exception 异常
     */
    private ModelAndView handleException(HttpServletRequest requestIn,
        HttpServletResponse responseIn, Throwable ex) throws Exception {
        Method handler = getExceptionHandler(ex);

        if (handler != null) {
            return invokeExceptionHandler(handler, requestIn, responseIn,
                ex);
        }

        // If we get here, there was no custom handler
        if (ex instanceof Exception) {
            throw (Exception) ex;
        }

        if (ex instanceof Error) {
            throw (Error) ex;
        }

        // Should never happen!
        throw new NestedServletException("Unknown Throwable type encountered",
            ex);
    }

    /**
     * Invoke the selected exception handler.
     *
     * @param handler handler method to invoke
     * @param requestIn request
     * @param responseIn response
     * @param ex Throwable
     * @return ModelAndView mv
     * @throws Exception 异常
     */
    private ModelAndView invokeExceptionHandler(Method handler,
        HttpServletRequest requestIn, HttpServletResponse responseIn,
        Throwable ex) throws Exception {
        if (handler == null) {
            throw new NestedServletException("No handler for exception", ex);
        }

        // If we get here, we have a handler.
        if (logger.isDebugEnabled()) {
            logger.debug("Invoking exception handler [" + handler
                + "] for exception [" + ex + "]");
        }

        try {
            Object returnValue = handler.invoke(this.delegate,
                    new Object[] {requestIn, responseIn, ex});

            return massageReturnValueIfNecessary(returnValue);
        } catch (InvocationTargetException ex2) {
            Throwable targetEx = ex2.getTargetException();

            if (targetEx instanceof Exception) {
                throw (Exception) targetEx;
            }

            if (targetEx instanceof Error) {
                throw (Error) targetEx;
            }

            // Should never happen!
            throw new NestedServletException("Unknown Throwable type encountered",
                targetEx);
        }
    }

    /**
     * Processes the return value of a handler method to ensure that it either returns
     * <code>null</code> or an instance of {@link ModelAndView}. When returning a {@link Map},
     * the {@link Map} instance is wrapped in a new {@link ModelAndView} instance.
     *
     * @param returnValue 返回值
     * @return ModelAndView 根据returnValue生成的ModelAndView
     */
    private ModelAndView massageReturnValueIfNecessary(Object returnValue) {
        if (returnValue instanceof ModelAndView) {
            return (ModelAndView) returnValue;
        } else if (returnValue instanceof Map) {
            return new ModelAndView().addAllObjects((Map) returnValue);
        } else {
            // Either returned null or was 'void' return.
            // We'll assume that the handle method already wrote the response.
            return null;
        }
    }
}
