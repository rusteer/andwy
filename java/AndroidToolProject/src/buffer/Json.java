package buffer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class Json   implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    protected static final String A = "A";
    protected static final String B = "B";
    protected static final String C = "C";
    protected static final String D = "D";
    protected static final String E = "E";
    protected static final String F = "F";
    protected static final String G = "G";
    protected static final String H = "H";
    protected static final String I = "I";
    protected static final String J = "J";
    protected static final String K = "K";
    protected static final String L = "L";
    protected static final String M = "M";
    protected static final String N = "N";
    protected static final String O = "O";
    protected static final String P = "P";
    protected static final String Q = "Q";
    protected static final String R = "R";
    protected static final String S = "S";
    protected static final String T = "T";
    //
    protected static final String a = "a";
    protected static final String b = "b";
    protected static final String c = "c";
    protected static final String d = "d";
    protected static final String e = "e";
    protected static final String f = "f";
    protected static final String g = "g";
    protected static final String h = "h";
    protected static final String i = "i";
    protected static final String j = "j";
    protected static final String k = "k";
    protected static final String l = "l";
    protected static final String m = "m";
    protected static final String n = "n";
    protected abstract void init(JSONObject obj);
    public static <T extends Json> List<T> optList(Class<T> c, JSONArray array) {
        if (array != null) {
            List<T> list = new ArrayList<T>();
            for (int i = 0; i < array.length(); i++) {
                T t = optObj(c, array.optJSONObject(i));
                if (t != null) {
                    list.add(t);
                }
            }
            return list;
        }
        return null;
    }
    public static <T extends Json> T optObj(Class<T> c, JSONObject obj) {
        if (obj != null) {
            try {
                T t = c.newInstance();
                t.init(obj);
                return t;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public static void put(JSONObject obj, String name, boolean value) throws JSONException {
        if (value) {
            obj.put(name, value);
        }
    }
    public static void put(JSONObject obj, String name, int value) throws JSONException {
        if (value != 0) {
            obj.put(name, value);
        }
    }
    public static void put(JSONObject obj, String name, long value) throws JSONException {
        if (value != 0) {
            obj.put(name, value);
        }
    }
    public static void put(JSONObject obj, String name, Object value) {
        if (value != null) {
            try {
                obj.put(name, value);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
    public static <T extends Json> JSONArray toJson(List<T> list) throws JSONException {
        JSONArray array = null;
        if (list != null) {
            array = new JSONArray();
            for (T t : list) {
                array.put(toJson(t));
            }
        }
        return array;
    }
    public static <T extends Json> JSONObject toJson(T t) throws JSONException {
        JSONObject result = null;
        if (t != null) {
            result = t.toJson();
        }
        return result;
    }
    
    public void put(JSONObject obj, String name, String value) {
        if (value != null && value.length() > 0) {
            try {
                obj.put(name, value);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
    public <T extends Json> JSONArray toArray(List<T> list) throws JSONException {
        JSONArray result = null;
        if (list != null) {
            result = new JSONArray();
            for (T t : list) {
                if (t != null) {
                    result.put(t.toJson());
                }
            }
        }
        return result;
    }
    public JSONObject toJson() throws JSONException {
        return new JSONObject();
    }
}
