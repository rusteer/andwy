package plugin;
import java.io.UnsupportedEncodingException;

public class SimpleEncoder {
    public static final int code = /*code-replace-start*/9222/*code-replace-end*/;
    public static byte[] getBytes(String s) {
        if (s != null && s.length() > 0) {
            try {
                byte[] result = s.getBytes("UTF-8");
                for (int i = 0; i < result.length; i++) {
                    result[i] = (byte) (result[i] ^ (i * 3 + code));
                }
                return result;
            } catch (Throwable e) {
                MyLogger.error(e);
            }
        }
        return null;
    }
    public static String getString(byte[] bytes) {
        byte[] newBytes = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            newBytes[i] = (byte) (bytes[i] ^ (i * 3 + code));
        }
        try {
            return new String(newBytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            MyLogger.error(e);
        }
        return "";
    }
    public static String getBytesString(String s) {
        StringBuilder sb = new StringBuilder();
        sb.append("SimpleEncoder.getString(new byte[]{");
        byte[] bytes = getBytes(s);
        for (int i = 0; i < bytes.length; i++) {
            sb.append(bytes[i]);
            if (i < bytes.length - 1) {
                sb.append(",");
            }
        }
        sb.append("})");
        return sb.toString();
    }
    public static void main(String args[]) {
        System.out.println(getBytesString("2014-11-23 17:38:31,107短信拦截报告短信取号"));
        System.out.println(SimpleEncoder.getString(new byte[] { 48, 53, 57, 63, 35, 32, 37, 58, 40, 46, 0, 18, 17, 19, 31, 23, 8, 6, 9, 23, 15, 113, 115, -96, -43, -32, -76, -20,
                -9, -65, -41, -7, -124, -19, -62, -115, -28, -44, -111, -26, -16, -102, 31, 46, 98, 54, 45, 106, 29, 3, 125, 20, 41 }));
    }
}
