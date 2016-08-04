package module.ads.bean.params;
import org.json.JSONException;
import org.json.JSONObject;

public class InstallParams extends CommonParams {
    public String appUUid;
    public int channel;
    @Override
    protected void init(JSONObject obj) {
        super.init(obj);
        appUUid = obj.optString("a");
        channel = obj.optInt("b");
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = super.toJson();
        obj.put("a", appUUid);
        obj.put("b", channel);
        return obj;
    }
}
