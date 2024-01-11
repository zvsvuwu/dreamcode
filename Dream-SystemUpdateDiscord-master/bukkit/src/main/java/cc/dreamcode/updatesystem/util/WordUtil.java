package cc.dreamcode.updatesystem.util;

import java.util.ArrayList;
import java.util.List;

public class WordUtil {

    public static List<String> fixWords(String text) {
        String[] words = text.split("\\s+(?<!\\S)|(?=\\s)");
        List<String> lines = new ArrayList<>();

        StringBuilder stringBuilder = new StringBuilder();
        for (String word : words) {
            if (stringBuilder.length() + word.length() + 1 > 40) {
                lines.add(stringBuilder.toString());
                stringBuilder = new StringBuilder();
            }
            stringBuilder.append(word).append(" ");
        }

        lines.add(stringBuilder.toString());
        return lines;
    }
}
