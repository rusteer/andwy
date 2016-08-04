import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.io.FileUtils;


public class ProguardUtil {
    public static String generateProguardKeywors(String filePath ) {
        int maxLength = 10;
        int minLength = 6;
        String error = null;
        char[] array = "تسعتسعينسعين".toCharArray ();
        Set<String> set = new HashSet<String> ();
        for (int i = 0; i < 100000; i++) {
            int length = maxLength;
            while (length-- > minLength) {
                StringBuilder sb = new StringBuilder ();
                for (int j = 1; j <= length; j++) {
                    char c = array[(int) (Math.random () * array.length)];
                    String ss = String.valueOf (c);
                    sb.append (ss.trim ());
                }
                set.add (sb.toString ());
            }
            if (set.size () > 5000) break;
        }
        StringBuilder sb = new StringBuilder ();
        for (String line : set) {
            sb.append (line).append ("\n");
        }
        try {
            FileUtils.write (new File (filePath), sb.toString ());
        } catch (IOException e) {
            error = "generateProguardKeywors-error";
        }
        return error;
    }
    public static void main(String[] args){
        generateProguardKeywors("E:/workspace/plugin/code/OneTouchLock/proguard-keys.txt");
    }
}
