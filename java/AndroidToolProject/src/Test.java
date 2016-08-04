import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

public final class Test {
    public static final String KUZAI_PIC_URL = "http://cdn.cooguo.com/download/kuzaipic/";
    static class AnimationResource {
        private String name;
        private int count;
        public AnimationResource(String name, int count) {
            this.name = name;
            this.count = count;
        }
    }
    public static void main(String args[]) {
      String s="30000849608918";
      System.out.println( Integer.valueOf(s.substring(s.length() - 2, s.length())));
      System.out.println();
    }
    private static final int bitmapStartIndex[] = { 0, 2387, 6311, 10091, 11840, 13064, 13366, 14189, 15757, 19246, 22505, 22674, 27723, 32232, 33247, 34181, 35336, 36382, 38437, 43748, 49108, 56137, 63202, 0x10e1b, 0x115c6, 0x13b50,
            0x13cfe, 0x13ee5, 0x14011, 0x14345, 0x146b5, 0x14a16, 0x1576a, 0x16377, 0x1d576 };
    public static void a(int index) {
        InputStream inputstream = null;
        try {
            File file = new File("e:/k.dat");
            inputstream = new FileInputStream(file);
            inputstream.skip(bitmapStartIndex[index - 1]);
            byte abyte0[] = new byte[bitmapStartIndex[index] - bitmapStartIndex[index - 1]];
            inputstream.read(abyte0, 0, abyte0.length);
            FileOutputStream out = new FileOutputStream("E:\\workspace\\wg\\code\\android\\ongoing\\KuguoKuzai\\ref\\images/" + index + ".png");
            out.write(abyte0);
            out.close();
            inputstream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
