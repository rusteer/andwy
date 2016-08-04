package replace;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class ReplaceResource {
    static String root = "E:\\workspace\\pay\\code\\projects\\Yuanlai";
    static String publicXmlPath = root + "/res/values/public.xml";
    static String sourceFolder = root + "/src/";
    static Map<String, Map<String, String>> map16 = new HashMap<String, Map<String, String>>();
    static Map<String, Map<String, String>> map10 = new HashMap<String, Map<String, String>>();
    String type = null;
    public ReplaceResource(String root) {
        this.root = root;
    }
    public static void main(String[] args) {
        
        System.out.println(Long.toHexString(2131362099));
        
        initMap(publicXmlPath);
        try {
           // new ReplaceResource(root).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void start() {
        for (String type : this.map16.keySet()) {
            this.type = type;
            batchReplaceID(map16,sourceFolder);
            batchReplaceID(map10,sourceFolder);
        }
        
        
        
        
        
        //new ReplaceU().startU(sourceFolder);
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
                    {
                        Map<String, String> typeMap = map16.containsKey(type) ? map16.get(type) : new HashMap<String, String>();
                        typeMap.put(id, name);
                        map16.put(type, typeMap);
                    }
                    {
                        Map<String, String> typeMap = map10.containsKey(type) ? map10.get(type) : new HashMap<String, String>();
                        typeMap.put(Long.valueOf(id.substring(2, id.length()), 16) + "", name);
                        map10.put(type, typeMap);
                    }
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
    void batchReplaceID(Map<String, Map<String, String>> map, String path) {
        File file = new File(path);
        if (!file.exists() || path.endsWith("RR.java")) return;
        if (file.isFile() && file.getAbsolutePath().endsWith(".java")) {
            replaceID(file.getAbsolutePath());
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File child : files) {
                batchReplaceID(map, child.getAbsolutePath());
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
       // if (!line.contains("0x")) return line;
        Map<String, String> typeMap = map16.get(this.type);
        for (String key : typeMap.keySet()) {
            if (line.contains(key)) {
                line = line.replaceAll(key, "AR." + this.type + "." + typeMap.get(key));
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
            StringBuilder sb = new StringBuilder();
            boolean addImport = false;
            while ((line = bufferedreader.readLine()) != null) {
                if (!addImport && line.startsWith("import")) {
                    if (line.startsWith("import biz.AR")) {
                        sb.append(line + "\n");
                    } else {
                        sb.append("import biz.AR;\n" + line + "\n");
                    }
                    addImport = true;
                    continue;
                }
                sb.append(replaceString(line));
                sb.append("\n");
            }
            bufferedreader.close();
            bufferedreader = null;
            fw = new FileWriter(filePath);
            fw.write(sb.toString());
            fw.flush();
            fw.close();
            fw = null;
            // sourceFile.deleteOnExit();
            // tempFile.renameTo(sourceFile);
            System.out.println(filePath + " done!");
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
