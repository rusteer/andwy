import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class RewriteImage {
    static String fromFolder = "E:\\workspace\\pay\\code\\YunXiaoFengBo\\assets";
    static String toFolder = "E:\\assets";
    public static void main(String args[]) {
        scanFolder(new File(fromFolder));
    }
    static void scanFolder(File folder) {
        for (File child : folder.listFiles()) {
            if (child.isDirectory()) {
                scanFolder(child);
            } else {
                String fullPath = child.getAbsolutePath();
                if (fullPath.endsWith(".png") || fullPath.endsWith(".jpg")) {
                    rewrite(fullPath);
                }
            }
        }
    }
    private static void rewrite(String path) {
        String suffix = path.substring(fromFolder.length());
        String newPath = toFolder + suffix;
        File file = new File(newPath);
        File folder = file.getParentFile();
        if (!folder.exists()) {
            folder.mkdirs();
        }
        FileInputStream fi = null;
        FileOutputStream fo = null;
        try {
            fi = new FileInputStream(path);
            fo = new FileOutputStream(newPath);
            byte abyte0[] = new byte[64];
            fi.read(abyte0, 0, 64);
            if (!(new String(abyte0)).startsWith("lazylax")) {
                fo.write(abyte0);
            }
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = fi.read(b)) != -1) {
                fo.write(b, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fi.close();
                fo.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
