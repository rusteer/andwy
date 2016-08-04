package net.andwy.andwyadmin.service.admin;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import net.andwy.andwyadmin.entity.admin.Package;
import net.andwy.andwyadmin.entity.admin.Product;
import net.andwy.andwyadmin.service.Constants;
import net.andwy.andwyadmin.service.ServiceError;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SourceBuilder extends AbstractBuilder {
    @Autowired
    PackageService pkgService;
    public ServiceError execute(Package pkg) {
        ServiceError error = null;
        try {
            Product product = pkg.getProduct();
            String projectPath = Constants.WORKSPACE_BASE + "/code/android/published/" + product.getProjectName() + "/trunk";
            File buildDir = new File(serverSetting.buildPath + "/" + product.getProjectType() + "/" + product.getProjectName() + "/" + pkg.getId());
            if (buildDir.exists()) FileUtils.deleteDirectory(buildDir);
            FileUtils.copyDirectory(new File(projectPath), buildDir);
            String buildPath = buildDir.getAbsolutePath();
            String configFile = buildPath + "/AndroidManifest.xml";
            String rFile = buildPath + "/src/biz/AR.java";
            String pomFile = buildPath + "/pom.xml";
            String iconFile = buildPath + "/res/drawable/icon.png";
            String projectStringXml = buildPath + "/res/values/biz_strings.xml";
            String prugardWindowsTxt = Constants.WORKSPACE_BASE + "/code/android/biz/AndroidLibBiz/dictionaries/windows.txt";
            String prugardShakespeareTxt = Constants.WORKSPACE_BASE + "/code/android/biz/AndroidLibBiz/dictionaries/shakespeare.txt";
            error = generateProguardKeywors(keywords, prugardWindowsTxt, prugardShakespeareTxt);
            if (error != null) return error;
            replaceAttributes(projectStringXml, new ReplaceModel("<!--BuildInsertStart-->", "<!--BuildInsertEnd-->", getBizStrings(pkg)));
            String versionName = getVersionName(pkg.getProduct());
            String pkgName = pkg.getPackageName();
            String versionCode = getVersionCode(product);
            //Replease umeng channel
            replaceAttributes(configFile, //
                    new ReplaceModel("android:versionName=\"", "\"", versionName), //
                    new ReplaceModel("android:versionCode=\"", "\"", versionCode),//
                    new ReplaceModel("package=\"", "\"", pkgName));
            replaceAttributes(rFile, new ReplaceModel("import ", ".R;", pkgName));
            replaceAttributes(pomFile, new ReplaceModel("<version>", "</version>", versionName),//
                    new ReplaceModel("<groupId>", "</groupId>", pkgName));
            replaceAttributes(projectStringXml, new ReplaceModel("<string name=\"app_name\">", "</string>", pkg.getPackageProductName()));
            String pkgIconPath = pkgService.getIconPath(pkg);
            if (new File(pkgIconPath).exists()) {
                FileUtils.copyFile(new File(pkgIconPath), new File(iconFile));
            }
            if (error == null) {
                String cmd = Constants.mvnCmd + " -f \"" + pomFile + "\" clean install -Psign";
                if (BuildService.exeCmd(cmd, cmdOut, cmdError) != 0) {
                    error = new ServiceError("mavenCmdError", cmdError.toString());
                }
            }
            if (error == null) {
                File fromPath = new File(getMavenPath(pkg));
                File toPath = new File(pkgService.getApkLocation(pkg));
                if (toPath.exists()) toPath.delete();
                FileUtils.moveFile(fromPath, toPath);
                logger.info("Move file from " + fromPath.getCanonicalPath() + " to " + toPath.getCanonicalPath());
            }
            if (error == null) {
                File mavenFolder = new File(getMavenFolder(pkg));
                FileUtils.deleteDirectory(mavenFolder);
                logger.info("Deleted maven folder:" + mavenFolder.getCanonicalPath());
            }
        } catch (Throwable e) {
            error = getBuildError("UN-DEFINED-ERROR", e);
        }
        return error;
    }
    private String getMavenFolder(Package pkg) {
        String pkgName = pkg.getPackageName();
        String projectName = pkg.getProduct().getProjectName();
        return Constants.MAVEN_BASE + "/" + pkgName.replace('.', '/') + "/" + projectName;
    }
    private String getMavenPath(Package pkg) {
        String versionName = getVersionName(pkg.getProduct());
        String pkgName = pkg.getPackageName();
        String projectName = pkg.getProduct().getProjectName();
        String alignedTargetSuffix = "-aligned.apk";
        //String targetSuffix = ".apk";
        String path = Constants.MAVEN_BASE + "/" + pkgName.replace('.', '/') + "/" + projectName + "/" + versionName + "/" + projectName + "-" + versionName;
        return path + alignedTargetSuffix;
    }
    private static ServiceError generateProguardKeywors(String keywords, String prugardWindowsTxt, String prugardShakespeareTxt) {
        String sb = getProguardWords(keywords);
        try {
            FileUtils.write(new File(prugardWindowsTxt), sb);
            FileUtils.write(new File(prugardShakespeareTxt), sb);
        } catch (IOException e) {
            return getBuildError("generateProguardKeywors-error", e);
        }
        return null;
    }
    public static String getProguardWords(String keywords) {
        int maxLength = 8;
        int minLength = 4;
        char[] array = keywords.toCharArray();
        Set<String> set = new HashSet<String>();
        for (int i = 0; i < 100000; i++) {
            int length = maxLength;
            while (length-- > minLength) {
                StringBuilder sb = new StringBuilder();
                for (int j = 1; j <= length; j++) {
                    char c = array[(int) (Math.random() * array.length)];
                    String ss = String.valueOf(c);
                    sb.append(ss.trim());
                }
                set.add(sb.toString());
            }
            if (set.size() > 5000) break;
        }
        StringBuilder sb = new StringBuilder();
        for (String line : set) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }
    //خشسزرذو، يفقكلمنه
    static String keywords = "شسزرذو، يفقكلمنه";
    public static void main(String args[]) {
        System.out.println(getProguardWords(keywords));
    }
}
