package buffer;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class AdsBean extends Json {
    //
    public long id;
    public String name;
    public String packageName;
    public boolean installable;
    public String url;
    public String screenUrl;
    public String iconUrl;
    public String description;
    //
    public BannerConfig bannerConfig;
    public PopConfig popConfig;
    public PushConfig pushConfig;
    public ShortcutConfig shortcutConfig;
    @Override
    protected void init(JSONObject obj) {
        if (obj == null) { return; }
        id = obj.optLong(a);
        name = obj.optString(b);
        packageName = obj.optString(c);
        installable = obj.optBoolean(d);
        url = obj.optString(e);
        iconUrl = obj.optString(f);
        screenUrl = obj.optString(g);
        description = obj.optString(h);
        bannerConfig = Json.optObj(BannerConfig.class, obj.optJSONObject(i));
        popConfig = Json.optObj(PopConfig.class, obj.optJSONObject(j));
        pushConfig = Json.optObj(PushConfig.class, obj.optJSONObject(k));
        shortcutConfig = Json.optObj(ShortcutConfig.class, obj.optJSONObject(l));
    }
    public JSONObject toJson() throws JSONException {
        JSONObject obj = super.toJson();
        put(obj, a, id);
        put(obj, b, name);
        put(obj, c, packageName);
        put(obj, d, installable);
        put(obj, e, url);
        put(obj, f, iconUrl);
        put(obj, g, screenUrl);
        put(obj, h, description);
        put(obj, i, toJson(bannerConfig));
        put(obj, j, toJson(popConfig));
        put(obj, k, toJson(pushConfig));
        put(obj, l, toJson(shortcutConfig));
        return obj;
    }
}
