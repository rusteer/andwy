import java.io.File;


public class Rename {
    static String path="E:\\workspace\\union\\code\\android\\PluginAds";
    static String OLD_PREFIX="";
    static String NEW_PREFIX="";
    public static void main(String[] args){
        replace(path,OLD_PREFIX,NEW_PREFIX);
    }
    
    
    static void replace(String path,String oldPrefix,String newPrefix){
        File rootFile=new File(path);
        if(!rootFile.exists()) return;
        if(rootFile.isFile()){
            String name=rootFile.getName();
            if(name.startsWith(oldPrefix)){
                String newPath=rootFile.getParent()+"\\"+name.replaceFirst(oldPrefix,newPrefix);
                rootFile.renameTo(new File(newPath));
                System.out.println( newPath);
            }
            return;
        }
        if(rootFile.isDirectory()){
            File list[]=rootFile.listFiles();
            for(File child:list){
                replace(child.getAbsolutePath(),oldPrefix,newPrefix);
            } 
        }
        
    }
}
