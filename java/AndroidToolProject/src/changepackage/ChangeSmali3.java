package changepackage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.io.FileUtils;

public class ChangeSmali3 {
    static String smaliFolder = "E:/temp/out/smali";
    static Set<String> includedPackaged = new HashSet<String>() {
        {
            //add("/com/poxiao/");
            add("/com/");
        }
    };
    static int index = 1000;
    static String filePrefix = "Class";
    public static void main(String args[]) throws Exception {
        scanFileName(smaliFolder);
    }
    private static boolean isIncluded(String path) {
        for (String key : includedPackaged) {
            if (path.contains(key)) return true;
        }
        return false;
    }
    static void scanFileName(String folder) throws Exception {
        for (File child : new File(folder).listFiles()) {
            String filePath = child.getAbsolutePath().replace('\\', '/');
            if (child.isDirectory()) {
                scanFileName(filePath);
            } else {
                if (!isIncluded(filePath)) {
                    continue;
                }
                String name = child.getName();
                
                if (name.toLowerCase().equals(name) && name.endsWith(".smali")) {
                //if (name.length()<=10 && name.endsWith(".smali")) {
                    //if (name.length()<11 && name.endsWith(".smali")) {
                    String firstLine = FileUtils.readLines(child).get(0);
                    String className = firstLine.substring(firstLine.indexOf(" L") + 1);
                    String newClassName = className.substring(0, className.lastIndexOf("/") + 1) + "Class" + (index++) + ";";
                    replaceKeyword(smaliFolder, className, newClassName);
                    System.out.println(className + "->" + newClassName);
                    //return;
                }
            }
        }
    }
    static String getClassName(String path) {
        path = path.substring(smaliFolder.length() + 1);
        path = path.substring(0, path.length() - ".smali".length());
        return "L" + path + ";";
    }
    static void replaceKeyword(String folder, String oldWord, String newWord) {
        File file = new File(folder);
        for (File child : file.listFiles()) {
            if (child.isFile()) {
                try {
                    String content = FileUtils.readFileToString(child);
                    if (content.contains(oldWord)) {
                        String newContent = content.replace(oldWord, newWord);
                        FileUtils.write(child, newContent);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                replaceKeyword(child.getAbsolutePath(), oldWord, newWord);
            }
        }
    }
}
