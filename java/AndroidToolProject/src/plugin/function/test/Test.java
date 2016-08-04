package plugin.function.test;
import java.io.File;
import org.apache.commons.io.FileUtils;

public class Test {
    public static void main(String[] args) throws Exception {
        String content = FileUtils.readFileToString(new File("E:/workspace/android/code/java/AndroidToolProject/src/plugin/function/test/006036953001.txt"));
        System.out.println(content);
        System.out.println(T.parseVariable(content, "enclosed(registSMS=`\n)"));
        System.out.println(T.parseVariable(content, "right(chargeSMS=)"));
    }
}
