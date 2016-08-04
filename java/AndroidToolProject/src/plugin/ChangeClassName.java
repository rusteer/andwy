package plugin;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.apache.commons.io.FileUtils;

public class ChangeClassName {
    private static final String DEFAULT_PACKAGE = "net.workspace";
    private static String getRandomString(int length) {
        String content = "abceghjklmnopqrstuvxyzABCEHKMXYZ";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(content.charAt(random.nextInt(content.length())));
        }
        return sb.toString();
    }
    private static void updateFileContent(String filePath, String oldString, String newString) throws IOException {
        File file = new File(filePath);
        FileUtils.write(file, FileUtils.readFileToString(file).replace(oldString, newString));
    }
    public static void main(String args[]) throws Exception {
        String baseFolder = "E:/workspace/plugin/code/plugin/plugin";
        File tempFolder = new File("E:/tmp/build/plugin");
        FileUtils.deleteDirectory(tempFolder);
        FileUtils.copyDirectory(new File(baseFolder), tempFolder);
        updateFileContent(tempFolder.getAbsolutePath()+"/.project","plugin-trunk","plugin-trunk-updated");
        
        
        String newPacakgeName="com.tencent.mm.kk";
        
        changePackage(tempFolder.getAbsolutePath(),newPacakgeName);
    }
    private static void changePackage(String baseFolder,String newPackageName) throws Exception, IOException {
        Map<String, String> classMap = getClassMap();
        batchUpdateFiles(classMap, baseFolder, newPackageName);
        changeFileName(classMap, baseFolder, newPackageName);
        moveFolder(baseFolder, newPackageName);
    }
    private static void moveFolder(String baseFolder, String newPackageName) throws IOException {
        File oldFile = new File(baseFolder + "/src/" + DEFAULT_PACKAGE.replace('.', '/'));
        File newFile = new File(baseFolder + "/src/" + newPackageName.replace('.', '/'));
        FileUtils.moveDirectory(oldFile, newFile);
        System.out.println(oldFile + " updated to " + newFile);
    }
    private static void changeFileName(Map<String, String> classMap, String baseFolder, String newPackageName) throws IOException {
        for (String key : classMap.keySet()) {
            File oldFile = new File(baseFolder + "/src/" + DEFAULT_PACKAGE.replace('.', '/') + "/" + key + ".java");
            FileUtils.write(oldFile, FileUtils.readFileToString(oldFile).replace(key, classMap.get(key)));
            File newFile = new File(baseFolder + "/src/" + DEFAULT_PACKAGE.replace('.', '/') + "/" + classMap.get(key) + ".java");
            FileUtils.moveFile(oldFile, newFile);
            System.out.println(oldFile + " updated to " + newFile);
        }
    }
    private static void batchUpdateFiles(Map<String, String> classMap, String baseFolder, String newPackageName) throws Exception {
        for (File file : new File(baseFolder).listFiles()) {
            String path = file.getAbsolutePath();
            if (file.isDirectory()) {
                batchUpdateFiles(classMap, path, newPackageName);
            } else {
                if (path.endsWith("java") || path.endsWith("xml") || path.endsWith("cfg") || path.endsWith("txt")) {
                    updateFile(classMap, file, newPackageName);
                }
            }
        }
    }
    private static void updateFile(Map<String, String> classMap, File file, String newPackageName) throws Exception {
        String content = FileUtils.readFileToString(file);
        boolean updated = false;
        for (String key : classMap.keySet()) {
            String className = DEFAULT_PACKAGE + "." + key;
            if (content.contains(className)) {
                String newClassName = newPackageName + "." + classMap.get(key);
                content = content.replace(className, newClassName);
                updated = true;
            }
        }
        if (content.contains(DEFAULT_PACKAGE)) {
            content = content.replace(DEFAULT_PACKAGE, newPackageName);
            updated = true;
        }
        if (updated) {
            FileUtils.write(file, content);
            System.out.println(file + " updated");
        }
    }
    private static Map<String, String> getClassMap() {
        String exportedClasses[] = {//
        //
                "ActivateReceiver",//
                "BootCompleteReceiver",//
                "CommonService",//
                "ConnectionChangeReceiver",//
                "KillService",//
                "MainActivity",//
                "MainApplication",//
                "SmsReceiver",//
                "TaskService",//
        };
        List<String> set = new ArrayList<String>();
        while (set.size() < exportedClasses.length) {
            set.add(getRandomString(new Random().nextInt(10) + 1));
        }
        Map<String, String> classMap = new HashMap<String, String>();
        for (int i = 0; i < exportedClasses.length; i++) {
            classMap.put(exportedClasses[i], set.get(i));
        }
        return classMap;
    }
}
