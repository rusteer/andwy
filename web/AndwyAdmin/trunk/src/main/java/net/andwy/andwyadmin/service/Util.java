package net.andwy.andwyadmin.service;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.UUID;

public class Util {
    public static String generateUUID() {
        return UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
    }
    public static String getStack(Throwable e) {
        String result = null;
        if (e != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            result = sw.toString();
            try {
                sw.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return result;
    }
    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }
}
