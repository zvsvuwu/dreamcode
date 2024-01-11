package cc.dreamcode.spawn.util;

import java.util.concurrent.TimeUnit;

public class TimeUtil {

    public static String formatTime(long milliseconds) {
        if (milliseconds < 0) {
            throw new IllegalArgumentException("Time cannot be negative.");
        }

        if (milliseconds < 1000) {
            return milliseconds + "ms";
        }

        long days = TimeUnit.MILLISECONDS.toDays(milliseconds);
        milliseconds -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(milliseconds);
        milliseconds -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds);
        milliseconds -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds);

        StringBuilder formattedTime = new StringBuilder();

        if (days > 0) {
            formattedTime.append(days).append("d ");
        }
        if (hours > 0) {
            formattedTime.append(hours).append("h ");
        }
        if (minutes > 0) {
            formattedTime.append(minutes).append("m ");
        }
        if (seconds > 0) {
            formattedTime.append(seconds).append("s");
        } else if (milliseconds > 0) {
            formattedTime.append(milliseconds).append("ms");
        }

        return formattedTime.toString();
    }
}
