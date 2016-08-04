package smali;

import java.io.File;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class DomReplace {
    public static void main(String[] args) throws  Exception{
        File sourceFile=new File("E:/workspace/wg/smali/projects/10/calc/out/AndroidManifest.xml");
        SAXReader saxReader = new SAXReader();
        Document doc = saxReader.read(sourceFile);
        Element element=doc.getRootElement();
        //element.setAttributeValue(arg0, arg1);
        System.out.println(element.asXML());
        Attribute attr=element.attribute("android:versionCode");
        //element.setAttributeValue(arg0, arg1);
        //attr.setValue("100");
        element.setAttributeValue("android:versionName", "1.9.0");
        element.setAttributeValue("package", "com.wg");
        System.out.println(element.asXML());
        //android:versionCode="1" android:versionName="1.0" android:installLocation="auto" package="com.wg.tools.calc.pro"
       // xmlns:android="http://schemas.android.com/apk/res/android">
        //Node node=doc.getRootElement().selectSingleNode("uses-permission[@android:name='android.permission.WRITE_EXTERNAL_STORAGE']");
        //System.out.println(node);
    }
}
