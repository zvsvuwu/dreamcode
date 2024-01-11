package cc.dreamcode.wallet.shared;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.text.DecimalFormat;

@UtilityClass
public class MoneyUtil {

    public static String formatMoney(@NonNull String suffix, double money) {
        final DecimalFormat df = new DecimalFormat("#,###.##");
        return df.format(money) + suffix;
    }
}
