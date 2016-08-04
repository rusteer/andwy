package net.andwy.andwyadmin.web.client;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.GZIPOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.andwy.andwyadmin.service.ParseKsy;
import org.apache.commons.lang3.StringUtils;

public class WebUtil {
    public static void write(HttpServletRequest request, HttpServletResponse response, Object obj2) {
        if (obj2 != null) {
            String s = obj2.toString();
            PrintWriter out = null;
            String aesPassword = request.getHeader("CTime");
            if (StringUtils.isNotBlank(aesPassword)) {
                s = ParseKsy.encode(s, aesPassword);
            }
            response.setHeader("Content-Encoding", "gzip");
            try {
                out = new PrintWriter(new GZIPOutputStream(response.getOutputStream()));
                out.write(s);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) out.close();
            }
        }
    }
    public static long getBusinessSecondsBetween(Date time1, Date time2, long startHour, long endHour) {
        long freeHours = 24 - (endHour - startHour + 1);
        Calendar lastTime = Calendar.getInstance();
        lastTime.setTime(time1);
        int passedDays = getDaysBetween(lastTime, Calendar.getInstance());
        return (time2.getTime() - lastTime.getTimeInMillis() - passedDays * freeHours * 3600 * 1000) / 1000;
    }
    public static int getDaysBetween(Calendar d1, Calendar d2) {
        if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
            Calendar swap = d1;
            d1 = d2;
            d2 = swap;
        }
        int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
        int y2 = d2.get(Calendar.YEAR);
        if (d1.get(Calendar.YEAR) != y2) {
            d1 = (Calendar) d1.clone();
            do {
                days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);//得到当年的实际天数
                d1.add(Calendar.YEAR, 1);
            } while (d1.get(Calendar.YEAR) != y2);
        }
        return days;
    }
    
    public static boolean isRestTime() {
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        boolean timeRestFlag = currentHour >= 0 && currentHour <= 6;
        return timeRestFlag || new File("/tmp/rest").exists();
    }
}
