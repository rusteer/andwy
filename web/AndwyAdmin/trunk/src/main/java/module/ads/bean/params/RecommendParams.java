package module.ads.bean.params;
import org.json.JSONException;
import org.json.JSONObject;

public class RecommendParams extends RequestParams {
    public int existCount;
    @Override
    protected void init(JSONObject obj) {
        super.init(obj);
        existCount = obj.optInt("z");
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = super.toJson();
        if (existCount != 0) obj.put("z", existCount);
        return obj;
    }
}
