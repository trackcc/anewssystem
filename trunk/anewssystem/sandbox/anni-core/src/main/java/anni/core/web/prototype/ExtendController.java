package anni.core.web.prototype;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.propertyeditors.CustomNumberEditor;

import org.springframework.util.Assert;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.util.WebUtils;


/**
 * SpringSide Multiaction Controller的基类.
 * 对Spring的MultiActionController作了少量扩展，主要是对数据绑定校验的扩展,
 *
 * 来自www.springside.org.cn
 * @author calvin
 * @since 2007-03-14
 * @version 1.0
 */
public class ExtendController extends PrototypeController {
    /** * logger. */
    private static Log logger = LogFactory.getLog(ExtendController.class);

    /**
     * 初始化binder的回调函数.
     * 默认以yyyy-MM-dd日期格式设置DateEditor及允许Integer,Double的字符串为空.
     *
     * @see MultiActionController#createBinder(HttpServletRequest,Object)
     * @param requestIn 请求
     * @param binder 用来绑定数据的工具
     */
    protected void initBinder(HttpServletRequest requestIn,
        ServletRequestDataBinder binder) {
        /*
         * // import org.springframework.beans.propertyeditors.CustomDateEditor;
         * SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
         * binder.registerCustomEditor(Date.class,
         *     new CustomDateEditor(dateFormat, true));
         */
        binder.registerCustomEditor(Date.class, new SimpleDateEditor());
        binder.registerCustomEditor(Integer.class,
            new CustomNumberEditor(Integer.class, true));
        binder.registerCustomEditor(Double.class,
            new CustomNumberEditor(Double.class, true));
        binder.registerCustomEditor(byte[].class,
            new ByteArrayMultipartFileEditor());
    }

    /**
     * 从Request中绑定对象并进行校验.
     * Spring MVC中的Bind函数未完全符合需求,因此参考原代码进行了扩展
     *
     * @param requestIn 请求
     * @param command 需要绑定数据的对象
     * @return 校验错误
     * @throws Exception 异常
     * @see #preBind(HttpServletRequest,Object,ServletRequestDataBinder)
     */
    protected BindingResult bindObject(HttpServletRequest requestIn,
        Object command) throws Exception {
        Assert.notNull(command);

        //创建Binder
        ServletRequestDataBinder binder = createBinder(requestIn, command);
        //回调函数，供子类扩展对binder做出更进一步设置,并进行不能由binder自动完成的绑定
        preBind(requestIn, command, binder);

        //绑定
        binder.bind(requestIn);

        //校验
        Validator[] validators = getValidators();

        if (validators != null) {
            for (Validator validator : validators) {
                if (validator.supports(command.getClass())) {
                    ValidationUtils.invokeValidator(validator, command,
                        binder.getBindingResult());
                }
            }
        }

        return binder.getBindingResult();
    }

    /**
     * 回调函数，在BindObject的最开始调用。负责
     * 1.继续对binder进行设置
     * 2.绑定一些不能由Binder自动绑定的属性.这些属性通常是需要查询数据库来获得对象的绑定.
     * 要注意设置这些属性为disallow. eg.
     * <pre>binder.setDisallowedFields(new String[]{"image","category"});</pre>
     *
     * @param requestIn 请求
     * @param command 需要绑定数据的对象
     * @param binder 数据绑定工具
     * @throws Exception 异常
     * @see #bindObject(HttpServletRequest, Object)
     * @see org.springframework.web.bind.ServletRequestDataBinder#setDisallowedFields(String[])
     */
    protected void preBind(HttpServletRequest requestIn, Object command,
        ServletRequestDataBinder binder) throws Exception {
        //在子类实现
    }

    /**
     * 回调函数，声明CommandName--对象的名字,默认为首字母小写的类名.
     *
     * @see #bindObject(HttpServletRequest, Object)
     * @param command 对象
     * @return 对象名称
     */
    protected String getCommandName(Object command) {
        return StringUtils.uncapitalize(command.getClass().getSimpleName());
    }

    /**
     * 增加validator.
     * 除了Spring配置文件注入的validators数组外,可以用此函数在子类的代码里再添加新的validator.
     *
     * @param validator 校验器
     */
    protected void addValidator(Validator validator) {
        ArrayUtils.add(getValidators(), validator);
    }

    /**
     * 向View层传递message时将message放入httpSession的messages变量中.
     * 放在session中能保证message即使Redirect也不会消失。
     * 需配合{@link org.springside.core.web.MessageFilter MessageFilter}使用
     *
     * @param message 保存的反馈信息
     */
    protected void saveMessage(String message) {
        if (StringUtils.isNotBlank(message)) {
            List messages = (List) WebUtils.getOrCreateSessionAttribute(request
                    .getSession(), "messages", ArrayList.class);
            messages.add(message);
        }
    }

    /**
     * 直接向客户端返回Content字符串，不用通过View页面渲染.
     *
     * @param response 响应
     * @param content 返回的内容
     * @throws IOException io异常
     */
    protected void rendText(HttpServletResponse response, String content)
        throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(content);
    }

    /**
     * 上传文件到本地目录.
     *
    * @param uploadDir 上传路径
    * @param parameterName 参数名
    * @return 文件的相对路径
     */
    protected String upload2File(String uploadDir, String parameterName) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile(parameterName);

        if ((multipartFile != null)
                && StringUtils.isNotEmpty(
                    multipartFile.getOriginalFilename())) {
            String fileName = multipartFile.getOriginalFilename();
            fileName = System.currentTimeMillis() + fileName;

            File dir = new File(getServletContext().getRealPath(uploadDir));
            dir.mkdirs();

            File file = new File(dir, fileName);

            try {
                multipartFile.transferTo(file);
            } catch (IOException ex) {
                logger.error(ex, ex);

                return null;
            }

            return uploadDir + "/" + fileName;
        } else {
            return null;
        }
    }

    /**
     * 将上传文件写到服务器硬盘.
     * 一些不支持BLOB类型的数据库,必须把上传的文件写到硬盘.
     * 本函数写得并不严谨,如文件同名判断,加入时间戳作为文件名一部分等未做,just for demo
     *
     * @param uploadDir 上传路径
     * @param parameterName 参数名
     * @return 文件的相对路径
     * @throws IOException io异常
     */
    protected String uploadImageToFile(String uploadDir,
        String parameterName) throws IOException {
        // FIXME: upload
        String fileRelativePath = null;
        MultipartHttpServletRequest multipartRequest = null;

        try {
            multipartRequest = (MultipartHttpServletRequest) request;
        } catch (ClassCastException e) {
            return null; //only for testcast 中,mockservlet不能转为MultipartRequest
        }

        MultipartFile multipartFile = multipartRequest.getFile(parameterName);

        if ((multipartFile != null)
                && StringUtils.isNotEmpty(
                    multipartFile.getOriginalFilename())) {
            String fileName = multipartFile.getOriginalFilename();
            String fileRealPath = getServletContext().getRealPath(uploadDir)
                + File.separator + fileName;
            File file = new File(fileRealPath);
            multipartFile.transferTo(file);
            fileRelativePath = uploadDir + "/" + fileName;
        }

        return fileRelativePath;
    }

    /**
     * 将上传文件写到服务器硬盘，多个文件的时候
     * 一些不支持BLOB类型的数据库,必须把上传的文件写到硬盘.
     * 本函数写得并不严谨,如文件同名判断,加入时间戳作为文件名一部分等未做,just for demo
     *
     * @param uploadDir 上传路径
     * @param parameterName 参数名
     * @return 文件的相对路径
     * @throws IOException io异常
     */
    protected String[] uploadImageToFiles(String uploadDir,
        String parameterName) throws IOException {
        MultipartHttpServletRequest multipartRequest = null;
        List<String> fileNames = new ArrayList<String>();

        try {
            multipartRequest = (MultipartHttpServletRequest) request;
        } catch (ClassCastException e) {
            // only for testcast 中,mockservlet不能转为MultipartRequest
            return new String[0];
        }

        for (Iterator iter = multipartRequest.getFileNames();
                iter.hasNext();) {
            String name = (String) iter.next();
            MultipartFile multipartFile = multipartRequest.getFile(name);

            if ((multipartFile != null)
                    && StringUtils.isNotEmpty(
                        multipartFile.getOriginalFilename())) {
                String fileName = multipartFile.getOriginalFilename();
                String fileRealPath = getServletContext()
                                          .getRealPath(uploadDir)
                    + File.separator + fileName;
                File file = new File(fileRealPath);
                multipartFile.transferTo(file);

                String fileRelativePath = uploadDir + "/" + fileName;
                fileNames.add(fileRelativePath);
            }
        }

        String[] arrays = new String[fileNames.size()];

        return fileNames.toArray(arrays);
    }

    /**
     * 返回String参数.
     *
     * @param name 参数名
     * @param defaultValue 默认值
     * @return String 参数值
     */
    protected String getStrParam(String name, String defaultValue) {
        return ServletRequestUtils.getStringParameter(request, name,
            defaultValue);
    }

    /**
     * 空值不会返回.
     *
     * @param name parameterName
     * @return String[]
     */
    protected String[] getStrParams(String name) {
        return ServletRequestUtils.getStringParameters(request, name);
    }

    /**
     * 结果中会包含空字符串.
     *
     * @param name parameterName
     * @return String[]
     */
    protected String[] getAllStrParams(String name) {
        return request.getParameterValues(name);
    }

    /**
     * 返回Integer参数.
     *
     * @param name 参数名
     * @param defaultValue 默认值
     * @return Integer 参数值
     */
    protected Integer getIntParam(String name, Integer defaultValue) {
        return ServletRequestUtils.getIntParameter(request, name,
            defaultValue);
    }

    /**
     * @param name parameterName.
     * @return Integer[]
     */
    protected Integer[] getIntParams(String name) {
        int[] params = ServletRequestUtils.getIntParameters(request, name);
        Integer[] result = new Integer[params.length];

        for (int i = 0; i < params.length; i++) {
            result[i] = params[i];
        }

        return result;
    }

    /**
     * 返回Long参数.
     *
     * @param name 参数名
     * @param defaultValue 默认值
     * @return Long 参数值
     */
    protected Long getLongParam(String name, Long defaultValue) {
        return ServletRequestUtils.getLongParameter(request, name,
            defaultValue);
    }

    /**
     * @param name parameterName.
     * @return Long[]
     */
    protected Long[] getLongParams(String name) {
        long[] params = ServletRequestUtils.getLongParameters(request, name);
        Long[] result = new Long[params.length];

        for (int i = 0; i < params.length; i++) {
            result[i] = params[i];
        }

        return result;
    }

    /**
     * 返回Boolean参数.
     *
     * @param name 参数名
     * @param defaultValue 默认值
     * @return Boolean 参数值
     */
    protected Boolean getBooleanParam(String name, Boolean defaultValue) {
        return ServletRequestUtils.getBooleanParameter(request, name,
            defaultValue);
    }

    /**
     * 显示request中所有参数.
     * 调试
     *
     * @return 请求中所有参数以及取值的字符串
     */
    protected StringBuffer params() {
        Map map = request.getParameterMap();
        StringBuffer buff = new StringBuffer();

        Set<Map.Entry> entrySet = map.entrySet();

        for (Map.Entry entry : entrySet) {
            String key = (String) entry.getKey();
            String[] values = (String[]) entry.getValue();
            buff.append(key).append(" : ").append(Arrays.asList(values))
                .append("\n");
        }

        return buff;
    }
}
