package replace;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class GenerateResource {
    static String root = "E:/workspace/ads/code/aora-market";
    static String genFolderPath = root + "/gen/";
    static String resourceFolderPath = root + "/src/biz/";
    static String getPackageName(String rFile) {
        String s = rFile.replace('\\', '.');
        String key = "gen.";
        int index = s.indexOf(key);
        int rIndex = s.indexOf(".R.java");
        return s.substring(index + key.length(), rIndex);
    }
    public static void main(String[] args) {
        try {
            //new ReplaceIdbyRAttributes(root).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String content = generateContent();
        File packageFile = new File(resourceFolderPath);
        if (!packageFile.exists()) packageFile.mkdirs();
        FileWriter fw = null;
        try {
            fw = new FileWriter(resourceFolderPath + "AR.java");
            fw.write(content);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(content);
    }
    public static String generateContent() {
        String rFile = getRFile(genFolderPath);
        StringBuilder sb = new StringBuilder();
        if (rFile != null) {
            File resourceDir = new File(resourceFolderPath);
            if (!resourceDir.exists()) resourceDir.mkdirs();
            BufferedReader bufferedreader = null;
            try {
                bufferedreader = new BufferedReader(new InputStreamReader(new FileInputStream(rFile)));
                String line;
                sb.append("package biz;\n\n");
                sb.append("import " + getPackageName(rFile) + ".R;\n\n");
                String classStr = null;
                boolean bodyStarted = false;
                while ((line = bufferedreader.readLine()) != null) {
                    String classKey = "public static final class";
                    String classStartKey = "{";
                    String classEndKey = "}";
                    if (line.contains("public final class R {")) {
                        bodyStarted = true;
                        sb.append("public final class AR {\n");
                        continue;
                    }
                    if (!bodyStarted) continue;
                    if (line.trim().equals(classEndKey)) {
                        classStr = null;
                        sb.append(line).append("\n");
                        continue;
                    }
                    int index = line.indexOf(classKey);
                    if (index > 0) {
                        int endIndex = line.indexOf(classStartKey, index + 1);
                        classStr = line.substring(index + classKey.length() + 1, endIndex).trim();
                        sb.append(line).append("\n");
                        continue;
                    }
                    if (classStr != null) {
                        int equalIndex = line.indexOf("=");
                        if (equalIndex > 0) {
                            String[] fields = line.split("\\s+");
                            if (fields != null && fields.length == 6) {
                                String[] keyValue = fields[5].split("=");
                                String key = keyValue[0].trim();
                                sb.append(line.substring(0, equalIndex) + "=R." + classStr + "." + key + ";\n");
                                continue;
                            }
                        }
                    }
                    sb.append(line).append("\n");
                }
                System.out.println(sb.toString());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bufferedreader != null) try {
                    bufferedreader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
    private static String getRFile(String rootPath) {
        File file = new File(rootPath);
        if (file.exists()) {
            if (file.isDirectory()) {
                File list[] = file.listFiles();
                for (File child : list) {
                    String result = getRFile(child.getAbsolutePath());
                    if (result != null) return result;
                }
            } else if (file.isFile() && file.getName().endsWith("R.java")) { return file.getAbsolutePath(); }
        }
        return null;
    }
}
