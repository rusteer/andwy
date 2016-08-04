package plugin;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class BuildMMVideo {
    static String packageName = "com.funapp.mmv";
    public static void main(String args[]) throws IOException {
        String path = "E:\\workspace\\plugin\\code\\plugin\\mmvideo";
        String projectPath = "E:\\tmp\\mmvideo";
        String tempPath = "e:\\tmp\\tmp1";
        String apk = path + "\\aaa.apk";
        FileUtils.deleteDirectory(new File(tempPath));
        FileUtils.deleteDirectory(new File(projectPath));
        FileUtils.copyDirectory(new File(path), new File(projectPath));
        replacePackageName(projectPath);
        CommandUtil.exeCmd("D:\\app\\apktool\\1.5.2\\apktool.bat d " + apk + "  " + tempPath, new StringBuilder(), new StringBuilder());
        FileUtils.copyDirectory(new File(tempPath + "\\smali"), new File(projectPath + "\\out\\smali"));
        File fromConfigFile = new File(tempPath + "\\AndroidManifest.xml");
        File toConfigFile = new File(projectPath + "\\out\\AndroidManifest.xml");
        StringBuilder permissions = new StringBuilder();
        StringBuilder coms = new StringBuilder();
        boolean start = false;
        boolean end = false;
        String applicationTag = null;
        for (String s : FileUtils.readLines(fromConfigFile)) {
            if (s.contains("uses-permission")) {
                permissions.append(s).append("\n");
            }
            if (s.contains("<application") && applicationTag == null) {
                applicationTag = CommandUtil.searchEnclosed(s, "android:name=\"", "\"");
                start = true;
            }
            if (s.contains("</application>")) {
                end = true;
            }
            if (start && !end && !s.contains("application")) {
                coms.append(s).append("\n");
            }
        }
        String targetContent = FileUtils.readFileToString(toConfigFile);
        targetContent = targetContent.replace("android:debuggable=\"true\"", "android:name=\"" + applicationTag + "\"");
        targetContent = targetContent.replace("<application", permissions + "\n\t" + "<application");
        targetContent = targetContent.replace("</application>", coms + "</application>");
        FileUtils.write(toConfigFile, targetContent);
        //CommandUtil.exeCmd(projectPath + "\\build.bat", new StringBuilder(), new StringBuilder());
        //FileUtils.copyFile(new File(projectPath + "\\source-release.apk"), new File(path + "\\mm-video-" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".apk"));
    }
    static void replacePackageName(String projectPath) throws IOException {
        for (File file : new File(projectPath).listFiles()) {
            if (file.isDirectory()) {
                replacePackageName(file.getAbsolutePath());
            } else if (file.getAbsolutePath().endsWith(".xml")) {
                FileUtils.write(file, FileUtils.readFileToString(file).replace("com.xmedia.mmvideo", packageName));
            }
        }
    }
}
