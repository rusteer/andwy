package plugin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonTest {
    public static void main(String args[]){
        String s="[]";
        try {
            JSONArray array=new JSONArray(s);
            System.out.println(array.length());
            System.out.println(array);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
