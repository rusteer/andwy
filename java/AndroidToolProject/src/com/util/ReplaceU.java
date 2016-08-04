package com.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class ReplaceU {
    public static void main(String args[]){
        new ReplaceU().startU();
    }
    public void startU() {
        System.out.println("startU start");
        batchReplaceU("E:/workspace/pay/code/projects/MeizhiRun");
        System.out.println("startU end");
    }    
    private ScriptEngineManager manager = new ScriptEngineManager();
    private ScriptEngine engine = manager.getEngineByName("js");
    private Bindings bindings = engine.createBindings();
    private String getChineseString(String line) {
        bindings.put("result", "");
        try {
            engine.eval("result='" + line + "'", bindings);
            String result = (String) bindings.get("result");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(line);
            return null;
        }
    }
    static String U_PREFIX = "\\u";
    static String U_SUFFIX = "\"";
    private void replaceU(String filePath) {
        BufferedReader bufferedreader = null;
        FileWriter fw = null;
        try {
            bufferedreader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            String line;
            StringBuilder sb = new StringBuilder();
            boolean needReplaceFile = false;
            while ((line = bufferedreader.readLine()) != null) {
                if (line.contains(U_PREFIX) && line.contains("\"")) {
                    String result = getChineseString(line);
                    if (result != null) {
                        needReplaceFile = true;
                        line = result;
                        line = line.replace("\n", "\\n");
                    }
                }
                sb.append(line).append('\n');
            }
            bufferedreader.close();
            bufferedreader = null;
            if (needReplaceFile) {
                fw = new FileWriter(filePath);
                fw.write(sb.toString());
                fw.flush();
                fw.close();
                fw = null;
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
    void batchReplaceU(String path) {
        File file = new File(path);
        if (!file.exists() || path.endsWith("RR.java")) return;
        if (file.isFile() && file.getAbsolutePath().endsWith(".java")) {
            replaceU(file.getAbsolutePath());
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File child : files) {
                batchReplaceU(child.getAbsolutePath());
            }
        }
    }
    
}
