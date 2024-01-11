package cc.dreamcode.shaman.util;

import lombok.Getter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RomanUtil {

    public static String arabicToRoman(int i) {
        if (i == 0) {
            return "0";
        }
        StringBuilder result = new StringBuilder();
        while (i > 0) {
            for (RomanNumber romanNumber : RomanNumber.values()) {
                if (romanNumber.getArabic() <= i) {
                    result.append(romanNumber.name());
                    i -= romanNumber.getArabic();
                }
            }
        }
        return result.toString();
    }

    public enum RomanNumber {
        C(100),
        XC(90),
        L(50),
        XL(40),
        X(10),
        IX(9),
        V(5),
        IV(4),
        I(1);

        @Getter private final int arabic;

        RomanNumber(int i) {
            this.arabic = i;
        }
    }
}
