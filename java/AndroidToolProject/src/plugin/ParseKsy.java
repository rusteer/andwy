package plugin;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class ParseKsy {
    public static String key = "duew&^%5d54nc'KH";
    public static String algorithm = "AES";
    private static String byte2hexString(byte abyte0[]) {
        StringBuffer stringbuffer = new StringBuffer(2 * abyte0.length);
        int i = 0;
        do {
            if (i >= abyte0.length) return stringbuffer.toString();
            stringbuffer.append(new StringBuilder(String.valueOf(Integer.toString(0xf & abyte0[i] >> 4, 16))).append(Integer.toString(0xf & abyte0[i], 16)).toString());
            i++;
        } while (true);
    }
    public static String decode(String target) {
        String result = "";
        try {
            SecretKeySpec secretkeyspec = new SecretKeySpec(key.getBytes(), algorithm);
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(2, secretkeyspec);
            result = new String(cipher.doFinal(hex2Bin(target)),"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public static String encode(String target) {
        String result = "";
        try {
            SecretKeySpec secretkeyspec = new SecretKeySpec(key.getBytes(), algorithm);
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(1, secretkeyspec);
            result = byte2hexString(cipher.doFinal(target.getBytes("UTF-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    private static byte[] hex2Bin(String s) {
        byte abyte0[];
        if (s.length() < 1) {
            abyte0 = null;
        } else {
            abyte0 = new byte[s.length() / 2];
            int i = 0;
            while (i < s.length() / 2) {
                int j = Integer.parseInt(s.substring(i * 2, 1 + i * 2), 16);
                abyte0[i] = (byte) (Integer.parseInt(s.substring(1 + i * 2, 2 + i * 2), 16) + j * 16);
                i++;
            }
        }
        return abyte0;
    }
    
    public static void main(String args[]) throws  Exception{
       
       String s=
               ParseKsy.encode("x.im9999.com");
               
               ;
         System.out.println("\""+s+"\";");
        
       //System.out.println(obj);
       
     /*   
       String s= "AES";
       byte[]  bytes= s.getBytes();
       System.out.print("new String(new byte[]{");
       for(int i=0;i<bytes.length;i++){
           System.out.print(bytes[i]);
           if(i<bytes.length-1) 
               System.out.print(",");
       }
       System.out.print("});");
       System.out.println();
       String ss=new String(new byte[]{65,69,83});
       System.out.println(ss.equals(s));*/
    }
    
    
}
