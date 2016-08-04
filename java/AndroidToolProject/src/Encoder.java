
public class Encoder {
    private static int a[] = { 74, 29, 39, 41, 46, 70, 9, 37, 82, 78, 82, 26, 43, 31, 9, 12, 93, 72, 74, 12, 20, 92, 33, 8, 17, 30, 77, 85, 67, 65, 75, 23, 82, 61, 1, 83, 7, 24,
            84, 79, 10, 71, 71, 65, 11, 54, 95, 37, 77, 14, 81, 10, 60, 58, 95, 61, 88, 92, 71, 78, 35, 91, 9, 52, 45, 59, 41, 19, 66, 35, 0, 78, 68, 68, 6, 21, 97, 33, 33, 64,
            12, 78, 47, 7, 21, 36, 38, 20, 82, 16, 7, 47, 7, 44, 58, 16, 40, 43, 55, 35, 48, 63, 44, 78, 32, 83, 25, 18, 74, 83, 62, 40, 40, 68, 18, 79, 10, 34, 31, 23, 77, 78,
            24, 94, 24, 13, 0, 45, 88, 9, 1, 59, 56, 95, 85, 0, 70, 84, 15, 60, 55, 49, 60, 15, 68, 18, 59, 62, 4, 60, 91, 29, 57, 9, 99, 64, 46, 85, 17, 28, 78, 27, 20, 85, 71,
            41, 4, 5, 64, 83, 55, 35, 27, 11, 31, 16, 73, 96, 22, 92, 71, 9, 89, 48, 99, 99, 9, 63, 64, 39, 89, 26, 42, 43, 70, 29, 65, 47, 24, 27 };
   
    
    public static void main(String args[]){
        StringBuilder sb = new StringBuilder();
        for(int c:a){
            sb.append((char)c);
        }
        System.out.print(sb);
    }
    public static String a(String s) {
        if (s == null) return "";
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("s");
        char ac[] = s.toCharArray();
        try {
            for (int i = 0; i < ac.length; i++) {
                char c1 = ac[i];
                char c2 = (char) (c1 + a[i % a.length]);
                stringbuilder.append(c2);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        stringbuilder.append("e");
        return stringbuilder.toString();
    }
    public static String Decode(String s) {
        if (s == null) return "";
        if (s.startsWith("s") && s.endsWith("e")) {
            StringBuilder stringbuilder = new StringBuilder(s);
            stringbuilder.deleteCharAt(0);
            stringbuilder.deleteCharAt(stringbuilder.length() - 1);
            char ac[] = stringbuilder.toString().toCharArray();
            stringbuilder = new StringBuilder();
            try {
                for (int i = 0; i < ac.length; i++) {
                    char c1 = ac[i];
                    char c2 = (char) (c1 - a[i % a.length]);
                    stringbuilder.append(c2);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return stringbuilder.toString();
        } else {
            return "";
        }
    }
}
