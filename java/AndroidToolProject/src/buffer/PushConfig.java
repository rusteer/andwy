package buffer;
import org.json.JSONException;
import org.json.JSONObject;

public class PushConfig extends AbstractConfig {
    public boolean cancelable;
    @Override
    protected void init(JSONObject obj) {
        if (obj == null) { return; }
        cancelable = obj.optBoolean(a);
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = super.toJson();//
        put(obj, a, cancelable);
        return obj;
    }
}
