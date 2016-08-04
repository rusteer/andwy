package module.ads.bean;
public enum CAppType {
    APP, GAME;
    public static CAppType get(int i) {
        if (i == 0) return APP;
        if (i == 1) return GAME;
        return null;
    }
}