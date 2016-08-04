import java.io.File;


public class GBK2UTF {
    String path="";
    public static void main(String[] args) {
         
    }
    
    static void replace(String path){
        File file=new File(path);
        if(file.isDirectory()){
            for(File child:file.listFiles()){
                replace(child.getAbsolutePath());
            }
        }
    }
}
