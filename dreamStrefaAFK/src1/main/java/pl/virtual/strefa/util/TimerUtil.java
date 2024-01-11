package pl.virtual.strefa.util;

import java.util.concurrent.TimeUnit;

public class TimerUtil {

    public static String convertLong(long seconds) {
        final long d = TimeUnit.SECONDS.toDays(seconds);
        final long h = TimeUnit.SECONDS.toHours(seconds) - d * 24L;
        final long m = TimeUnit.SECONDS.toMinutes(seconds) - d * 24L * 60L - h * 60L;
        final long s = seconds - d * 24L * 60L * 60L - h * 60L * 60L - m * 60L;
        if (d != 0L) {
            return d + "d " + h + "h " + m + "min " + s + "s";
        }
        if (h != 0L) {
            return h + "h " + m + "min " + s + "s";
        }
        if (m != 0L) {
            return m + "min " + s + "s";
        }
        return s + "s";
    }
}
