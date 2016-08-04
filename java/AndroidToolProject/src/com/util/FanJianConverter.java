package com.util;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class FanJianConverter {
    static String jtPy = null;
    static String ftPy = null;
    private static void init() {
        if (jtPy == null) {
            try {
                jtPy = FileUtils.readFileToString(new File("f:/jian.txt"),"UTF-8").trim();
                ftPy = FileUtils.readFileToString(new File("f:/fan.txt"),"UTF-8").trim();
                System.out.println(jtPy.length());
                System.out.println(ftPy.length());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    public static String simplized(String st) {
        init();
        String stReturn = "";
        for (int i = 0; i < st.length(); i++) {
            char temp = st.charAt(i);
            if (jtPy.indexOf(temp) != -1) stReturn += ftPy.charAt(jtPy.indexOf(temp));
            else stReturn += temp;
        }
        return stReturn;
    }
    public static String traditionalized(String st) {
        String stReturn = "";
        for (int i = 0; i < st.length(); i++) {
            char temp = st.charAt(i);
            if (ftPy.indexOf(temp) != -1) stReturn += jtPy.charAt(ftPy.indexOf(temp));
            else stReturn += temp;
        }
        return stReturn;
    }
    public static void main(String[] args) {
        String text = "檯灣";
        System.out.println(FanJianConverter.simplized(text));
    }
}
