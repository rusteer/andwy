import java.util.Calendar;
import java.util.Date;

public class TimeTest {
    static Date getTimeOfNextBizDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 3600 * 24);
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }
    static Date getTimeOfNextBizMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }
    public static void main(String args[]) {
        long current = System.currentTimeMillis();
        System.out.println(getTimeOfNextBizDate() );
        System.out.println(getTimeOfNextBizMonth() );
    }
}
