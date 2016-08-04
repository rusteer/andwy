package common.bean;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class Json {
    public static <T extends Json> T optObj(Class<T> c, JSONObject obj) {
        if (obj != null) {
            try {
                T t = c.newInstance();
                t.init(obj);
                return t;
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    protected abstract void init(JSONObject obj);
    public static <T extends Json> List<T> optList(Class<T> c, JSONArray array) {
        if (array != null) {
            List<T> list = new ArrayList<T>();
            for (int i = 0; i < array.length(); i++) {
                T t = optObj(c, array.optJSONObject(i));
                if (t != null) list.add(t);
            }
            return list;
        }
        return null;
    }
    public static List<Long> optList(JSONArray array) {
        List<Long> list = new ArrayList<Long>();
        if (array != null) {
            for (int i = 0; i < array.length(); i++) {
                list.add(array.optLong(i));
            }
        }
        return list;
    }
    protected <T> JSONArray toArray(List<T> list) throws JSONException {
        JSONArray result = null;
        if (list != null) {
            result = new JSONArray();
            for (T t : list) {
                if (t != null) {
                    if (t instanceof Json) {
                        result.put(((Json) t).toJson());
                    } else {
                        result.put(t);
                    }
                }
            }
        }
        return result;
    }
    public JSONObject toJson() throws JSONException {
        return new JSONObject();
    }
    protected <T extends Json> JSONObject toJson(T t) throws JSONException {
        JSONObject result = null;
        if (t != null) {
            result = t.toJson();
        }
        return result;
    }
}
