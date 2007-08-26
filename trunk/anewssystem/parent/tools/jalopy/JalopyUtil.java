import java.io.*;

import java.util.*;

import de.hunsicker.jalopy.storage.*;


public class JalopyUtil {
    public static void main(String[] args) throws Exception {
        // System.out.println(System.currentTimeMillis());

        File file = new File(
                "C:/Documents and Settings/Administrator/.jalopy.15/default/history.dat");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
                    file));
        Map map = (Map) ois.readObject();
        ois.close();

        /**
         * 新买的机器的毛病很奇怪，不但主板的鼠标接口不能用，
         * 键盘按键不好用，键盘还短路。
         * 现在发现的问题更奇怪，设置文件最后修改时间，最后三位总是000，而且倒数第四位只能是双数。
         * 造成的结果是用timestamp记录的jalopy一直失效，只好自己对历史文件做修改，手动加上2000，
         * 这样应该没问题了吧？
         *
         * @todo: 这是个大问题，能上网了一定要查一下看是怎么回事。
         * 其实最好重写jalopy，让中文支持checksum，就没这问题了。
         */
        for (Iterator iter = map.entrySet().iterator(); iter.hasNext();) {
            Map.Entry mapEntry = (Map.Entry) iter.next();
            String fileName = (String) mapEntry.getKey();
            History.Entry entry = (History.Entry) mapEntry.getValue();
            History.Entry trueEntry = new History.Entry(entry
                    .getPackageName(), entry.getModification() + 4000);
            map.put(fileName, trueEntry);

            //File mapFile = new File(fileName);
            //System.out.println(mapFile.lastModified() + " - " + entry.getModification());
        }

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
		oos.writeObject(map);
		oos.flush();
		oos.close();
    }
}
