package cc.dreamcode.updatesystem.util;

import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@UtilityClass
public class DateUtil {

    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public static String getDateTime(final long time) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
        LocalDateTime now = LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault());

        long yearsBetween = ChronoUnit.YEARS.between(localDateTime, now);
        if (yearsBetween > 0) {
            if (yearsBetween == 1) {
                return "Rok temu";
            }
            else if (yearsBetween <= 4) {
                return yearsBetween + " lata temu";
            }
            else {
                return yearsBetween + " lat temu";
            }
        }

        long monthsBetween = ChronoUnit.MONTHS.between(localDateTime, now);
        if (monthsBetween > 0) {
            if (monthsBetween == 1) {
                return "Miesiac temu";
            }
            else if (monthsBetween <= 4) {
                return monthsBetween + " miesiace temu";
            }
            else {
                return monthsBetween + " miesiecy temu";
            }
        }

        long weeksBetween = ChronoUnit.WEEKS.between(localDateTime, now);
        if (weeksBetween >= 1 && weeksBetween <= 4) {
            if (weeksBetween == 1) {
                return "Tydzien temu";
            }
            return weeksBetween + " tygodne temu";
        }

        long daysBetween = ChronoUnit.DAYS.between(localDateTime, now);
        if (daysBetween == 0) {
            return "Dzisiaj o " + localDateTime.format(timeFormatter);
        }
        else if (daysBetween == 1) {
            return "Wczoraj o " + localDateTime.format(timeFormatter);
        }
        else if (daysBetween == 2) {
            return "Przedwczoraj o " + localDateTime.format(timeFormatter);
        }
        else {
            return daysBetween + " dni temu";
        }
    }

}
