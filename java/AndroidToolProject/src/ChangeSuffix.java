import java.io.File;


public class ChangeSuffix {
    public static void main(String[] args){
        String path="E:/workspace/wg/code/android/biz/AndroidLibBiz/assets/lib/modules";
        for(File file:new File(path).listFiles()){
            file.renameTo(new File(file.getAbsolutePath().replace(".xml", ".dat")));
        }
    }
}
