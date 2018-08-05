package Util.ServerHelper;

import java.io.IOException;
import java.io.InputStream;

public class IOSUtil {
    public static String inputStream2String(InputStream ins) throws IOException {
        int ava = ins.available();
        byte[] data = new byte[ava];
        ins.read(data,0,ava);
        return new String(data);
    }
}
