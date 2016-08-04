package com.util;


public class Encoder {
    private static final byte a[] = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116,
            117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
    private static final byte b[];
    public Encoder() {}
    public static byte[] a(String s) {
        s =  b(s);
        byte abyte0[];
        if (s.charAt(s.length() - 2) == '=') abyte0 = new byte[(s.length() / 4 - 1) * 3 + 1];
        else if (s.charAt(s.length() - 1) == '=') abyte0 = new byte[(s.length() / 4 - 1) * 3 + 2];
        else abyte0 = new byte[s.length() / 4 * 3];
        int i = 0;
        for (int j = 0; i < s.length() - 4; j += 3) {
            byte byte0 = b[s.charAt(i)];
            byte byte4 = b[s.charAt(i + 1)];
            byte byte8 = b[s.charAt(i + 2)];
            byte byte11 = b[s.charAt(i + 3)];
            abyte0[j] = (byte) (byte0 << 2 | byte4 >> 4);
            abyte0[j + 1] = (byte) (byte4 << 4 | byte8 >> 2);
            abyte0[j + 2] = (byte) (byte8 << 6 | byte11);
            i += 4;
        }
        if (s.charAt(s.length() - 2) == '=') {
            byte byte1 = b[s.charAt(s.length() - 4)];
            byte byte5 = b[s.charAt(s.length() - 3)];
            abyte0[abyte0.length - 1] = (byte) (byte1 << 2 | byte5 >> 4);
        } else if (s.charAt(s.length() - 1) == '=') {
            byte byte2 = b[s.charAt(s.length() - 4)];
            byte byte6 = b[s.charAt(s.length() - 3)];
            byte byte9 = b[s.charAt(s.length() - 2)];
            abyte0[abyte0.length - 2] = (byte) (byte2 << 2 | byte6 >> 4);
            abyte0[abyte0.length - 1] = (byte) (byte6 << 4 | byte9 >> 2);
        } else {
            byte byte3 = b[s.charAt(s.length() - 4)];
            byte byte7 = b[s.charAt(s.length() - 3)];
            byte byte10 = b[s.charAt(s.length() - 2)];
            byte byte12 = b[s.charAt(s.length() - 1)];
            abyte0[abyte0.length - 3] = (byte) (byte3 << 2 | byte7 >> 4);
            abyte0[abyte0.length - 2] = (byte) (byte7 << 4 | byte10 >> 2);
            abyte0[abyte0.length - 1] = (byte) (byte10 << 6 | byte12);
        }
        return abyte0;
    }
    private static String b(String s) {
        StringBuffer stringbuffer = new StringBuffer();
        int i = s.length();
        for (int j = 0; j < i; j++)
            if ( a((byte) s.charAt(j))) stringbuffer.append(s.charAt(j));
        return stringbuffer.toString();
    }
    private static boolean a(byte byte0) {
        if (byte0 == 61) return true;
        if (byte0 < 0 || byte0 >= 128) return false;
        return b[byte0] != -1;
    }
    static {
        b = new byte[128];
        for (int i = 0; i < 128; i++)
            b[i] = -1;
        for (int j = 65; j <= 90; j++)
            b[j] = (byte) (j - 65);
        for (int k = 97; k <= 122; k++)
            b[k] = (byte) (k - 97 + 26);
        for (int l = 48; l <= 57; l++)
            b[l] = (byte) (l - 48 + 52);
        b[43] = 62;
        b[47] = 63;
    }
    
    public static void main(String args[]){
        System.out.println("\""+new String(Encoder.a(new String("b3Blbi5waHA/")))+"\";");
    }
}
