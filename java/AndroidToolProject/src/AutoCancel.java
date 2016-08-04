import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutoCancel {
    public static void main(String[] args) {
        execute("F:\\block.txt");
       
        System.out.println(parse(sms));
    }
    private static void execute(String filePath) {
        BufferedReader bufferedreader = null;
        try {
            bufferedreader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            String line;
            while ((line = bufferedreader.readLine()) != null) {
                Sms sms = parse(line);
                if (sms == null) {
                    System.out.println(line);
                }
                //break;
            }
            bufferedreader.close();
            bufferedreader = null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedreader != null) try {
                bufferedreader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    static String sms="尊敬的客户，您好！您已成功订购中国移动的芒果TV手机视频包月业务，标准资费10.0元/月（实际收费根据您参加的套餐或优惠活动而定），从2014年10月10日开始生效。如有疑问可在1小时内回复\"8366\"，我们将在72小时内由10086联系您处理问题。如需帮助，请咨询10086。【中国移动提醒服务】";
    static Pattern[] patterns = {
            //
            Pattern.compile("发送\\s*([\\d]+)[^\\d]+([\\d]+)"), //
            Pattern.compile("回复([\\d]+)"),//
            Pattern.compile("发送([JC][\\d]+)[^\\d]+([\\d]+)"), //
            Pattern.compile("回复[\"“](否)[\"”]"), //订购&疑问&回复
            Pattern.compile("回复[\"“]([\\d]+)[\"”]"), //订购&疑问&回复  --尊敬的客户，您好！您已成功订购中国移动的芒果TV手机视频包月业务，标准资费10.0元/月（实际收费根据您参加的套餐或优惠活动而定），从2014年10月10日开始生效。如有疑问可在1小时内回复\"8366\"，我们将在72小时内由10086联系您处理问题。如需帮助，请咨询10086。【中国移动提醒服务】
            Pattern.compile("发送[\"“]([\\d]+)[\"”]到([\\d]+)"), //订购&疑问&发送-------订购提醒：尊敬的客户，您好！您已成功订购由中国移动通信集团北京有限公司提供的飞信，免费。如有疑问，请咨询10086。发送“1111”到10086获取更多精彩手机应用！中国移动
    };
    public static class Sms {
        String port;
        String content;
        public String toString() {
            return content + "->" + port;
        }
    }
    private static Sms parse(String line) {
        //可在1小时内发送337912（系统生成的随机码）到10086901，我们
        //可在1小时内发送329952到10086901，
        //可在1小时内回复2919，我们将在72小时内
        //可在1小时内回复3874，
        // System.out.println(line);
        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                String content = null;
                String port = null;
                if (matcher.groupCount() >= 1) {
                    content = matcher.group(1);
                }
                if (matcher.groupCount() >= 2) {
                    port = matcher.group(2);
                }
                if (content != null && content.length() > 0) {
                    Sms sms = new Sms();
                    sms.content = content;
                    sms.port = port;
                    //System.out.println(sms.content + "->" + sms.port);
                    return sms;
                }
            }
        }
        return null;
    }
}
