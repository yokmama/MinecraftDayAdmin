package jp.minecraftday.spigot.minecraftdayadmin;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by masafumi on 2017/02/26.
 */
public class StringUtils {
    public static String timeFormat(long time) {
        long startTime = System.currentTimeMillis();
        long endTime = startTime + time;
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        Calendar result = Calendar.getInstance();

        start.setTimeInMillis(startTime);
        end.setTimeInMillis(endTime);

        long sa = end.getTimeInMillis() - start.getTimeInMillis() - result.getTimeZone().getRawOffset();

        result.setTimeInMillis(sa);

        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss:SSS");

        return sdf.format(result.getTime());
    }

}
