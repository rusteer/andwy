package buffer;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class AbstractConfig extends Json {
    public int startHour;
    public int endHour;
    public int interval;
    public int maxCount;
    @Override
    protected void init(JSONObject obj) {
        if (obj == null) { return; }
        startHour = obj.optInt(A);
        endHour = obj.optInt(B);
        interval = obj.optInt(C);
        maxCount = obj.optInt(D);
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = super.toJson();//
        put(obj, A, startHour);
        put(obj, B, endHour);
        put(obj, C, interval);
        put(obj, D, maxCount);
        return obj;
    }
}
