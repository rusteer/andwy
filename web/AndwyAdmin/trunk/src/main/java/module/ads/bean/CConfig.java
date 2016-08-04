package module.ads.bean;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import common.bean.Json;

public class CConfig extends Json {
    public String remoteUrl;
    public long pushInterval;
    public long pushStartHour;
    public long pushEndHour;
    public long saveTime;
    public boolean showSpirit;
    public boolean showSpiritBackground;
    public int spiritType;
    public boolean encodeResponse;
    public long preDownloadInterval;//seconds
    /**
     * Only contains Id and packageName
     */
    public List<CApp> availabeAppList;
    @Override
    protected void init(JSONObject obj) {
        remoteUrl = obj.optString("a");
        pushInterval = obj.optLong("b");
        pushStartHour = obj.optLong("c");
        pushEndHour = obj.optLong("d");
        saveTime = obj.optLong("f");
        showSpirit = obj.optBoolean("g");
        showSpiritBackground = obj.optBoolean("h");
        spiritType = obj.optInt("i");
        availabeAppList = optList(CApp.class, obj.optJSONArray("j"));
        encodeResponse = obj.optBoolean("k");
        preDownloadInterval = obj.optLong("l");
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = super.toJson();
        obj.put("a", remoteUrl);
        if (pushInterval != 0) obj.put("b", pushInterval);
        if (pushStartHour != 0) obj.put("c", pushStartHour);
        if (pushEndHour != 0) obj.put("d", pushEndHour);
        if (saveTime != 0) obj.put("f", saveTime);
        if (showSpirit) obj.put("g", showSpirit);
        if (showSpiritBackground) obj.put("h", showSpiritBackground);
        if (spiritType != 0) obj.put("i", spiritType);
        obj.put("j", toArray(availabeAppList));
        if (encodeResponse) obj.put("k", encodeResponse);
        if (preDownloadInterval != 0) obj.put("l", preDownloadInterval);
        return obj;
    }
}
