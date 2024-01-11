package xyz.ravis96.dreamkit.nms.notice;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.ChatColor;
import xyz.ravis96.dreamkit.utils.ChatUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
@RequiredArgsConstructor
public class Notice {

    private final Type type;
    private final String text;
    private int duration = 70;

    public enum Type {
        CHAT, ACTION_BAR, SUBTITLE, TITLE, SIDEBAR
    }

    public String getColoredText() {
        return ChatUtil.fixColor(this.text);
    }
}
