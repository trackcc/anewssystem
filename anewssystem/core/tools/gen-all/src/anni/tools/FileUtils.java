
package anni.tools;

import java.io.*;

public class FileUtils {
    public static void copy(String src, String dest) throws Exception {
        copy(new File(src), new File(dest));
    }

    public static void copy(File src, File dest) throws Exception {
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }

        FileInputStream fis = new FileInputStream(src);
        FileOutputStream fos = new FileOutputStream(dest);
        byte[] b = new byte[1024];
        int len = 0;
        while ((len = fis.read(b, 0, 1024)) != -1) {
            fos.write(b, 0, len);
        }
        fos.flush();
        fos.close();
        fis.close();
    }
}
