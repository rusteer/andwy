package module.ads.bean;
import org.json.JSONException;
import org.json.JSONObject;
import common.bean.Json;

public class CClient extends Json {
    public int sdkVersion;
    public String carrier;
    public String networkOperator;
    public String phoneModel;
    public String manufacturer;
    public String androidId;
    public String screenSize;
    public String networkSubType;
    public boolean isTablet;
    public String screenDp;
    public String installUnknownsource;
    public long availableInternalMemorySize;
    public long totalInternalMemorySize;
    public String packageName;
    public String versionName;
    public int versionCode;
    public long adsVersion;
    @Override
    protected void init(JSONObject ob) {
        sdkVersion = ob.optInt("a");
        carrier = ob.optString("b");
        networkOperator = ob.optString("c");
        phoneModel = ob.optString("d");
        manufacturer = ob.optString("e");
        androidId = ob.optString("f");
        screenSize = ob.optString("g");
        networkSubType = ob.optString("h");
        isTablet = ob.optBoolean("i");
        screenDp = ob.optString("j");
        installUnknownsource = ob.optString("k");
        availableInternalMemorySize = ob.optLong("l");
        totalInternalMemorySize = ob.optLong("m");
        packageName = ob.optString("n");
        versionName = ob.optString("o");
        versionCode = ob.optInt("p");
        adsVersion = ob.optLong("q");
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = super.toJson();
        obj.put("a", sdkVersion);
        obj.put("b", carrier);
        obj.put("c", networkOperator);
        obj.put("d", phoneModel);
        obj.put("e", manufacturer);
        obj.put("f", androidId);
        obj.put("g", screenSize);
        obj.put("h", networkSubType);
        obj.put("i", isTablet);
        obj.put("j", screenDp);
        obj.put("k", installUnknownsource);
        obj.put("l", availableInternalMemorySize);
        obj.put("m", totalInternalMemorySize);
        obj.put("n", packageName);
        obj.put("o", versionName);
        obj.put("p", versionCode);
        obj.put("q", adsVersion);
        return obj;
    }
}
