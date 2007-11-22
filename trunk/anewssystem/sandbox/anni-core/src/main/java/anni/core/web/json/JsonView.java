/*
 * Copyright 2002-2007 the original author or authors.
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
package anni.core.web.json;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import org.springframework.web.servlet.view.AbstractView;


/**
 * A View that renders its model as a JSON object.
 *
 * @author Andres Almiray aalmiray@users.sourceforge.net
 */
public class JsonView extends AbstractView {
    /** Default content type. Overridable as bean property. */
    private static final String DEFAULT_JSON_CONTENT_TYPE = "application/json";

    /** Set of properties to be excluded in the JSON rendering. */
    private String[] excludedProperties;

    /**
     * Tells the JSONSerializer to ignore or not its internal property
     * exclusions.
     */
    private boolean ignoreDefaultExcludes;

    /**
     * 日期格式.
     */
    private String datePattern = "yyyy-MM-dd";

    /**
     * 构造方法.
     */
    public JsonView() {
        super();
        setContentType(DEFAULT_JSON_CONTENT_TYPE);
    }

    /**
     * Returns wether the JSONSerializer will ignore or not its internal property
     * exclusions.
     *
     * @return 是否忽略默认的排除属性
     */
    public boolean isIgnoreDefaultExcludes() {
        return ignoreDefaultExcludes;
    }

    /**
     * Sets the group of properties to be excluded.
     *
     * @param excludedProperties 设置需要排除的属性
     */
    public void setExcludedProperties(String[] excludedProperties) {
        this.excludedProperties = excludedProperties;
    }

    /**
     * Sets wether the JSONSerializer will ignore or not its internal property
     * exclusions.
     *
     * @param ignoreDefaultExcludes 设置是否忽略默认排除的属性
     */
    public void setIgnoreDefaultExcludes(boolean ignoreDefaultExcludes) {
        this.ignoreDefaultExcludes = ignoreDefaultExcludes;
    }

    /**
     * 设置日期格式.
     *
     * @param datePattern 日期格式
     */
    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }

    /**
     * Creates a JSON [JSONObject,JSONArray,JSONNUll] from the model values.
     *
     * @param model 数据模型
     * @param request 请求
     * @param response 响应
     * @return 生成JSON
     */
    protected JSON createJSON(Map model, HttpServletRequest request,
        HttpServletResponse response) {
        return defaultCreateJSON(model);
    }

    /**
     * Creates a JSON [JSONObject,JSONArray,JSONNUll] from the model values.
     *
     * @param model 数据模型
     * @return 通过model返回JSON
     */
    protected final JSON defaultCreateJSON(Map model) {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(getExcludedProperties());
        jsonConfig.setIgnoreDefaultExcludes(isIgnoreDefaultExcludes());
        jsonConfig.registerJsonValueProcessor(Date.class,
            new DateJsonValueProcessor(datePattern));

        return JSONSerializer.toJSON(model, jsonConfig);
    }

    /**
     * Returns the group of properties to be excluded.
     *
     * @return 需要排除的属性
     */
    protected String[] getExcludedProperties() {
        return excludedProperties;
    }

    /**
     * 渲染数据.
     *
     * @param model 数据模型
     * @param request 请求
     * @param response 响应
     * @throws Exception 异常
     */
    protected void renderMergedOutputModel(Map model,
        HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        response.setContentType(getContentType());

        JSON json = createJSON(model, request, response);
        json.write(response.getWriter());
        // modify by lingo since 2007-06-30
        // 添加flush，可以避免spring-mock-2.0.6在出现缺少"}"的问题
        response.getWriter().flush();
    }
}
