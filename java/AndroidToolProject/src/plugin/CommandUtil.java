package plugin;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import plugin.function.test.TextUtils;

public class CommandUtil {
    public static int exeCmd(String cmd, StringBuilder cmdOut, StringBuilder cmdError) {
        System.out.println("start cmd:" + cmd);
        cmdOut.setLength(0);
        cmdError.setLength(0);
        int result = 0;
        try {
            Runtime run = Runtime.getRuntime();
            Process p = run.exec(cmd);
            BufferedReader inBr = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String lineStr;
            while ((lineStr = inBr.readLine()) != null) {
                System.out.println(lineStr);
                cmdOut.append(lineStr).append('\n');
            }
            while ((lineStr = stdError.readLine()) != null) {
                System.err.println(lineStr);
                cmdError.append(lineStr).append('\n');
            }
            if (p.waitFor() != 0) {
                result = p.exitValue();
                if (result != 0) {
                    System.err.println("Error occurs while executing cmd {" + cmd + "} and the return value is " + result);
                }
            }
            inBr.close();
            stdError.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("end cmd:" + cmd);
        return result;
    }
    public static String searchEnclosed(String target, String left, String right) {
        return searchEnclosed(target, left, right, 0);
    }
    public static String searchEnclosed(String target, String left, String right, int matcherIndex) {
        int index = 0;
        int leftPlace = 0;
        int rightPlace = 0;
        int fromPlace = 0;
        while (leftPlace >= 0 && rightPlace >= 0) {
            leftPlace = target.indexOf(left, fromPlace);
            if (leftPlace >= 0) {
                fromPlace = leftPlace + left.length() + 1;
                rightPlace = target.indexOf(right, fromPlace);
                if (rightPlace > 0 && (matcherIndex == -1 || matcherIndex == index++)) {//
                    return target.substring(leftPlace + left.length(), rightPlace);
                }
                fromPlace = rightPlace + right.length() + 1;
            }
        }
        return null;
    }
}
