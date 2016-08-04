package module.ads.bean.params;
import module.ads.bean.CClient;
import org.json.JSONException;
import org.json.JSONObject;

public class ConfigParams extends CommonParams {
    public CClient client;
    @Override
    protected void init(JSONObject obj) {
        super.init(obj);
        client = optObj(CClient.class, obj.optJSONObject("a"));
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = super.toJson();
        obj.put("a", toJson(client));
        return obj;
    }
}
