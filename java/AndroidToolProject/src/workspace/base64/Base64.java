package workspace.base64;

public class Base64 {
    public static String decode(String s) {
        try {
            return new String(decodeToBytes(s), "UTF-8");
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }
    public static byte[] decodeToBytes(String s) {
        try {
            return new BASE64Decoder().decodeBuffer(s);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String encode(byte[] bytes) {
        return new BASE64Encoder().encode(bytes);
    }
    public static String encode(String s) {
        if (s != null) {
            try {
                return encode(s.getBytes("UTF-8"));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
