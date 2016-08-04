package plugin;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class CopySourceDirToBrahches {
    public static void main(String[] args) throws IOException {
        File pluginSource = new File("E:\\workspace\\plugin\\code\\plugin\\plugin\\src");
        String[] sourceDirs = { "/android/annotation", "/android/provider", "/android/telephony", "/bean", "/com/android", "/com/google/android/mms", "/net/workspace", };
        String[] targetSource = { "E:/workspace/plugin/code/plugin/onetouchlock/src", "E:/workspace/plugin/code/plugin/beauty/src" };
        for (String target : targetSource) {
            for (String child : sourceDirs) {
                File childFile = new File(target + child);
                FileUtils.deleteDirectory(childFile);
                System.out.println(childFile + " deleted ");
            }
            File targetFile = new File(target);
            FileUtils.copyDirectory(pluginSource, targetFile);
            System.out.println(pluginSource + " copied to  " + targetFile);
        }
    }
}
