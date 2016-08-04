package module.ads.bean;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import common.bean.Json;

public class CAppList extends Json {
    public List<CApp> list;
    public int serverCount;
    @Override
    protected void init(JSONObject obj) {
        list = optList(CApp.class, obj.optJSONArray("a"));
        serverCount = obj.optInt("b");
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = super.toJson();
        obj.put("a", this.toArray(list));
        obj.put("b", serverCount);
        return obj;
    }
}
