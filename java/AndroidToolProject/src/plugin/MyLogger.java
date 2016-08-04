package plugin;
public class MyLogger {
    public static void debug(Object info) {
        System.out.println(info);
    }
    public static void debug(String key, Object value) {
        debug(key + ":" + value);
    }
    public static void error(String errorMsg) {
        System.err.println(errorMsg);
    }
    public static void error(Throwable e) {
        e.printStackTrace();
    }
    public static void info(Object info) {
        System.out.println(info == null ? null : info.toString());
    }
    private static final String TAG = /*const-replace-start*/"Tcccccccccc";
}
