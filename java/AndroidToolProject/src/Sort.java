import java.util.HashSet;
import java.util.Set;

public class Sort {
    public static void main(String args[]) {
        int maxLength=6;
        int minLength=1;
        char[] array = "تسعتسعينسعين".toCharArray();
        Set<String> set = new HashSet<String>();
        for (int i = 0; i < 1000000; i++) {
            int length = maxLength;
            while((length--)>minLength){
            StringBuilder sb = new StringBuilder();
            for (int j = 1; j <= length; j++) {
                char c = array[(int) (Math.random() * array.length)];
                String ss = String.valueOf(c);
                sb.append(ss.trim());
            }
            set.add(sb.toString());
            }
            if (set.size() > 10000) break;
        }
        for (String s : set) {
            System.out.println(s);
        }
    }
}
