package com.util;
import java.io.File;
import org.apache.commons.io.FileUtils;

public class RemoveDebugger {
    public static void main(String args[]) throws Exception {
        remove("E:/workspace/plugin/code/plugin/plugin", "MyLogger.");
    }
    public static void remove(String path, String debugName) throws Exception {
        for (File file : new File(path).listFiles()) {
            String childpath = file.getAbsolutePath();
            if (file.isDirectory()) {
                remove(childpath, debugName);
            } else if (childpath.endsWith(".java")) {
                StringBuilder sb = new StringBuilder();
                for (String line : FileUtils.readLines(file, "utf-8")) {
                    if (line.trim().startsWith(debugName)) continue;
                    sb.append(line).append("\n");
                }
                FileUtils.write(file, sb.toString());
            }
        }
    }
}
