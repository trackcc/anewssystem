package anni.core.utils;

import java.security.*;
import java.security.spec.*;


/**
 * md5摘要.
 *
 * @author Lingo
 * @since 2007-06-29
 */
public class Md5Utils {
    public static final String md5(String s) {
        char[] hexDigits = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b',
                'c', 'd', 'e', 'f'
            };

        try {
            byte[] strTemp = s.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);

            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;

            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }

            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        //MD5_Test aa = new MD5_Test(); 
        System.out.print(Md5Utils.md5("XX"));
    }
}
