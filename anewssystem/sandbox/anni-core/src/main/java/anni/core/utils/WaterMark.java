package anni.core.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;


/**
 * 给图片加水印.
 *
 * @author Lingo
 */
public class WaterMark {
    /** * 默认水印文字. */
    public static final String DEFAULT_TEXT = "LINGO";

    /** * 默认图片格式. */
    public static final String DEFAULT_FORMAT = "jpg";

    /** * 默认文字颜色. */
    public static final Color DEFAULT_COLOR = Color.LIGHT_GRAY;

    /** * 默认字体. */
    public static final Font DEFAULT_FONT = new Font("Arial", Font.BOLD, 18);

    /** * 工具类需要protected构造方法. */
    protected WaterMark() {
    }

    /**
     * 与springmvc中上传文件配合，添加水印.
     * @param fileName 上传文件名
     * @param ctx request.getRealPath
     * @return 去掉了realPath部分的相对路径
     * @throws Exception 异常
     */
    public static String makeWaterMark(String fileName, String ctx)
        throws Exception {
        if (fileName == null) {
            return null;
        }

        try {
            String dest = execute(ctx + "/" + fileName);

            return dest.substring(ctx.length());
        } catch (Exception ex) {
            return fileName;
        }
    }

    /**
     * 添加水印，输入图片与输出图片相同，使用默认的文字，颜色，字体.
     *
     * @param fileName 处理的图片的名称
     * @return 返回生成的文件名
     * @throws Exception 可能抛出任何异常
     */
    public static String execute(String fileName) throws Exception {
        return execute(fileName, fileName, DEFAULT_TEXT);
    }

    /**
     * 添加水印，使用默认的颜色，字体.
     *
     * @param src 源文件名
     * @param dest 目标文件名
     * @param text 水印文字
     * @return 返回生成的文件名
     * @throws Exception 可能抛出任何异常
     */
    public static String execute(String src, String dest, String text)
        throws Exception {
        return execute(src, dest, text, DEFAULT_COLOR, DEFAULT_FONT);
    }

    /**
     * 添加水印.
     *
     * @param src 源文件名
     * @param dest 目标文件名
     * @param text 水印文字
     * @param color 文字颜色
     * @param font 字体
     * @return 返回生成的文件名
     * @throws Exception 可能抛出任何异常
     */
    public static String execute(String src, String dest, String text,
        Color color, Font font) throws Exception {
        BufferedImage srcImage = ImageIO.read(new File(src));

        int width = srcImage.getWidth(null);
        int height = srcImage.getHeight(null);
        BufferedImage destImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics g = destImage.getGraphics();

        g.drawImage(srcImage, 0, 0, width, height, null);
        g.setColor(color);
        g.setFont(font);
        g.fillRect(0, 0, 50, 50);
        g.drawString(text, width / 5, height - 10);
        g.dispose();

        int index = dest.lastIndexOf(".");

        if (index == -1) {
            dest += ("." + DEFAULT_FORMAT);
        } else {
            dest = dest.substring(0, index + 1) + DEFAULT_FORMAT;
        }

        ImageIO.write(destImage, DEFAULT_FORMAT, new File(dest));

        return dest;
    }

    /**
     * 返回图片的格式.
     * 当不知道是什么格式的时候，就返回null
     *
     * @param file 图片对象，我这里倾向于用File类型
     * @return 格式名称
     * @throws IOException 1.如果createImageInputStream的时候，无法生成缓存文件
     *                     2.如果getFormatName的时候，无法读取格式信息
     */
    public static String getFormatName(File file) throws IOException {
        ImageInputStream iis = ImageIO.createImageInputStream(file);

        Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);

        if (!iter.hasNext()) {
            throw new IllegalArgumentException("can't get image.");
        }

        // 一个文件，只能有一个图片吧？多图片的咱们也不考虑，先
        ImageReader reader = iter.next();

        iis.close();

        return reader.getFormatName();
    }
}
