package plugin;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class CopyBeanDirToWeb {
    public static void main(String[] args) throws IOException {
        File pluginSource = new File("E:\\workspace\\plugin\\code\\plugin\\plugin\\src\\bean");
        String[] targetSource = { "E:/workspace/plugin/code/web/src/main/java/bean" };
        for (String target : targetSource) {
            File targetFile = new File(target);
            FileUtils.deleteDirectory(targetFile);
            FileUtils.copyDirectory(pluginSource, targetFile);
        }
    }
}
