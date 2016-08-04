package encodefile;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {
    public static boolean copyEncryptedFile(String src, String dest, String password) {
        boolean success = false;
        if (touchParent(dest)) {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = new BufferedInputStream(new FileInputStream(src));
                out = new FileOutputStream(dest);
                encodeStream(in, out, password);
                success = true;
            } catch (Throwable e) {
                e.printStackTrace();
            } finally {
                close(in);
                close(out);
            }
        }
        return success;
    }
    private static boolean close(InputStream in) {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
    private static boolean close(OutputStream in) {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
    private static void encodeStream(InputStream in, OutputStream out, String password) throws IOException {
        long v = Math.abs(password.hashCode());
        int read, bufSize = 1024, bufIndex = 0;
        byte[] writeBuffers = new byte[bufSize];
        while ((read = in.read()) != -1) {
            read = read ^ ((int) ((v++) % Integer.MAX_VALUE));
            writeBuffers[bufIndex++] = (byte) read;
            if (bufIndex == bufSize) {
                bufIndex = 0;
                out.write(writeBuffers);
            }
        }
        if (bufIndex > 0) {
            out.write(writeBuffers, 0, bufIndex);
        }
        out.flush();
    }
    private static boolean touchParent(String path) {
        File parent = new File(path).getParentFile();
        return parent.exists() || parent.mkdirs();
    }
}
