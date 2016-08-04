package tools;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.io.FileUtils;

public class ScanCode {
    static String root = "E:/temp/MouseTrap/trunk";
    static String outFile = "d:/code-" + System.currentTimeMillis() + ".txt";
    static int maxLines = 20000;
    static List<String> content = new ArrayList<String>();
    public static void main(String[] args) throws IOException {
        scan(root);
        StringBuilder sb = new StringBuilder();
        for (String line : content) {
            sb.append(line).append("\n");
        }
        FileUtils.write(new File(outFile), sb.toString());
    }
    private static Set<String> TYPES = new HashSet<String>() {
        private static final long serialVersionUID = 1L;
        {
            add(".java");
            //add(".h");
            //add(".cpp");
            //add(".c");
            //add(".xml");
           // add(".bat");
           // add(".txt");
           // add(".properties");
           // add(".sh");
        }
    };
    private static boolean matchType(File file) {
        for (String key : TYPES) {
            String absolutePath = file.getAbsolutePath().replace("\\", "/");
            if (absolutePath.toUpperCase().endsWith(key.toUpperCase()) && !absolutePath.contains("/gen/") && !absolutePath.contains("/bin/") && !absolutePath.contains("/target/")) return true;
        }
        return false;
    }
    static void scan(String path) throws IOException {
        File file = new File(path);
        if (content.size() > maxLines) return;
        if (file.isFile()) {
            if (matchType(file)) {
                List<String> lines = FileUtils.readLines(file);
                System.out.println(file);
                content.add("-----------------------------------------------------------------------------------------");
                content.add(file.getAbsolutePath().substring(root.length()).replace("\\", "/"));
                content.add("-----------------------------------------------------------------------------------------");
                content.addAll(lines);
            }
        } else if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                scan(child.getAbsolutePath());
            }
        }
    }
}
