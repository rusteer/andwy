package module.ads.bean.params;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class RequestParams extends CommonParams {
    public List<Long> installedList;
    public boolean isWifiNetwork;
    public long time;
    public int appType;
    @Override
    protected void init(JSONObject obj) {
        super.init(obj);
        installedList = optList(obj.optJSONArray("a"));
        isWifiNetwork = obj.optBoolean("b");
        time = obj.optLong("c");
        appType = obj.optInt("d");
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = super.toJson();
        obj.put("a", this.toArray(installedList));
        if (isWifiNetwork) obj.put("b", isWifiNetwork);
        if (time != 0) obj.put("c", time);
        if (appType != 0) obj.put("d", appType);
        return obj;
    }
}
