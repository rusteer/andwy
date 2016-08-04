import java.io.UnsupportedEncodingException;

public class StringToHexString {
    public static void main(String args[]) throws UnsupportedEncodingException  {
         
        printHexString("《美女跑酷》" );
        printHexString("奔跑吧妹子！" );
        
    }
    
    private static void printHexString(String s) throws UnsupportedEncodingException{
        String result=printHexString(s.getBytes("utf-8"));
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<result.length();i++){
            sb.append(result.charAt(i));
            if(i%2==1) sb.append(" ");
        }
        System.out.println(sb);
    }
    
    
    
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) { return null; }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
    public static String printHexString(byte[] b) {
        String a = "";
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            a = a + hex;
        }
        return a.toUpperCase();
    }
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) { return null; }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}
