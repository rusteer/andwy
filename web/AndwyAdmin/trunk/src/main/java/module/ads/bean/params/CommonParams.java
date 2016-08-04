package module.ads.bean.params;
import org.json.JSONException;
import org.json.JSONObject;
import common.bean.Json;

public class CommonParams extends Json {
    public String method;
    public String deviceId;
    public String packageId;
    @Override
    protected void init(JSONObject obj) {
        method = obj.optString("aa");
        deviceId = obj.optString("bb");
        packageId = obj.optString("cc");
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = super.toJson();
        obj.put("aa", method);
        obj.put("bb", deviceId);
        obj.put("cc", packageId);
        return obj;
    }
}
