package module.ads.bean;
public enum SpriteType {
    KUZAI, KUXUAN;
    public static SpriteType get(int style) {
        return style == 0 ? KUZAI : KUXUAN;
    }
}
