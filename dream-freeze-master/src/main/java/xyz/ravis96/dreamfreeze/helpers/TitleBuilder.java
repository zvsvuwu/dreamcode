package xyz.ravis96.dreamfreeze.helpers;

import com.cryptomorin.xseries.messages.Titles;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import xyz.ravis96.dreamfreeze.PluginMain;
import xyz.ravis96.dreamfreeze.utils.ChatUtil;

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

    public void sendAll(Server server) {
        server.getOnlinePlayers().forEach(p ->
                Titles.sendTitle(p,
                this.fadeIn,
                this.stay,
                this.fadeOut,
                ChatUtil.fixColor(this.title),
                ChatUtil.fixColor(this.subtitle)));
    }

    public void sendAll(Server server, Map<String, String> mapReplacer) {
        server.getOnlinePlayers().forEach(p ->
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
