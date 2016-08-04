import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import workspace.base64.Base64;

public class AES {
    public static String decode(String data, String password) {
        AES aes = new AES(password);
        byte[] crypted = Base64.decodeToBytes(data);
        return new String(aes.decrypt(crypted));
    }
    /**
     * "sfe023f_9fd&fwfl"
     * @param s
     * @param password
     * @return
     */
    public static String encode(String data, String password) {
        AES aes = new AES(password);
        byte[] crypted = aes.encrypt(data.getBytes());
        return Base64.encode(crypted);
    }
    private IvParameterSpec ivSpec;
    private SecretKeySpec keySpec;
    public AES(String key) {
        try {
            byte[] keyBytes = key.getBytes();
            byte[] buf = new byte[16];
            for (int i = 0; i < keyBytes.length && i < buf.length; i++) {
                buf[i] = keyBytes[i];
            }
            keySpec = new SecretKeySpec(buf, "AES");
            ivSpec = new IvParameterSpec(keyBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public byte[] decrypt(byte[] crypted) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            return cipher.doFinal(crypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public byte[] encrypt(byte[] origData) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            return cipher.doFinal(origData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static byte[] getBytes(String s) {
        if (s == null || s.length() == 0) return null;
        byte[] result = s.getBytes();
        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) (result[i] ^ (i + 2));
        }
        return result;
    }
    public static String getString(byte[] bytes) {
        byte[] newBytes = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            newBytes[i] = (byte) (bytes[i] ^ (i + 2));
        }
        return new String(newBytes);
    }
    
    public static String getBytesString(String s){
        StringBuilder sb=new StringBuilder();
        //sb.append("new byte[]{");
        byte[] bytes=getBytes(s);
        for(int i=0;i<bytes.length;i++){
            sb.append(bytes[i]);
            if(i<bytes.length-1){
                sb.append(",");
            }
        }
        //sb.append("};");
        return sb.toString();
    }
    static String password = "com.ggle.android";
    public static void main(String args[]) {
        
        //byte[] bytes=getBytes(password);
       // System.out.println(getString(getBytes(password)));
        //System.out.println(getBytesString("com.ggle.android"));
        String data= "\n";
        System.out.println("AES.decode(\""+encode(data, password)+"\")");
        System.out.println("SimpleEncoder.getString(new byte[]{"+getBytesString(data)+"});");
       // System.out.println(new String(new byte[]{45,119,105,117,40,102,120,98}));
        //System.out.println(decode("+J9wWKztXJCV4E4j/NUErzWFgLXRErUqYau9nTCMYk2010DioYf6DfEBFtLR8zKk",password));
    }
}
