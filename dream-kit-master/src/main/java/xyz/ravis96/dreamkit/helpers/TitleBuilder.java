package xyz.ravis96.dreamkit.helpers;

import com.cryptomorin.xseries.messages.Titles;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import xyz.ravis96.dreamkit.PluginMain;
import xyz.ravis96.dreamkit.utils.ChatUtil;

import java.util.Map;
import java.util.function.Function;

@Getter
@RequiredArgsConstructor
public class TitleBuilder {

    private final String title;
    private final String subtitle;
    private final int fadeIn;
    private final int stay;
    private final int fadeOut;

    public void send(Player p) {
        Titles.sendTitle(p,
                this.fadeIn,
                this.stay,
                this.fadeOut,
                ChatUtil.fixColor(this.title),
                ChatUtil.fixColor(this.subtitle));
    }

    public void send(Player p, Map<String, String> mapReplacer) {
        Titles.sendTitle(p,
                this.fadeIn,
                this.stay,
                this.fadeOut,
                ChatUtil.fixColor(this.replaceFromMap(this.title, mapReplacer)),
                ChatUtil.fixColor(this.replaceFromMap(this.subtitle, mapReplacer)));
    }

    public void sendAll() {
        PluginMain.getPluginMain().get().getServer().getOnlinePlayers().forEach(p ->
                Titles.sendTitle(p,
                this.fadeIn,
                this.stay,
                this.fadeOut,
                ChatUtil.fixColor(this.title),
                ChatUtil.fixColor(this.subtitle)));
    }

    public void sendAll(Map<String, String> mapReplacer) {
        PluginMain.getPluginMain().get().getServer().getOnlinePlayers().forEach(p ->
                Titles.sendTitle(p,
                this.fadeIn,
                this.stay,
                this.fadeOut,
                ChatUtil.fixColor(this.replaceFromMap(this.title, mapReplacer)),
                ChatUtil.fixColor(this.replaceFromMap(this.subtitle, mapReplacer))));
    }

    private String replaceFromMap(final String text, Map<String, String> map) {
        return map.entrySet().stream()
                .map(entryToReplace -> (Function<String, String>) s ->
                        s.replace(entryToReplace.getKey(), entryToReplace.getValue()))
                .reduce(Function.identity(), Function::andThen)
                .apply(text);
    }
}
