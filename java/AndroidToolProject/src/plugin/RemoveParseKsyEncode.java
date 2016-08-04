package plugin;
import java.io.File;
import java.util.List;
import org.apache.commons.io.FileUtils;

public class RemoveParseKsyEncode {
    public static void main(String[] args) throws Exception {
        String path = "E:/workspace/plugin/code/plugin/plugin";
        update(path);
    }
    private static void update(String path) throws Exception {
        for (File file : new File(path).listFiles()) {
            if (file.isDirectory()) {
                update(file.getAbsolutePath());
            } else {
                /**const-replace-start**/
                String javaPath = file.getAbsolutePath();
                if (javaPath.endsWith(".java") && !javaPath.endsWith("ParseKsy.java")) {
                    StringBuilder sb = new StringBuilder();
                    List<String> lines = FileUtils.readLines(file, "UTF-8");
                    boolean update = false;
                    for (String line : lines) {
                        String flag = "ParseKsy.decode(\"";
                        String flag2 = "\")";
                        int index = line.indexOf(flag);
                        if (index > 0) {
                            int lastIndex = line.indexOf(flag2, index + 2);
                            if (lastIndex > 0) {
                                String s = line.substring(index + flag.length(), lastIndex);
                                String decodeS = ParseKsy.decode(s);
                                line = line.replace(flag + s + flag2, "/*const-replace-start*/\"" + decodeS+"\"");
                                System.out.println(line);
                                update = true;
                            }
                        }
                        sb.append(line).append("\n");
                    }
                    if (update) {
                        FileUtils.write(file, sb.toString());
                    }
                }
            }
        }
    }
}
