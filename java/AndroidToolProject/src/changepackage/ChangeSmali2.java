package changepackage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.io.FileUtils;

public class ChangeSmali2 {
    static String smaliFolder = "E:/temp/out/smali";
    static Set<String> includedPackaged = new HashSet<String>() {
        {
           // add("/com/kryptanium/");
        }
    };
    private static boolean isIncluded(String path) {
        for (String key : includedPackaged) {
            if (path.contains(key)) return true;
        }
        return false;
    }
    static int index = 1000;
    static String filePrefix = "Class";
    public static void main(String args[]) throws IOException {
        scanFileName(smaliFolder);
    }
   
    static void scanFileName(String folder) throws IOException {
        for (File child : new File(folder).listFiles()) {
            String filePath = child.getAbsolutePath().replace('\\', '/');
            if (child.isDirectory()) {
                scanFileName(filePath);
            } else {
               // if (!isIncluded(filePath)) {
               //     continue;
               // }
                String name = child.getName();
                //if (name.toLowerCase().equals(name) && name.endsWith(".smali")) {
                if (name.length()<11 && name.endsWith(".smali")) {
                    String newName = filePrefix + (index++) + ".smali";
                    String newPath = (child.getParent() + "/" + newName).replace('\\', '/');
                    File newFile = new File(newPath);
                    while (newFile.exists()) {
                        newName = filePrefix + (index++) + ".smali";
                        newPath = (child.getParent() + "/" + newName).replace('\\', '/');
                        newFile = new File(newPath);
                    }
                    FileUtils.moveFile(child, newFile);
                    System.out.println(newFile.getAbsolutePath());
                    replaceKeyword(smaliFolder, getClassName(filePath), getClassName(newPath));
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
                        String newContent = content.replaceAll(oldWord, newWord);
                        FileUtils.write(child, newContent);
                        System.out.println(child.getAbsolutePath());
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