package httptest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONObject;

public class PostBody {
    
    public static void main(String args[]) throws Throwable{
        String body="{\"provinceId\" :\"21\",\"partnerType\":\"2\",\"paymentType\":\"1\",\"serialNumber\":\"23412313\",\"billFee\":\"100\",\"result\":\"0\",\"appName\":\"游戏名称\",\"indexId\":\"21323\",\"chargeTime\":\"2014-01-01 12:11:10\"}";
        //String url="http://localhost:6080/api/sync/myepay";
        String url="http://app.y9688.com/sync/myepay";
        System.out.println(body);
        JSONObject obj = new JSONObject(body);
        System.out.println(postBody(url,body));
    }
    
    private static String postBody(String url, String body) throws Throwable {
        StringBuilder sb = new StringBuilder ();
        OutputStreamWriter out = null;
        Throwable error = null;
        try {
            URLConnection con = new URL (url).openConnection ();
            con.setDoOutput (true);
            con.setRequestProperty ("Pragma:", "no-cache");
            con.setRequestProperty ("Cache-Control", "no-cache");
            con.setRequestProperty ("Content-Type", "text/xml");
            out = new OutputStreamWriter (con.getOutputStream ());
            out.write (new String (body.getBytes ("UTF-8")));
            out.flush ();
            BufferedReader br = new BufferedReader (new InputStreamReader (con.getInputStream ()));
            String line;
            for (line = br.readLine (); line != null; line = br.readLine ()) {
                sb.append (line).append ("\n");
            }
        } catch (Exception e) {
            error = e;
        } finally {
            if (out != null) {
                try {
                    out.close ();
                } catch (IOException e) {
                    error = e;
                }
            }
        }

        if (error != null) throw error;
        return sb.toString ();
    }
    
}
