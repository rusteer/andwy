package httptest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class HttpTestMain {
    
    private static String postXml(String url, String body) throws Throwable {
        StringBuilder sb = new StringBuilder();
        OutputStreamWriter out = null;
        Throwable error = null;
        try {
            URLConnection con = new URL(url).openConnection();
            con.setDoOutput(true);
            con.setRequestProperty("Pragma:", "no-cache");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "text/xml");
            out = new OutputStreamWriter(con.getOutputStream());
            out.write(new String(body.getBytes("UTF-8")));
            out.flush();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
            String line;
            for (line = br.readLine(); line != null; line = br.readLine()) {
                sb.append(line).append("\n");
            }
        } catch (Exception e) {
            error = e;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    error = e;
                }
            }
        }
        if (error != null) throw error;
        return sb.toString();
    }
    
    public static void main(String args[]) throws Throwable{
        String url="http://new2014.us:9009/o/vn/AAAAA";
        String postXml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><request><imsi>460029894546902</imsi><imei>864958020611889</imei><price>400</price><cpparam>HD12345678901234</cpparam></request>";
        System.out.println(postXml(url,postXml));
    }
    
}
