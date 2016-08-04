package buffer;
import org.json.JSONException;
import org.json.JSONObject;

public class BannerConfig extends AbstractConfig {
    public String blackPackages;//适配游戏
    public String whitePackages;//适配游戏
    public boolean autoExit;//是否跟游戏一同退出
    @Override
    protected void init(JSONObject obj) {
        if (obj == null) { return; }
        blackPackages = obj.optString(a);
        whitePackages = obj.optString(b);
        autoExit = obj.optBoolean(c);
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = super.toJson();//
        put(obj, a, blackPackages);
        put(obj, b, whitePackages);
        put(obj, c, autoExit);
        return obj;
    }
}
