package cc.dreamcode.killreward.util;

public class PercentUtil {

    public static String formatPercent(double percent) {
        return percent * 100 + "%";
    }
    public static double unFormatPercent(String formattedPercent) {
        return Double.parseDouble(formattedPercent.replace("%", "")) / 100;
    }
}
