package module.ads.bean;
import org.json.JSONException;
import org.json.JSONObject;
import common.bean.Json;

public class CApp extends Json {
    private static final String SPLITTER = String.valueOf((char) 29);
    public long id;
    public String description;
    public String apkUrl;
    public String name;
    public String uuid;
    public String pkgName;
    public String hint;
    public String appType;
    public int size;
    public String pkgVersionName;
    public int pkgVersionCode;
    public String iconUrl;
    public String screenUrl;
    public String provider;
    public String pushTitle;
    public String pushShortDescription;
    public int channel;
    public int pushType;
    public boolean pushOnWifi;
    public long saveTime;
    //used by client,no need to init from server side
    public boolean isInstalled;
    public boolean downloaded;
    public int appStatus;
    @Override
    protected void init(JSONObject obj) {
        description = obj.optString("a");
        apkUrl = obj.optString("b");
        name = obj.optString("c");
        uuid = obj.optString("d");
        pkgName = obj.optString("e");
        hint = obj.optString("f");
        appType = obj.optString("g");
        size = obj.optInt("h");
        pkgVersionName = obj.optString("i");
        pkgVersionCode = obj.optInt("j");
        iconUrl = obj.optString("k");
        screenUrl = obj.optString("l");
        provider = obj.optString("m");
        pushTitle = obj.optString("n");
        pushShortDescription = obj.optString("o");
        channel = obj.optInt("p");
        id = obj.optLong("q");
        pushType = obj.optInt("r");
        pushOnWifi = obj.optBoolean("s");
        saveTime = obj.optLong("t");
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = super.toJson();
        obj.put("a", description);
        obj.put("b", apkUrl);
        obj.put("c", name);
        obj.put("d", uuid);
        obj.put("e", pkgName);
        obj.put("f", hint);
        obj.put("g", appType);
        if (size != 0) obj.put("h", size);
        obj.put("i", pkgVersionName);
        if (pkgVersionCode != 0) obj.put("j", pkgVersionCode);
        obj.put("k", iconUrl);
        obj.put("l", screenUrl);
        obj.put("m", provider);
        obj.put("n", pushTitle);
        obj.put("o", pushShortDescription);
        if (channel != 0) obj.put("p", channel);
        if (id != 0) obj.put("q", id);
        if (pushType != 0) obj.put("r", pushType);
        if (pushOnWifi) obj.put("s", pushOnWifi);
        if (saveTime != 0) obj.put("t", saveTime);
        return obj;
    }
    public boolean fromStream(String s1) {
        String as[] = s1.split(SPLITTER);
        if (as != null) {
            int index = 0;
            try {
                description = as[index++];
                apkUrl = as[index++];
                name = as[index++];
                uuid = as[index++];
                pkgName = as[index++];
                hint = as[index++];
                appType = as[index++];
                size = Integer.valueOf(as[index++]).intValue();
                pkgVersionName = as[index++];
                pkgVersionCode = Integer.valueOf(as[index++]).intValue();
                iconUrl = as[index++];
                screenUrl = as[index++];
                provider = as[index++];
                pushTitle = as[index++];
                pushShortDescription = as[index++];
                channel = Integer.valueOf(as[index++]);
                return true;
            } catch (Throwable e) {}
        }
        return false;
    }
    public String toStream() {
        StringBuilder sb = new StringBuilder();
        sb.append(description).append(SPLITTER);
        sb.append(apkUrl).append(SPLITTER);
        sb.append(name).append(SPLITTER);
        sb.append(uuid).append(SPLITTER);
        sb.append(pkgName).append(SPLITTER);
        sb.append(hint).append(SPLITTER);
        sb.append(appType).append(SPLITTER);
        sb.append(size).append(SPLITTER);
        sb.append(pkgVersionName).append(SPLITTER);
        sb.append(pkgVersionCode).append(SPLITTER);
        sb.append(iconUrl).append(SPLITTER);
        sb.append(screenUrl).append(SPLITTER);
        sb.append(provider).append(SPLITTER);
        sb.append(pushTitle).append(SPLITTER);
        sb.append(pushShortDescription).append(SPLITTER);
        sb.append(channel).append(SPLITTER);
        return sb.toString();
    }
}
