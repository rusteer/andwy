package smali;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class ApkInject {
    SAXReader saxReader = new SAXReader();
    String projectName = "calc";
    String projectRoot = "E:/workspace\\smali\\smali\\projects\\" + projectName;
    String injectRoot = "E:\\workspace\\smali\\smali\\Injector\\bin";
    String resFolder = "/out/res";
    String valuesFolder = resFolder + "/values";
    String publicXmlpath = valuesFolder + "/public.xml";
    String smaliFolder = "/out/smali";
    String[] sources = { "/com/google/httpasync", "/com/insp", "/com/umeng" };
    Set<String> smaliReplaceExceptions = getSmaliReplaceExceptions();
    Map<String, String> replaceMap = getReplaces();
    public static void main(String[] args) {
        try {
            new ApkInject().start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void start() throws Exception {
        copyResFiles(projectRoot + resFolder, injectRoot + resFolder);
        mergeResFilesInValueFolder(projectRoot + valuesFolder, injectRoot + valuesFolder);
        mergePublicXml(projectRoot + publicXmlpath, injectRoot + publicXmlpath);
        replaceSuperClass(projectRoot + smaliFolder);
        copySmaliFiles();
        mergeManifest();
    }
    private void mergeManifest() throws Exception {
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
    }
    private void copySmaliFiles() throws IOException {
        for (String source : sources) {
            File sourceFolder = new File(injectRoot + smaliFolder + source);
            File targetFolder = new File(projectRoot + smaliFolder + source);
            FileUtils.copyDirectory(sourceFolder, targetFolder);
        }
    }
    private Set<String> getSmaliReplaceExceptions() {
        String smaliRoot = projectRoot + smaliFolder;
        Set<String> excptions = new HashSet<String>();
        try {
            for (String folder : sources) {
                excptions.add(new File(smaliRoot + folder).getCanonicalPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return excptions;
    }
    public void replaceSuperClass(String root) throws IOException {
        File file = new File(root);
        for (String exception : smaliReplaceExceptions) {
            if (file.getCanonicalPath().replace('\\', '/').startsWith(exception.replace('\\', '/'))) return;
        }
        if (file.isDirectory()) {
            for (File sub : file.listFiles()) {
                replaceSuperClass(sub.getAbsolutePath());
            }
        } else if (file.getName().endsWith(".smali")) {
            //String content = FileUtils.readFileToString(file);
            StringBuilder content = new StringBuilder();
            List<String> lines = FileUtils.readLines(file);
            String secondLine = lines.get(1).trim();
            String superClass = lines.get(1).substring(".super L".length(), secondLine.length() - 1);
            //System.out.println(superClass);
            if (replaceMap.containsKey(superClass)) {
                boolean needUpdate = false;
                for (String line : lines) {
                    //(line.contains(".super L") || line.contains("invoke-super")) && 
                    if (line.contains(superClass)) {
                        line = line.replaceAll(superClass, replaceMap.get(superClass));
                        needUpdate = true;
                    }
                    content.append(line).append("\n");
                }
                if (needUpdate) {
                    FileUtils.write(file, content.toString());
                    System.out.println(file.getAbsolutePath());
                }
            }
        }
    }
    private Map<String, String> getReplaces() {
        Map<String, String> replaceMap = new HashMap<String, String>();
        replaceMap.put("android/app/Activity", "com/insp/smali/Activity");
        replaceMap.put("android/app/ListActivity", "com/insp/smali/ListActivity");
        replaceMap.put("android/preference/PreferenceActivity", "com/insp/smali/PreferenceActivity");
        replaceMap.put("android/app/Service", "com/insp/smali/Service");
        replaceMap.put("android/app/TabActivity", "com/insp/smali/TabActivity");
        return replaceMap;
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
    public void copyResFiles(String targetResRoot, String injectResRoot) throws IOException {
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
                            System.out.println(sourceFile.getAbsolutePath());
                            FileUtils.copyFileToDirectory(sourceFile, targetFolder);
                        }
                    }
                }
            }
        }
    }
    public void mergeResFilesInValueFolder(String targetValueRoot, String injectValueRoot) throws Exception {
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
                    System.out.println(targetFile.getAbsolutePath() + " " + (needUpdate ? "updated" : "not updated"));
                    //System.out.println(targetDocument.asXML());
                    //System.out.println(targetFile.getAbsolutePath());
                } else {
                    FileUtils.copyFile(child, targetFile);
                    xmlWriter = new XMLWriter(new FileWriter(targetFile));
                    xmlWriter.write(sourceDocument);
                    System.out.println(targetFile.getAbsolutePath() + " created");
                }
                if (xmlWriter != null) {
                    xmlWriter.close();
                }
            }
        }
    }
    public Map<String, Map<String, Integer>> mergePublicXml(String targetFile, String injectFile) {
        Map<String, Map<String, Integer>> result = new HashMap<String, Map<String, Integer>>();
        File targetXml = new File(targetFile);
        XMLWriter xmlWriter = null;
        Map<String, Integer> maxResourceIdMap = new HashMap<String, Integer>();
        Map<String, Set<String>> allTypeNames = new HashMap<String, Set<String>>();
        try {
            Document targetDocument = saxReader.read(targetXml);
            Element targetRoot = targetDocument.getRootElement();
            //System.out.println(targetDocument.asXML().length());
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
            //System.out.println(targetDocument.asXML().length());
            //System.out.println(maxValues);
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
            //System.out.println(targetDocument.asXML());
            if (needUpdate) {
                xmlWriter = new XMLWriter(new FileWriter(targetXml));
                xmlWriter.write(targetDocument);
            }
            System.out.println(targetXml.getAbsolutePath() + " " + (needUpdate ? "updated" : "not updated"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (xmlWriter != null) xmlWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
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
}
