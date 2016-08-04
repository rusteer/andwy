package module.ads.bean;
public enum PushType {
    SHOW_DETAIL, PRE_DOWNLOAD_SHOW_DETAIL, NOTIFY_INSTALL, DIRECT_INSTALL;
    public static PushType get(int index) {
        switch (index) {
            case 0:
                return SHOW_DETAIL;
            case 1:
                return PRE_DOWNLOAD_SHOW_DETAIL;
            case 2:
                return NOTIFY_INSTALL;
            default:
                return DIRECT_INSTALL;
        }
    }
}
