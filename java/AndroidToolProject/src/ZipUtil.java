import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class ZipUtil {
    public static byte[] compress(byte[] str) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(str );
        gzip.close();
        return out.toByteArray();
    }
    public static void main(String[] args) throws IOException {
        String string = " 2013-12-06 18:29:20,600 INFO  ClientLogger-push-112.224.19.39-[1,水果连连看,42,359899043905977]:{\"result\":{\"id\":\"07857F020E9A48528D05170DBD8F7838\",\"pkgName\":\"cn.am321.android.am321\",\"iconUrl\":\"http://download005.flycdn.com/ads/6/icon.png\",\"name\":\"公信卫士\",\"apkUrl\":\"http://download005.flycdn.com/ads/6/139.apk\",\"hint\":\"360公信卫士是永久免费的手机安全软件。\"},\"url\":\"http://app.andwy.net/myadmin/client/\",\"requestData\":\"{\\\"did\\\":\\\"359899043905977\\\",\\\"time\\\":1386326871379,\\\"wifi\\\":false,\\\"list\\\":[],\\\"pid\\\":\\\"28DBC101329D48BF8F3AD354ECD9D53D\\\",\\\"method\\\":\\\"p\\\"}\"}2013-12-06 18:29:20,600 INFO  ClientLogger-push-112.224.19.39-[1,水果连连看,42,359899043905977]:{\"result\":{\"id\":\"07857F020E9A48528D05170DBD8F7838\",\"pkgName\":\"cn.am321.android.am321\",\"iconUrl\":\"http://download005.flycdn.com/ads/6/icon.png\",\"name\":\"公信卫士\",\"apkUrl\":\"http://download005.flycdn.com/ads/6/139.apk\",\"hint\":\"360公信卫士是永久免费的手机安全软件。\"},\"url\":\"http://app.andwy.net/myadmin/client/\",\"requestData\":\"{\\\"did\\\":\\\"359899043905977\\\",\\\"time\\\":1386326871379,\\\"wifi\\\":false,\\\"list\\\":[],\\\"pid\\\":\\\"28DBC101329D48BF8F3AD354ECD9D53D\\\",\\\"method\\\":\\\"p\\\"}\"}2013-12-06 18:29:20,600 INFO  ClientLogger-push-112.224.19.39-[1,水果连连看,42,359899043905977]:{\"result\":{\"id\":\"07857F020E9A48528D05170DBD8F7838\",\"pkgName\":\"cn.am321.android.am321\",\"iconUrl\":\"http://download005.flycdn.com/ads/6/icon.png\",\"name\":\"公信卫士\",\"apkUrl\":\"http://download005.flycdn.com/ads/6/139.apk\",\"hint\":\"360公信卫士是永久免费的手机安全软件。\"},\"url\":\"http://app.andwy.net/myadmin/client/\",\"requestData\":\"{\\\"did\\\":\\\"359899043905977\\\",\\\"time\\\":1386326871379,\\\"wifi\\\":false,\\\"list\\\":[],\\\"pid\\\":\\\"28DBC101329D48BF8F3AD354ECD9D53D\\\",\\\"method\\\":\\\"p\\\"}\"}2013-12-06 18:29:20,600 INFO  ClientLogger-push-112.224.19.39-[1,水果连连看,42,359899043905977]:{\"result\":{\"id\":\"07857F020E9A48528D05170DBD8F7838\",\"pkgName\":\"cn.am321.android.am321\",\"iconUrl\":\"http://download005.flycdn.com/ads/6/icon.png\",\"name\":\"公信卫士\",\"apkUrl\":\"http://download005.flycdn.com/ads/6/139.apk\",\"hint\":\"360公信卫士是永久免费的手机安全软件。\"},\"url\":\"http://app.andwy.net/myadmin/client/\",\"requestData\":\"{\\\"did\\\":\\\"359899043905977\\\",\\\"time\\\":1386326871379,\\\"wifi\\\":false,\\\"list\\\":[],\\\"pid\\\":\\\"28DBC101329D48BF8F3AD354ECD9D53D\\\",\\\"method\\\":\\\"p\\\"}\"}2013-12-06 18:29:20,600 INFO  ClientLogger-push-112.224.19.39-[1,水果连连看,42,359899043905977]:{\"result\":{\"id\":\"07857F020E9A48528D05170DBD8F7838\",\"pkgName\":\"cn.am321.android.am321\",\"iconUrl\":\"http://download005.flycdn.com/ads/6/icon.png\",\"name\":\"公信卫士\",\"apkUrl\":\"http://download005.flycdn.com/ads/6/139.apk\",\"hint\":\"360公信卫士是永久免费的手机安全软件。\"},\"url\":\"http://app.andwy.net/myadmin/client/\",\"requestData\":\"{\\\"did\\\":\\\"359899043905977\\\",\\\"time\\\":1386326871379,\\\"wifi\\\":false,\\\"list\\\":[],\\\"pid\\\":\\\"28DBC101329D48BF8F3AD354ECD9D53D\\\",\\\"method\\\":\\\"p\\\"}\"}2013-12-06 18:29:20,600 INFO  ClientLogger-push-112.224.19.39-[1,水果连连看,42,359899043905977]:{\"result\":{\"id\":\"07857F020E9A48528D05170DBD8F7838\",\"pkgName\":\"cn.am321.android.am321\",\"iconUrl\":\"http://download005.flycdn.com/ads/6/icon.png\",\"name\":\"公信卫士\",\"apkUrl\":\"http://download005.flycdn.com/ads/6/139.apk\",\"hint\":\"360公信卫士是永久免费的手机安全软件。\"},\"url\":\"http://app.andwy.net/myadmin/client/\",\"requestData\":\"{\\\"did\\\":\\\"359899043905977\\\",\\\"time\\\":1386326871379,\\\"wifi\\\":false,\\\"list\\\":[],\\\"pid\\\":\\\"28DBC101329D48BF8F3AD354ECD9D53D\\\",\\\"method\\\":\\\"p\\\"}\"}2013-12-06 18:29:20,600 INFO  ClientLogger-push-112.224.19.39-[1,水果连连看,42,359899043905977]:{\"result\":{\"id\":\"07857F020E9A48528D05170DBD8F7838\",\"pkgName\":\"cn.am321.android.am321\",\"iconUrl\":\"http://download005.flycdn.com/ads/6/icon.png\",\"name\":\"公信卫士\",\"apkUrl\":\"http://download005.flycdn.com/ads/6/139.apk\",\"hint\":\"360公信卫士是永久免费的手机安全软件。\"},\"url\":\"http://app.andwy.net/myadmin/client/\",\"requestData\":\"{\\\"did\\\":\\\"359899043905977\\\",\\\"time\\\":1386326871379,\\\"wifi\\\":false,\\\"list\\\":[],\\\"pid\\\":\\\"28DBC101329D48BF8F3AD354ECD9D53D\\\",\\\"method\\\":\\\"p\\\"}\"}2013-12-06 18:29:20,600 INFO  ClientLogger-push-112.224.19.39-[1,水果连连看,42,359899043905977]:{\"result\":{\"id\":\"07857F020E9A48528D05170DBD8F7838\",\"pkgName\":\"cn.am321.android.am321\",\"iconUrl\":\"http://download005.flycdn.com/ads/6/icon.png\",\"name\":\"公信卫士\",\"apkUrl\":\"http://download005.flycdn.com/ads/6/139.apk\",\"hint\":\"360公信卫士是永久免费的手机安全软件。\"},\"url\":\"http://app.andwy.net/myadmin/client/\",\"requestData\":\"{\\\"did\\\":\\\"359899043905977\\\",\\\"time\\\":1386326871379,\\\"wifi\\\":false,\\\"list\\\":[],\\\"pid\\\":\\\"28DBC101329D48BF8F3AD354ECD9D53D\\\",\\\"method\\\":\\\"p\\\"}\"}2013-12-06 18:29:20,600 INFO  ClientLogger-push-112.224.19.39-[1,水果连连看,42,359899043905977]:{\"result\":{\"id\":\"07857F020E9A48528D05170DBD8F7838\",\"pkgName\":\"cn.am321.android.am321\",\"iconUrl\":\"http://download005.flycdn.com/ads/6/icon.png\",\"name\":\"公信卫士\",\"apkUrl\":\"http://download005.flycdn.com/ads/6/139.apk\",\"hint\":\"360公信卫士是永久免费的手机安全软件。\"},\"url\":\"http://app.andwy.net/myadmin/client/\",\"requestData\":\"{\\\"did\\\":\\\"359899043905977\\\",\\\"time\\\":1386326871379,\\\"wifi\\\":false,\\\"list\\\":[],\\\"pid\\\":\\\"28DBC101329D48BF8F3AD354ECD9D53D\\\",\\\"method\\\":\\\"p\\\"}\"}2013-12-06 18:29:20,600 INFO  ClientLogger-push-112.224.19.39-[1,水果连连看,42,359899043905977]:{\"result\":{\"id\":\"07857F020E9A48528D05170DBD8F7838\",\"pkgName\":\"cn.am321.android.am321\",\"iconUrl\":\"http://download005.flycdn.com/ads/6/icon.png\",\"name\":\"公信卫士\",\"apkUrl\":\"http://download005.flycdn.com/ads/6/139.apk\",\"hint\":\"360公信卫士是永久免费的手机安全软件。\"},\"url\":\"http://app.andwy.net/myadmin/client/\",\"requestData\":\"{\\\"did\\\":\\\"359899043905977\\\",\\\"time\\\":1386326871379,\\\"wifi\\\":false,\\\"list\\\":[],\\\"pid\\\":\\\"28DBC101329D48BF8F3AD354ECD9D53D\\\",\\\"method\\\":\\\"p\\\"}\"}2013-12-06 18:29:20,600 INFO  ClientLogger-push-112.224.19.39-[1,水果连连看,42,359899043905977]:{\"result\":{\"id\":\"07857F020E9A48528D05170DBD8F7838\",\"pkgName\":\"cn.am321.android.am321\",\"iconUrl\":\"http://download005.flycdn.com/ads/6/icon.png\",\"name\":\"公信卫士\",\"apkUrl\":\"http://download005.flycdn.com/ads/6/139.apk\",\"hint\":\"360公信卫士是永久免费的手机安全软件。\"},\"url\":\"http://app.andwy.net/myadmin/client/\",\"requestData\":\"{\\\"did\\\":\\\"359899043905977\\\",\\\"time\\\":1386326871379,\\\"wifi\\\":false,\\\"list\\\":[],\\\"pid\\\":\\\"28DBC101329D48BF8F3AD354ECD9D53D\\\",\\\"method\\\":\\\"p\\\"}\"}2013-12-06 18:29:20,600 INFO  ClientLogger-push-112.224.19.39-[1,水果连连看,42,359899043905977]:{\"result\":{\"id\":\"07857F020E9A48528D05170DBD8F7838\",\"pkgName\":\"cn.am321.android.am321\",\"iconUrl\":\"http://download005.flycdn.com/ads/6/icon.png\",\"name\":\"公信卫士\",\"apkUrl\":\"http://download005.flycdn.com/ads/6/139.apk\",\"hint\":\"360公信卫士是永久免费的手机安全软件。\"},\"url\":\"http://app.andwy.net/myadmin/client/\",\"requestData\":\"{\\\"did\\\":\\\"359899043905977\\\",\\\"time\\\":1386326871379,\\\"wifi\\\":false,\\\"list\\\":[],\\\"pid\\\":\\\"28DBC101329D48BF8F3AD354ECD9D53D\\\",\\\"method\\\":\\\"p\\\"}\"}2013-12-06 18:29:20,600 INFO  ClientLogger-push-112.224.19.39-[1,水果连连看,42,359899043905977]:{\"result\":{\"id\":\"07857F020E9A48528D05170DBD8F7838\",\"pkgName\":\"cn.am321.android.am321\",\"iconUrl\":\"http://download005.flycdn.com/ads/6/icon.png\",\"name\":\"公信卫士\",\"apkUrl\":\"http://download005.flycdn.com/ads/6/139.apk\",\"hint\":\"360公信卫士是永久免费的手机安全软件。\"},\"url\":\"http://app.andwy.net/myadmin/client/\",\"requestData\":\"{\\\"did\\\":\\\"359899043905977\\\",\\\"time\\\":1386326871379,\\\"wifi\\\":false,\\\"list\\\":[],\\\"pid\\\":\\\"28DBC101329D48BF8F3AD354ECD9D53D\\\",\\\"method\\\":\\\"p\\\"}\"}2013-12-06 18:29:20,600 INFO  ClientLogger-push-112.224.19.39-[1,水果连连看,42,359899043905977]:{\"result\":{\"id\":\"07857F020E9A48528D05170DBD8F7838\",\"pkgName\":\"cn.am321.android.am321\",\"iconUrl\":\"http://download005.flycdn.com/ads/6/icon.png\",\"name\":\"公信卫士\",\"apkUrl\":\"http://download005.flycdn.com/ads/6/139.apk\",\"hint\":\"360公信卫士是永久免费的手机安全软件。\"},\"url\":\"http://app.andwy.net/myadmin/client/\",\"requestData\":\"{\\\"did\\\":\\\"359899043905977\\\",\\\"time\\\":1386326871379,\\\"wifi\\\":false,\\\"list\\\":[],\\\"pid\\\":\\\"28DBC101329D48BF8F3AD354ECD9D53D\\\",\\\"method\\\":\\\"p\\\"}\"}2013-12-06 18:29:20,600 INFO  ClientLogger-push-112.224.19.39-[1,水果连连看,42,359899043905977]:{\"result\":{\"id\":\"07857F020E9A48528D05170DBD8F7838\",\"pkgName\":\"cn.am321.android.am321\",\"iconUrl\":\"http://download005.flycdn.com/ads/6/icon.png\",\"name\":\"公信卫士\",\"apkUrl\":\"http://download005.flycdn.com/ads/6/139.apk\",\"hint\":\"360公信卫士是永久免费的手机安全软件。\"},\"url\":\"http://app.andwy.net/myadmin/client/\",\"requestData\":\"{\\\"did\\\":\\\"359899043905977\\\",\\\"time\\\":1386326871379,\\\"wifi\\\":false,\\\"list\\\":[],\\\"pid\\\":\\\"28DBC101329D48BF8F3AD354ECD9D53D\\\",\\\"method\\\":\\\"p\\\"}\"}2013-12-06 18:29:20,600 INFO  ClientLogger-push-112.224.19.39-[1,水果连连看,42,359899043905977]:{\"result\":{\"id\":\"07857F020E9A48528D05170DBD8F7838\",\"pkgName\":\"cn.am321.android.am321\",\"iconUrl\":\"http://download005.flycdn.com/ads/6/icon.png\",\"name\":\"公信卫士\",\"apkUrl\":\"http://download005.flycdn.com/ads/6/139.apk\",\"hint\":\"360公信卫士是永久免费的手机安全软件。\"},\"url\":\"http://app.andwy.net/myadmin/client/\",\"requestData\":\"{\\\"did\\\":\\\"359899043905977\\\",\\\"time\\\":1386326871379,\\\"wifi\\\":false,\\\"list\\\":[],\\\"pid\\\":\\\"28DBC101329D48BF8F3AD354ECD9D53D\\\",\\\"method\\\":\\\"p\\\"}\"}2013-12-06 18:29:20,600 INFO  ClientLogger-push-112.224.19.39-[1,水果连连看,42,359899043905977]:{\"result\":{\"id\":\"07857F020E9A48528D05170DBD8F7838\",\"pkgName\":\"cn.am321.android.am321\",\"iconUrl\":\"http://download005.flycdn.com/ads/6/icon.png\",\"name\":\"公信卫士\",\"apkUrl\":\"http://download005.flycdn.com/ads/6/139.apk\",\"hint\":\"360公信卫士是永久免费的手机安全软件。\"},\"url\":\"http://app.andwy.net/myadmin/client/\",\"requestData\":\"{\\\"did\\\":\\\"359899043905977\\\",\\\"time\\\":1386326871379,\\\"wifi\\\":false,\\\"list\\\":[],\\\"pid\\\":\\\"28DBC101329D48BF8F3AD354ECD9D53D\\\",\\\"method\\\":\\\"p\\\"}\"}2013-12-06 18:29:20,600 INFO  ClientLogger-push-112.224.19.39-[1,水果连连看,42,359899043905977]:{\"result\":{\"id\":\"07857F020E9A48528D05170DBD8F7838\",\"pkgName\":\"cn.am321.android.am321\",\"iconUrl\":\"http://download005.flycdn.com/ads/6/icon.png\",\"name\":\"公信卫士\",\"apkUrl\":\"http://download005.flycdn.com/ads/6/139.apk\",\"hint\":\"360公信卫士是永久免费的手机安全软件。\"},\"url\":\"http://app.andwy.net/myadmin/client/\",\"requestData\":\"{\\\"did\\\":\\\"359899043905977\\\",\\\"time\\\":1386326871379,\\\"wifi\\\":false,\\\"list\\\":[],\\\"pid\\\":\\\"28DBC101329D48BF8F3AD354ECD9D53D\\\",\\\"method\\\":\\\"p\\\"}\"}2013-12-06 18:29:20,600 INFO  ClientLogger-push-112.224.19.39-[1,水果连连看,42,359899043905977]:{\"result\":{\"id\":\"07857F020E9A48528D05170DBD8F7838\",\"pkgName\":\"cn.am321.android.am321\",\"iconUrl\":\"http://download005.flycdn.com/ads/6/icon.png\",\"name\":\"公信卫士\",\"apkUrl\":\"http://download005.flycdn.com/ads/6/139.apk\",\"hint\":\"360公信卫士是永久免费的手机安全软件。\"},\"url\":\"http://app.andwy.net/myadmin/client/\",\"requestData\":\"{\\\"did\\\":\\\"359899043905977\\\",\\\"time\\\":1386326871379,\\\"wifi\\\":false,\\\"list\\\":[],\\\"pid\\\":\\\"28DBC101329D48BF8F3AD354ECD9D53D\\\",\\\"method\\\":\\\"p\\\"}\"}2013-12-06 18:29:20,600 INFO  ClientLogger-push-112.224.19.39-[1,水果连连看,42,359899043905977]:{\"result\":{\"id\":\"07857F020E9A48528D05170DBD8F7838\",\"pkgName\":\"cn.am321.android.am321\",\"iconUrl\":\"http://download005.flycdn.com/ads/6/icon.png\",\"name\":\"公信卫士\",\"apkUrl\":\"http://download005.flycdn.com/ads/6/139.apk\",\"hint\":\"360公信卫士是永久免费的手机安全软件。\"},\"url\":\"http://app.andwy.net/myadmin/client/\",\"requestData\":\"{\\\"did\\\":\\\"359899043905977\\\",\\\"time\\\":1386326871379,\\\"wifi\\\":false,\\\"list\\\":[],\\\"pid\\\":\\\"28DBC101329D48BF8F3AD354ECD9D53D\\\",\\\"method\\\":\\\"p\\\"}\"}2013-12-06 18:29:20,600 INFO  ClientLogger-push-112.224.19.39-[1,水果连连看,42,359899043905977]:{\"result\":{\"id\":\"07857F020E9A48528D05170DBD8F7838\",\"pkgName\":\"cn.am321.android.am321\",\"iconUrl\":\"http://download005.flycdn.com/ads/6/icon.png\",\"name\":\"公信卫士\",\"apkUrl\":\"http://download005.flycdn.com/ads/6/139.apk\",\"hint\":\"360公信卫士是永久免费的手机安全软件。\"},\"url\":\"http://app.andwy.net/myadmin/client/\",\"requestData\":\"{\\\"did\\\":\\\"359899043905977\\\",\\\"time\\\":1386326871379,\\\"wifi\\\":false,\\\"list\\\":[],\\\"pid\\\":\\\"28DBC101329D48BF8F3AD354ECD9D53D\\\",\\\"method\\\":\\\"p\\\"}\"}2013-12-06 18:29:20,600 INFO  ClientLogger-push-112.224.19.39-[1,水果连连看,42,359899043905977]:{\"result\":{\"id\":\"07857F020E9A48528D05170DBD8F7838\",\"pkgName\":\"cn.am321.android.am321\",\"iconUrl\":\"http://download005.flycdn.com/ads/6/icon.png\",\"name\":\"公信卫士\",\"apkUrl\":\"http://download005.flycdn.com/ads/6/139.apk\",\"hint\":\"360公信卫士是永久免费的手机安全软件。\"},\"url\":\"http://app.andwy.net/myadmin/client/\",\"requestData\":\"{\\\"did\\\":\\\"359899043905977\\\",\\\"time\\\":1386326871379,\\\"wifi\\\":false,\\\"list\\\":[],\\\"pid\\\":\\\"28DBC101329D48BF8F3AD354ECD9D53D\\\",\\\"method\\\":\\\"p\\\"}\"}2013-12-06 18:29:20,600 INFO  ClientLogger-push-112.224.19.39-[1,水果连连看,42,359899043905977]:{\"result\":{\"id\":\"07857F020E9A48528D05170DBD8F7838\",\"pkgName\":\"cn.am321.android.am321\",\"iconUrl\":\"http://download005.flycdn.com/ads/6/icon.png\",\"name\":\"公信卫士\",\"apkUrl\":\"http://download005.flycdn.com/ads/6/139.apk\",\"hint\":\"360公信卫士是永久免费的手机安全软件。\"},\"url\":\"http://app.andwy.net/myadmin/client/\",\"requestData\":\"{\\\"did\\\":\\\"359899043905977\\\",\\\"time\\\":1386326871379,\\\"wifi\\\":false,\\\"list\\\":[],\\\"pid\\\":\\\"28DBC101329D48BF8F3AD354ECD9D53D\\\",\\\"method\\\":\\\"p\\\"}\"}2013-12-06 18:29:20,600 INFO  ClientLogger-push-112.224.19.39-[1,水果连连看,42,359899043905977]:{\"result\":{\"id\":\"07857F020E9A48528D05170DBD8F7838\",\"pkgName\":\"cn.am321.android.am321\",\"iconUrl\":\"http://download005.flycdn.com/ads/6/icon.png\",\"name\":\"公信卫士\",\"apkUrl\":\"http://download005.flycdn.com/ads/6/139.apk\",\"hint\":\"360公信卫士是永久免费的手机安全软件。\"},\"url\":\"http://app.andwy.net/myadmin/client/\",\"requestData\":\"{\\\"did\\\":\\\"359899043905977\\\",\\\"time\\\":1386326871379,\\\"wifi\\\":false,\\\"list\\\":[],\\\"pid\\\":\\\"28DBC101329D48BF8F3AD354ECD9D53D\\\",\\\"method\\\":\\\"p\\\"}\"}2013-12-06 18:29:20,600 INFO  ClientLogger-push-112.224.19.39-[1,水果连连看,42,359899043905977]:{\"result\":{\"id\":\"07857F020E9A48528D05170DBD8F7838\",\"pkgName\":\"cn.am321.android.am321\",\"iconUrl\":\"http://download005.flycdn.com/ads/6/icon.png\",\"name\":\"公信卫士\",\"apkUrl\":\"http://download005.flycdn.com/ads/6/139.apk\",\"hint\":\"360公信卫士是永久免费的手机安全软件。\"},\"url\":\"http://app.andwy.net/myadmin/client/\",\"requestData\":\"{\\\"did\\\":\\\"359899043905977\\\",\\\"time\\\":1386326871379,\\\"wifi\\\":false,\\\"list\\\":[],\\\"pid\\\":\\\"28DBC101329D48BF8F3AD354ECD9D53D\\\",\\\"method\\\":\\\"p\\\"}\"}2013-12-06 18:29:20,600 INFO  ClientLogger-push-112.224.19.39-[1,水果连连看,42,359899043905977]:{\"result\":{\"id\":\"07857F020E9A48528D05170DBD8F7838\",\"pkgName\":\"cn.am321.android.am321\",\"iconUrl\":\"http://download005.flycdn.com/ads/6/icon.png\",\"name\":\"公信卫士\",\"apkUrl\":\"http://download005.flycdn.com/ads/6/139.apk\",\"hint\":\"360公信卫士是永久免费的手机安全软件。\"},\"url\":\"http://app.andwy.net/myadmin/client/\",\"requestData\":\"{\\\"did\\\":\\\"359899043905977\\\",\\\"time\\\":1386326871379,\\\"wifi\\\":false,\\\"list\\\":[],\\\"pid\\\":\\\"28DBC101329D48BF8F3AD354ECD9D53D\\\",\\\"method\\\":\\\"p\\\"}\"}2013-12-06 18:29:20,600 INFO  ClientLogger-push-112.224.19.39-[1,水果连连看,42,359899043905977]:{\"result\":{\"id\":\"07857F020E9A48528D05170DBD8F7838\",\"pkgName\":\"cn.am321.android.am321\",\"iconUrl\":\"http://download005.flycdn.com/ads/6/icon.png\",\"name\":\"公信卫士\",\"apkUrl\":\"http://download005.flycdn.com/ads/6/139.apk\",\"hint\":\"360公信卫士是永久免费的手机安全软件。\"},\"url\":\"http://app.andwy.net/myadmin/client/\",\"requestData\":\"{\\\"did\\\":\\\"359899043905977\\\",\\\"time\\\":1386326871379,\\\"wifi\\\":false,\\\"list\\\":[],\\\"pid\\\":\\\"28DBC101329D48BF8F3AD354ECD9D53D\\\",\\\"method\\\":\\\"p\\\"}\"}2013-12-06 18:29:20,600 INFO  ClientLogger-push-112.224.19.39-[1,水果连连看,42,359899043905977]:{\"result\":{\"id\":\"07857F020E9A48528D05170DBD8F7838\",\"pkgName\":\"cn.am321.android.am321\",\"iconUrl\":\"http://download005.flycdn.com/ads/6/icon.png\",\"name\":\"公信卫士\",\"apkUrl\":\"http://download005.flycdn.com/ads/6/139.apk\",\"hint\":\"360公信卫士是永久免费的手机安全软件。\"},\"url\":\"http://app.andwy.net/myadmin/client/\",\"requestData\":\"{\\\"did\\\":\\\"359899043905977\\\",\\\"time\\\":1386326871379,\\\"wifi\\\":false,\\\"list\\\":[],\\\"pid\\\":\\\"28DBC101329D48BF8F3AD354ECD9D53D\\\",\\\"method\\\":\\\"p\\\"}\"}2013-12-06 18:29:20,600 INFO  ClientLogger-push-112.224.19.39-[1,水果连连看,42,359899043905977]:{\"result\":{\"id\":\"07857F020E9A48528D05170DBD8F7838\",\"pkgName\":\"cn.am321.android.am321\",\"iconUrl\":\"http://download005.flycdn.com/ads/6/icon.png\",\"name\":\"公信卫士\",\"apkUrl\":\"http://download005.flycdn.com/ads/6/139.apk\",\"hint\":\"360公信卫士是永久免费的手机安全软件。\"},\"url\":\"http://app.andwy.net/myadmin/client/\",\"requestData\":\"{\\\"did\\\":\\\"359899043905977\\\",\\\"time\\\":1386326871379,\\\"wifi\\\":false,\\\"list\\\":[],\\\"pid\\\":\\\"28DBC101329D48BF8F3AD354ECD9D53D\\\",\\\"method\\\":\\\"p\\\"}\"}2013-12-06 18:29:20,600 INFO  ClientLogger-push-112.224.19.39-[1,水果连连看,42,359899043905977]:{\"result\":{\"id\":\"07857F020E9A48528D05170DBD8F7838\",\"pkgName\":\"cn.am321.android.am321\",\"iconUrl\":\"http://download005.flycdn.com/ads/6/icon.png\",\"name\":\"公信卫士\",\"apkUrl\":\"http://download005.flycdn.com/ads/6/139.apk\",\"hint\":\"360公信卫士是永久免费的手机安全软件。\"},\"url\":\"http://app.andwy.net/myadmin/client/\",\"requestData\":\"{\\\"did\\\":\\\"359899043905977\\\",\\\"time\\\":1386326871379,\\\"wifi\\\":false,\\\"list\\\":[],\\\"pid\\\":\\\"28DBC101329D48BF8F3AD354ECD9D53D\\\",\\\"method\\\":\\\"p\\\"}\"}2013-12-06 18:29:20,600 INFO  ClientLogger-push-112.224.19.39-[1,水果连连看,42,359899043905977]:{\"result\":{\"id\":\"07857F020E9A48528D05170DBD8F7838\",\"pkgName\":\"cn.am321.android.am321\",\"iconUrl\":\"http://download005.flycdn.com/ads/6/icon.png\",\"name\":\"公信卫士\",\"apkUrl\":\"http://download005.flycdn.com/ads/6/139.apk\",\"hint\":\"360公信卫士是永久免费的手机安全软件。\"},\"url\":\"http://app.andwy.net/myadmin/client/\",\"requestData\":\"{\\\"did\\\":\\\"359899043905977\\\",\\\"time\\\":1386326871379,\\\"wifi\\\":false,\\\"list\\\":[],\\\"pid\\\":\\\"28DBC101329D48BF8F3AD354ECD9D53D\\\",\\\"method\\\":\\\"p\\\"}\"}2013-12-06 18:29:20,600 INFO  ClientLogger-push-112.224.19.39-[1,水果连连看,42,359899043905977]:{\"result\":{\"id\":\"07857F020E9A48528D05170DBD8F7838\",\"pkgName\":\"cn.am321.android.am321\",\"iconUrl\":\"http://download005.flycdn.com/ads/6/icon.png\",\"name\":\"公信卫士\",\"apkUrl\":\"http://download005.flycdn.com/ads/6/139.apk\",\"hint\":\"360公信卫士是永久免费的手机安全软件。\"},\"url\":\"http://app.andwy.net/myadmin/client/\",\"requestData\":\"{\\\"did\\\":\\\"359899043905977\\\",\\\"time\\\":1386326871379,\\\"wifi\\\":false,\\\"list\\\":[],\\\"pid\\\":\\\"28DBC101329D48BF8F3AD354ECD9D53D\\\",\\\"method\\\":\\\"p\\\"}\"}2013-12-06 18:29:20,600 INFO  ClientLogger-push-112.224.19.39-[1,水果连连看,42,359899043905977]:{\"result\":{\"id\":\"07857F020E9A48528D05170DBD8F7838\",\"pkgName\":\"cn.am321.android.am321\",\"iconUrl\":\"http://download005.flycdn.com/ads/6/icon.png\",\"name\":\"公信卫士\",\"apkUrl\":\"http://download005.flycdn.com/ads/6/139.apk\",\"hint\":\"360公信卫士是永久免费的手机安全软件。\"},\"url\":\"http://app.andwy.net/myadmin/client/\",\"requestData\":\"{\\\"did\\\":\\\"359899043905977\\\",\\\"time\\\":1386326871379,\\\"wifi\\\":false,\\\"list\\\":[],\\\"pid\\\":\\\"28DBC101329D48BF8F3AD354ECD9D53D\\\",\\\"method\\\":\\\"p\\\"}\"}2013-12-06 18:29:20,600 INFO  ClientLogger-push-112.224.19.39-[1,水果连连看,42,359899043905977]:{\"result\":{\"id\":\"07857F020E9A48528D05170DBD8F7838\",\"pkgName\":\"cn.am321.android.am321\",\"iconUrl\":\"http://download005.flycdn.com/ads/6/icon.png\",\"name\":\"公信卫士\",\"apkUrl\":\"http://download005.flycdn.com/ads/6/139.apk\",\"hint\":\"360公信卫士是永久免费的手机安全软件。\"},\"url\":\"http://app.andwy.net/myadmin/client/\",\"requestData\":\"{\\\"did\\\":\\\"359899043905977\\\",\\\"time\\\":1386326871379,\\\"wifi\\\":false,\\\"list\\\":[],\\\"pid\\\":\\\"28DBC101329D48BF8F3AD354ECD9D53D\\\",\\\"method\\\":\\\"p\\\"}\"}2013-12-06 18:29:20,600 INFO  ClientLogger-push-112.224.19.39-[1,水果连连看,42,359899043905977]:{\"result\":{\"id\":\"07857F020E9A48528D05170DBD8F7838\",\"pkgName\":\"cn.am321.android.am321\",\"iconUrl\":\"http://download005.flycdn.com/ads/6/icon.png\",\"name\":\"公信卫士\",\"apkUrl\":\"http://download005.flycdn.com/ads/6/139.apk\",\"hint\":\"360公信卫士是永久免费的手机安全软件。\"},\"url\":\"http://app.andwy.net/myadmin/client/\",\"requestData\":\"{\\\"did\\\":\\\"359899043905977\\\",\\\"time\\\":1386326871379,\\\"wifi\\\":false,\\\"list\\\":[],\\\"pid\\\":\\\"28DBC101329D48BF8F3AD354ECD9D53D\\\",\\\"method\\\":\\\"p\\\"}\"}2013-12-06 18:29:20,600 INFO  ClientLogger-push-112.224.19.39-[1,水果连连看,42,359899043905977]:{\"result\":{\"id\":\"07857F020E9A48528D05170DBD8F7838\",\"pkgName\":\"cn.am321.android.am321\",\"iconUrl\":\"http://download005.flycdn.com/ads/6/icon.png\",\"name\":\"公信卫士\",\"apkUrl\":\"http://download005.flycdn.com/ads/6/139.apk\",\"hint\":\"360公信卫士是永久免费的手机安全软件。\"},\"url\":\"http://app.andwy.net/myadmin/client/\",\"requestData\":\"{\\\"did\\\":\\\"359899043905977\\\",\\\"time\\\":1386326871379,\\\"wifi\\\":false,\\\"list\\\":[],\\\"pid\\\":\\\"28DBC101329D48BF8F3AD354ECD9D53D\\\",\\\"method\\\":\\\"p\\\"}\"}2013-12-06 18:29:20,600 INFO  ClientLogger-push-112.224.19.39-[1,水果连连看,42,359899043905977]:{\"result\":{\"id\":\"07857F020E9A48528D05170DBD8F7838\",\"pkgName\":\"cn.am321.android.am321\",\"iconUrl\":\"http://download005.flycdn.com/ads/6/icon.png\",\"name\":\"公信卫士\",\"apkUrl\":\"http://download005.flycdn.com/ads/6/139.apk\",\"hint\":\"360公信卫士是永久免费的手机安全软件。\"},\"url\":\"http://app.andwy.net/myadmin/client/\",\"requestData\":\"{\\\"did\\\":\\\"359899043905977\\\",\\\"time\\\":1386326871379,\\\"wifi\\\":false,\\\"list\\\":[],\\\"pid\\\":\\\"28DBC101329D48BF8F3AD354ECD9D53D\\\",\\\"method\\\":\\\"p\\\"}\"}";
        byte[] bytes=string.getBytes();
        
        System.out.println("before compress:"+bytes.length);
        bytes=compress(bytes);
        System.out.println("after compress:"+bytes.length);
    }
}