package replace;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReplaceResourceIdV2 {
    static String root = "E:/temp";
    static Map<String, String> map16 = new HashMap<String, String>();
    String type = null;
    public ReplaceResourceIdV2(String paarms) {
        root = paarms;
    }
    public static void main(String[] args) {
        //System.out.println(Long.toHexString(2131362099));
        new ReplaceResourceIdV2(root).start();
    }
    public void start() {
        initMap(root + "/out/res/values/public.xml");
        batchReplaceID(root + "/out/smali/com/");
    }
    static void initMap(String path) {
        BufferedReader bufferedreader = null;
        try {
            bufferedreader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            String line;
            while ((line = bufferedreader.readLine()) != null) {
                if (line.contains("<public")) {
                    String type = findAttrValue(line, "type");
                    String name = findAttrValue(line, "name");
                    String id = findAttrValue(line, "id");
                    //String value = "biz.AR." + type + "." + name;
                    String value = "Lbiz/AR$" + type + ";->" + name + ":I";
                    map16.put(id, value);
                }
            }
            bufferedreader.close();
            bufferedreader = null;
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
    void batchReplaceID(String path) {
        File file = new File(path);
        if (file.isFile() && file.getAbsolutePath().endsWith(".smali")) {
            replaceID(file.getAbsolutePath());
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File child : files) {
                batchReplaceID(child.getAbsolutePath());
            }
        }
    }
    private static String findAttrValue(String line, String attr) {
        String prefix = attr + "=\"";
        if (line.contains(attr)) {
            int index = line.indexOf(prefix);
            int lastIndex = line.indexOf("\"", index + prefix.length());
            if (index > 0 && lastIndex > index) { return line.substring(index + prefix.length(), lastIndex); }
        }
        return "";
    }
    String replaceString(String line) {
        if (line.contains("0x7f0") && line.contains("const")) {
            for (String key : map16.keySet()) {
                if (line.contains(key)) {
                    String[] fields = line.split(",");
                    if (fields.length == 2) {
                        String id = fields[1].trim();
                        if (map16.containsKey(id)) {
                            fields = fields[0].trim().split(" ");
                            if (fields.length == 2) {
                                String variableName = fields[1].trim();
                                if (variableName.startsWith("v")) {
                                    line = String.format("sget %s, %s", variableName, map16.get(id));
                                }
                            }
                        }
                    }
                    break;
                }
            }
        }
        return line;
    }
    private void replaceID(String filePath) {
        BufferedReader bufferedreader = null;
        FileWriter fw = null;
        try {
            bufferedreader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            String line;
            boolean update = false;
            StringBuilder sb = new StringBuilder();
            while ((line = bufferedreader.readLine()) != null) {
                String newLine = replaceString(line);
                if (!newLine.equals(line)) {
                    update = true;
                }
                sb.append(newLine);
                sb.append("\n");
            }
            bufferedreader.close();
            if (update) {
                fw = new FileWriter(filePath);
                fw.write(sb.toString());
                fw.flush();
                fw.close();
                fw = null;
                // sourceFile.deleteOnExit();
                // tempFile.renameTo(sourceFile);
                System.out.println(filePath + " done!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedreader != null) try {
                bufferedreader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (fw != null) try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    static List<Integer> getCollection() {
        System.out.println("----");
        List<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < 10; ++i)
            result.add(i);
        return result;
    }
}
