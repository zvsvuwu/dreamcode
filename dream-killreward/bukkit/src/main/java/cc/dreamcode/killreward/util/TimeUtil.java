package cc.dreamcode.killreward.util;

import java.util.concurrent.TimeUnit;

public class TimeUtil {

    public static String formatMilliseconds(long milliseconds) {
        if (milliseconds < 1000) {
            return "<1s";
        } else {
            long days = TimeUnit.MILLISECONDS.toDays(milliseconds);
            long hours = TimeUnit.MILLISECONDS.toHours(milliseconds) % 24;
            long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds) % 60;
            long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60;

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
                formattedTime.append(seconds).append("s ");
            }
            return formattedTime.toString().trim();
        }
    }
}
