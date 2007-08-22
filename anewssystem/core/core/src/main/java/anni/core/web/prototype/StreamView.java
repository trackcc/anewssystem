package anni.core.web.prototype;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;


/**
 * 不需要使用模板渲染的时候，就可以用这个view来避免spring抛出异常.
 *
 * @author Lingo
 * @since 2007-06-05
 */
public class StreamView implements View {
    /** * 默认JPG图片使用的view. */
    public static final StreamView IMAGE_JPEG_VIEW = new StreamView(
            "image/jpeg");

    /** * contentType. */
    private String contentType = null;

    /**
     * 默认构造方法.
     */
    public StreamView() {
        this("text/plain");
    }

    /**
     * 使用的contentType只能从构造方法注入.
     * 不变体的一种设计模式
     *
     * @param contentTypeIn contentType
     */
    public StreamView(String contentTypeIn) {
        contentType = contentTypeIn;
    }

    /**
     * @return String contentType.
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * 这里不做任何事情.
     *
     * @param model Map
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception 任何异常
     */
    public void render(Map model, HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        // do nothing;
    }
}
