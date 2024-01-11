package xyz.ravis96.dreamfreeze.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ChatUtil {
    public static String fixColor(final String text) {
        if (text == null) {
            return "";
        }
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static List<String> fixLore(final List<String> lore) {
        List<String> fixLore = new ArrayList<>();
        if(lore == null) {
            return fixLore;
        }
        lore.forEach(s -> fixLore.add(fixColor(s)));
        return fixLore;
    }
}
