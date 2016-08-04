package net.andwy.andwyadmin.service.admin;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.andwy.andwyadmin.entity.admin.Package;
import net.andwy.andwyadmin.entity.admin.Product;
import net.andwy.andwyadmin.service.Constants;
import net.andwy.andwyadmin.service.ServiceError;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BinaryBuilder extends AbstractBuilder {
    private SAXReader saxReader = new SAXReader();
    private String smaliRoot = Constants.WORKSPACE_BASE + "/smali";
    private String injectRoot = smaliRoot + "/injector";
    public ServiceError execute(Package pkg) {
        ServiceError error = null;
        try {
            Product product = pkg.getProduct();
            String projectPath = Constants.WORKSPACE_BASE + "/smali/projects/" + product.getProjectName();
            File buildDir = new File(serverSetting.buildPath + "/" + product.getProjectType() + "/" + product.getProjectName() + "/" + pkg.getId());
            if (buildDir.exists()) FileUtils.deleteDirectory(buildDir);
            FileUtils.copyDirectory(new File(projectPath), buildDir);
            String buildPath = buildDir.getAbsolutePath();
            if (error == null && "Y".equals(pkg.getInjectAds())) error = injectAds(buildPath);
            if (error == null) error = replacePackageName(pkg, buildPath);
            if (error == null) error = updateProjectInfo(pkg, buildPath);
            if (error == null) error = apktoolBuild(pkg, buildPath);
            if (error == null) error = zipAlign(pkg, buildPath);
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            error = getBuildError("UN-DEFINED-ERROR", e);
        }
        return error;
    }
    Map<String, String> replaceMap = getReplaces();
    private Map<String, String> getReplaces() {
        Map<String, String> replaceMap = new HashMap<String, String>();
        replaceMap.put("android/app/Activity", "android/support/v4/jni/Activity");
        replaceMap.put("android/app/ListActivity", "android/support/v4/jni/ListActivity");
        replaceMap.put("android/preference/PreferenceActivity", "android/support/v4/jni/PreferenceActivity");
        //replaceMap.put("android/app/Service", "android/support/v4/jni/Service");
        replaceMap.put("android/app/TabActivity", "android/support/v4/jni/TabActivity");
        return replaceMap;
    }
    private ServiceError injectAds(String projectRoot) {
        ServiceError error = null;
        String resFolder = "/out/res";
        String valuesFolder = resFolder + "/values";
        String publicXmlpath = valuesFolder + "/public.xml";
        String smaliFolder = "/out/smali";
        error = copyResFiles(projectRoot + resFolder, injectRoot + resFolder);
        if (error == null) error = mergeResFilesInValueFolder(projectRoot + valuesFolder, injectRoot + valuesFolder);
        if (error == null) error = mergePublicXml(projectRoot + publicXmlpath, injectRoot + publicXmlpath);
        if (error == null) error = replaceSuperClassInSmaliFiles(projectRoot + smaliFolder);
        if (error == null) error = copyInjectedSmaliFiles(projectRoot, smaliFolder);
        if (error == null) error = mergeManifest(projectRoot);
        if (error == null) error = copyAssets(projectRoot);
        return error;
    }
    protected ServiceError copyAssets(String projectRoot) {
        ServiceError error = null;
        File assetFolder = new File(ASSET_FOLDER_PATH);
        if (assetFolder.exists()) {
            try {
                File target = new File(projectRoot + "/out/assets");
                logger.info("Copy assets from " + assetFolder.getAbsolutePath() + " to " + target.getAbsolutePath());
                FileUtils.copyDirectory(assetFolder, target);
            } catch (IOException e) {
                error = getBuildError("copyAssets-error", e);
            }
        }
        return error;
    }
    public ServiceError replacePackageName(Package pkg, String projectPath) {
        ServiceError error = replaceSchamaInXmlFiles(pkg, projectPath);
        if (error == null) error = replacePackageNameInSmaliFiles(pkg, projectPath);
        return error;
    }
    private ServiceError replacePackageNameInSmaliFiles(Package pkg, String projectPath) {
        ServiceError error = null;
        String fromPackage = pkg.getProduct().getBasePackage().replaceAll("\\.", "\\\\.");
        String toPackage = pkg.getPackageName();
        String path = projectPath + "/out/smali";
        try {
            this.replacePackageNameInSmaliFiles(fromPackage, toPackage, path);
        } catch (Throwable e) {
            error = getBuildError("replacePackageNameInSmaliFiles-error", e);
        }
        return error;
    }
    /**
     * Replease the package name with new package name, some app will check it
     * @param fromPackage
     * @param toPackage
     * @param path
     * @throws Exception
     */
    private void replacePackageNameInSmaliFiles(String fromPackage, String toPackage, String path) throws Exception {
        //schemas.android.com/apk/res/net.daum.android.solcalendar
        File file = new File(path);
        if (file.isFile() && file.getName().endsWith(".smali")) {
            String content = FileUtils.readFileToString(file);
            if (content.matches("[\\d\\D]*" + fromPackage + "[\\d\\D]*")) {
                FileUtils.write(file, content.replaceAll(fromPackage, toPackage));
                logger.info(file.getAbsolutePath() + " schemas updated");
            }
        } else if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                replacePackageNameInSmaliFiles(fromPackage, toPackage, child.getAbsolutePath());
            }
        }
    }
    private ServiceError replaceSchamaInXmlFiles(Package pkg, String projectPath) {
        ServiceError error = null;
        String fromSchemaName = "schemas.android.com/apk/res/" + pkg.getProduct().getBasePackage();
        String targetSchemaName = "schemas.android.com/apk/res/" + pkg.getPackageName();
        String path = projectPath + "/out/res";
        try {
            this.replaceSchamaInXmlFiles(fromSchemaName, targetSchemaName, path);
        } catch (Throwable e) {
            error = getBuildError("replaceSchamaInXmlFiles-error", e);
        }
        return error;
    }
    private void replaceSchamaInXmlFiles(String fromSchemaName, String targetSchemaName, String path) throws Exception {
        //schemas.android.com/apk/res/net.daum.android.solcalendar
        File file = new File(path);
        if (file.isFile() && file.getName().endsWith(".xml")) {
            String content = FileUtils.readFileToString(file);
            if (content.contains(fromSchemaName)) {
                FileUtils.write(file, content.replaceAll(fromSchemaName, targetSchemaName));
                logger.info(file.getAbsolutePath() + " schemas updated");
            }
        } else if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                replaceSchamaInXmlFiles(fromSchemaName, targetSchemaName, child.getAbsolutePath());
            }
        }
    }
    private ServiceError zipAlign(Package pkg, String buildPath) {
        ServiceError error = null;
        String signed = buildPath + "/out/source-release.apk";
        String targetApk = pkgService.getApkLocation(pkg);
        File targetFile = new File(targetApk);
        if (targetFile.exists()) targetFile.delete();
        File dir = targetFile.getParentFile();
        if (!dir.exists()) dir.mkdirs();
        String zipAlignCommand = "zipalign -v 4 " + signed + " " + targetApk;
        if (BuildService.exeCmd(zipAlignCommand, cmdOut, cmdError) != 0) {
            error = new ServiceError("ZIP_ALIGN-ERROR", cmdError.toString());
        }
        logger.info(signed + " copied to" + targetApk);
        return error;
    }
    private ServiceError apktoolBuild(Package pkg, String projectPath) throws Exception {
        ServiceError error = null;
        final String projectOutPath = projectPath + "/out";
        String cmd = Constants.apktoolCmd + " b " + projectOutPath;
        if (BuildService.exeCmd(cmd, cmdOut, cmdError) != 0) error = new ServiceError("apktool b error", cmdError.toString());
        if (error == null) {
            String keyStoreLocation = Constants.WORKSPACE_BASE + "/smali/keystore/smali.keystore";
            String storePass = "wodemima";
            String alais = "main";
            String projectKeystore = projectPath + "/keystore";
            if (new File(projectKeystore).exists()) {
                keyStoreLocation = projectKeystore;
                storePass = "123456";
                alais = "myalias";
            }
            String unsignedApk = projectOutPath + "/dist/source.apk";
            String targetApk = projectOutPath + "/source-release.apk";
            cmd = String.format("%s -verbose -storepass %s -keystore %s -signedjar %s %s %s", Constants.jarsignerCmd, storePass, keyStoreLocation, targetApk, unsignedApk, alais);
            // cmd = Constants.jarsignerCmd + " -verbose -storepass " + storePass + " -keystore " + keyStoreLocation + " -signedjar " + targetApk + " " + unsignedApk + " main";
            if (BuildService.exeCmd(cmd.toString(), cmdOut, cmdError) != 0) error = new ServiceError("jarsigner error", cmdError.toString());
        }
        return error;
    }
    public ServiceError updateProjectInfo(Package pkg, String projectPath) throws Exception {
        ServiceError error = null;
        final String projectOutPath = projectPath + "/out";
        String configFile = projectOutPath + "/AndroidManifest.xml";
        String projectIconName = "icon.png";
        if (StringUtils.isNotBlank(pkg.getProduct().getIconResource())) projectIconName = pkg.getProduct().getIconResource();
        if (!projectIconName.endsWith(".png")) projectIconName += ".png";
        String projectIconPath = projectOutPath + "/res/drawable/" + projectIconName;
        String projectStringXmlPath = projectOutPath + "/res/values/strings.xml";
        String versionName = getVersionName(pkg.getProduct());
        String pkgName = pkg.getPackageName();
        String versionCode = getVersionCode(pkg.getProduct());
        //Replease umeng channel
        replaceAttributes(configFile, //
                new ReplaceModel("android:versionName=\"", "\"", versionName), //
                new ReplaceModel("android:versionCode=\"", "\"", versionCode),//
                new ReplaceModel("package=\"", "\"", pkgName));
        String appNameLabel = "app_name";
        if (StringUtils.isNotBlank(pkg.getProduct().getAppNameResource())) {
            appNameLabel = pkg.getProduct().getAppNameResource();
        }
        String channel = pkg.getMarketAccount().getMarket().getShortName().toLowerCase() + pkg.getMarketAccount().getDeveloper().getShortName().toUpperCase();
        if (!"Y".equals(pkg.getInjectAds())) {
            channel = channel + "clear";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(pkg.getPackageProductName());
        sb.append("</string>");
        sb.append(this.getBizStrings(pkg));
        sb.append("<string name=\"abcdefgdopqrst\">");
        replaceAttributes(projectStringXmlPath, new ReplaceModel("<string name=\"" + appNameLabel + "\">", "</string>", sb.toString()));
        String pkgIconPath = pkgService.getIconPath(pkg);
        if (new File(pkgIconPath).exists()) {
            FileUtils.copyFile(new File(pkgIconPath), new File(projectIconPath));
        }
        return error;
    }
    private ServiceError mergeManifest(String projectRoot) {
        ServiceError error = null;
        try {
            String suffix = "/out/AndroidManifest.xml";
            File sourceFile = new File(injectRoot + suffix);
            File targetFile = new File(projectRoot + suffix);
            Document sourceDocument = saxReader.read(sourceFile);
            Document targetDocument = saxReader.read(targetFile);
            Set<String> set = new HashSet<String>();
            Element targetRoot = targetDocument.getRootElement();
            Element applicationRoot = null;
            for (Iterator<?> i = targetRoot.elementIterator(); i.hasNext();) {
                Element element = (Element) i.next();
                String name = element.getName();
                if (name.equalsIgnoreCase("application")) {
                    applicationRoot = element;
                } else {
                    String xml = element.asXML().trim();
                    set.add(xml);
                }
            }
            for (Iterator<?> i = applicationRoot.elementIterator(); i.hasNext();) {
                Element element = (Element) i.next();
                String xml = element.asXML().trim();
                set.add(xml);
            }
            boolean needUpdate = false;
            for (Iterator<?> i = sourceDocument.getRootElement().elementIterator(); i.hasNext();) {
                Element element = (Element) i.next();
                String name = element.getName();
                if (name.equalsIgnoreCase("application")) {
                    for (Iterator<?> j = element.elementIterator(); j.hasNext();) {
                        element = (Element) j.next();
                        String xml = element.asXML().trim();
                        if (!set.contains(xml)) {
                            needUpdate = true;
                            applicationRoot.add(element.createCopy());
                        }
                    }
                } else {
                    String xml = element.asXML().trim();
                    if (!set.contains(xml)) {
                        needUpdate = true;
                        targetRoot.add(element.createCopy());
                    }
                }
            }
            if (needUpdate) {
                FileUtils.write(targetFile, targetDocument.asXML());
            }
        } catch (Throwable e) {
            error = getBuildError("mergeManifest-error", e);
        }
        return error;
    }
    private ServiceError copyInjectedSmaliFiles(String projectRoot, String smaliFolder) {
        ServiceError error = null;
        try {
            File sourceFolder = new File(injectRoot + smaliFolder);
            File targetFolder = new File(projectRoot + smaliFolder);
            FileUtils.copyDirectory(sourceFolder, targetFolder);
            logger.info("Merged smali packages from " + sourceFolder.getAbsolutePath() + " to " + targetFolder.getAbsolutePath());
        } catch (IOException e) {
            error = getBuildError("copySmaliFiles-error", e);
        }
        return error;
    }
    private ServiceError replaceSuperClassInSmaliFiles(String root) {
        ServiceError error = null;
        try {
            logger.info("Replace super classes begin");
            startReplace(root);
            logger.info("Replace super classes end");
        } catch (Throwable e) {
            error = getBuildError("replaceSuperClass-error", e);
        }
        return error;
    }
    private void startReplace(String root) throws Exception {
        File file = new File(root);
        if (file.isDirectory()) {
            for (File sub : file.listFiles()) {
                startReplace(sub.getAbsolutePath());
            }
        } else if (file.getName().endsWith(".smali")) {
            //String content = FileUtils.readFileToString(file);
            StringBuilder content = new StringBuilder();
            List<String> lines = FileUtils.readLines(file);
            String secondLine = lines.get(1).trim();
            String superClass = lines.get(1).substring(".super L".length(), secondLine.length() - 1);
            //logger.info(superClass);
            if (replaceMap.containsKey(superClass)) {
                boolean needUpdate = false;
                for (String line : lines) {
                    //(line.contains(".super L") || line.contains("invoke-super")) && 
                    /*boolean ignore = line.contains("##noreplace##") //
                            || line.contains("invoke-static") //
                            || line.contains(superClass + ";)")//
                            || line.contains("(L" + superClass);
                    */
                    if (line.contains("L" + superClass + ";->") || line.contains(".super") && line.contains(superClass)) {
                        line = line.replaceAll(superClass, replaceMap.get(superClass));
                        needUpdate = true;
                    }
                    content.append(line).append("\n");
                }
                if (needUpdate) {
                    FileUtils.write(file, content.toString());
                    logger.info(file.getAbsolutePath() + " updated");
                }
            }
        }
    }
    static String intToHex(int num) {
        String sResult = "";
        while (num > 0) {
            int m = num % 16;
            if (m < 10) sResult = (char) ('0' + m) + sResult;
            else sResult = (char) ('A' + m - 10) + sResult;
            num = num / 16;
        }
        return "0x" + sResult.toLowerCase();
    }
    private ServiceError copyResFiles(String targetResRoot, String injectResRoot) {
        ServiceError error = null;
        try {
            File injectRoot = new File(injectResRoot);
            for (File subFolder : injectRoot.listFiles()) {
                String name = subFolder.getName();
                if (subFolder.isDirectory() && !name.startsWith("values")) {
                    File targetFolder = new File(targetResRoot + "/" + name);
                    if (!targetFolder.exists()) {
                        targetFolder.mkdirs();
                    }
                    for (File sourceFile : subFolder.listFiles()) {
                        if (sourceFile.isFile()) {
                            File targetFile = new File(targetFolder.getAbsolutePath() + "/" + sourceFile.getName());
                            if (!targetFile.exists()) {
                                FileUtils.copyFileToDirectory(sourceFile, targetFolder);
                                logger.info(sourceFile.getCanonicalPath() + " copied to " + targetFolder.getCanonicalPath());
                            }
                        }
                    }
                }
            }
        } catch (Throwable e) {
            error = getBuildError("COPY-RES-FILES-ERROR", e);
        }
        return error;
    }
    private ServiceError mergeResFilesInValueFolder(String targetValueRoot, String injectValueRoot) {
        ServiceError error = null;
        try {
            File injectRoot = new File(injectValueRoot);
            for (File child : injectRoot.listFiles()) {
                String name = child.getName();
                if (name.endsWith("xml") && !name.equals("public.xml")) {
                    File targetFile = new File(targetValueRoot + "/" + name);
                    Document sourceDocument = saxReader.read(child);
                    XMLWriter xmlWriter = null;
                    if (targetFile.exists()) {
                        //append content to target
                        Document targetDocument = saxReader.read(targetFile);
                        Element targetRoot = targetDocument.getRootElement();
                        Set<String> set = new HashSet<String>();
                        for (Iterator<?> i = targetRoot.elementIterator(); i.hasNext();) {
                            Element element = (Element) i.next();
                            String xml = element.asXML().trim();
                            set.add(xml);
                        }
                        boolean needUpdate = false;
                        for (Iterator<?> i = sourceDocument.getRootElement().elementIterator(); i.hasNext();) {
                            Element element = (Element) i.next();
                            String xml = element.asXML().trim();
                            if (!set.contains(xml)) {
                                //targetRoot.addElement(element);
                                targetRoot.add(element.createCopy());
                                needUpdate = true;
                            }
                        }
                        if (needUpdate) {
                            xmlWriter = new XMLWriter(new FileWriter(targetFile));
                            xmlWriter.write(targetDocument);
                        }
                        logger.info(targetFile.getAbsolutePath() + " " + (needUpdate ? "updated" : "not updated"));
                        //logger.info(targetDocument.asXML());
                        //logger.info(targetFile.getAbsolutePath());
                    } else {
                        FileUtils.copyFile(child, targetFile);
                        //xmlWriter = new XMLWriter(new FileWriter(targetFile));
                        //xmlWriter.write(sourceDocument);
                        logger.info(child.getCanonicalPath() + " copied to " + targetFile.getCanonicalPath());
                    }
                    if (xmlWriter != null) {
                        xmlWriter.close();
                    }
                }
            }
        } catch (Throwable e) {
            error = getBuildError("mergeResFilesInValueFolder-ERROR", e);
        }
        return error;
    }
    private ServiceError mergePublicXml(String targetFile, String injectFile) {
        ServiceError error = null;
        Map<String, Map<String, Integer>> result = new HashMap<String, Map<String, Integer>>();
        File targetXml = new File(targetFile);
        XMLWriter xmlWriter = null;
        Map<String, Integer> maxResourceIdMap = new HashMap<String, Integer>();
        Map<String, Set<String>> allTypeNames = new HashMap<String, Set<String>>();
        try {
            Document targetDocument = saxReader.read(targetXml);
            Element targetRoot = targetDocument.getRootElement();
            //logger.info(targetDocument.asXML().length());
            for (Iterator<?> i = targetRoot.elementIterator(); i.hasNext();) {
                Element child = (Element) i.next();
                String type = child.attributeValue("type");
                String name = child.attributeValue("name");
                int id = Integer.valueOf(child.attributeValue("id").substring(2), 16);
                setMaxResourceId(maxResourceIdMap, type, id);
                Set<String> typeNames = allTypeNames.get(type);
                if (typeNames == null) {
                    typeNames = new HashSet<String>();
                    allTypeNames.put(type, typeNames);
                }
                typeNames.add(name);
            }
            //logger.info(targetDocument.asXML().length());
            //logger.info(maxValues);
            Document injectDocument = saxReader.read(new File(injectFile));
            boolean needUpdate = false;
            for (Iterator<?> i = injectDocument.getRootElement().elementIterator(); i.hasNext();) {
                Element child = (Element) i.next();
                String type = child.attributeValue("type");
                String name = child.attributeValue("name");
                Map<String, Integer> map = result.get(type);
                if (map == null) {
                    map = new HashMap<String, Integer>();
                    result.put(type, map);
                }
                setResourceId(maxResourceIdMap, child);
                if (!allTypeNames.containsKey(type) || !allTypeNames.get(type).contains(name)) {
                    targetRoot.add(child.createCopy());
                    needUpdate = true;
                }
            }
            //logger.info(targetDocument.asXML());
            if (needUpdate) {
                xmlWriter = new XMLWriter(new FileWriter(targetXml));
                xmlWriter.write(targetDocument);
            }
            logger.info(targetXml.getAbsolutePath() + " " + (needUpdate ? "updated" : "not updated"));
        } catch (Throwable e) {
            error = getBuildError("mergePublicXml-error", e);
        } finally {
            try {
                if (xmlWriter != null) xmlWriter.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return error;
    }
    @SuppressWarnings("deprecation")
    private void setResourceId(Map<String, Integer> maxResourceIdMap, Element child) {
        int id = Integer.valueOf(child.attributeValue("id").substring(2), 16);
        String type = child.attributeValue("type");
        if (type.equals("anim")) type = "xml";
        if (maxResourceIdMap.containsKey(type)) {
            id = maxResourceIdMap.get(type) + 1;
            maxResourceIdMap.put(type, id);
        }
        child.setAttributeValue("id", intToHex(id));
    }
    private void setMaxResourceId(Map<String, Integer> maxResourceIdMap, String type, int id) {
        if (type.equals("anim")) type = "xml";
        int maxResourceId = 0;
        if (maxResourceIdMap.containsKey(type)) {
            maxResourceId = maxResourceIdMap.get(type);
        }
        if (id > maxResourceId) maxResourceIdMap.put(type, id);
    }
    @Autowired
    PackageService pkgService;
}
