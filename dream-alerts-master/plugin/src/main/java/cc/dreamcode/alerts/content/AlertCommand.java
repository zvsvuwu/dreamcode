package cc.dreamcode.alerts.content;

import cc.dreamcode.alerts.AlertsPlugin;
import cc.dreamcode.alerts.command.CommandHandler;
import cc.dreamcode.alerts.command.annotations.RequiredPermission;
import cc.dreamcode.alerts.command.annotations.RequiredPlayer;
import cc.dreamcode.alerts.config.MessageConfig;
import cc.dreamcode.alerts.config.PluginConfig;
import cc.dreamcode.alerts.notice.Notice;
import com.google.common.collect.ImmutableMap;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@RequiredPlayer
@RequiredPermission(permission = "dreamalert.alert")
public class AlertCommand extends CommandHandler {
    private @Inject MessageConfig messageConfig;
    private @Inject PluginConfig pluginConfig;

    public AlertCommand() {
        super("alert", null);
    }

    @Override
    public void handle(@NonNull CommandSender sender, @NonNull String[] args) {
        Player p = (Player) sender;
        if (args.length == 0) {
            send(messageConfig.usage, p, new ImmutableMap.Builder<String, Object>()
                    .put("usage", "/alert <typ> <tresc>")
                    .build());
            return;
        }
        String msg = StringUtils.join(args, ' ', 1, args.length);
        switch (args[0]) {
            case "chat":
                AlertsPlugin.getAlertsPlugin().getServer().getOnlinePlayers().forEach(player ->
                    send(new Notice(Notice.Type.CHAT, msg), player));
                send(messageConfig.success, p);
                break;
            case "title":
                AlertsPlugin.getAlertsPlugin().getServer().getOnlinePlayers().forEach(player ->
                        send(new Notice(Notice.Type.TITLE, msg, pluginConfig.alertTime), player));
                send(messageConfig.success, p);
                break;
            case "actionbar":
                AlertsPlugin.getAlertsPlugin().getServer().getOnlinePlayers().forEach(player ->
                        send(new Notice(Notice.Type.ACTION_BAR, msg, pluginConfig.alertTime), player));
                send(messageConfig.success, p);
                break;
            case "title_subtitle":
                String main = pluginConfig.alertTitle + "%NEWLINE%" + msg;
                AlertsPlugin.getAlertsPlugin().getServer().getOnlinePlayers().forEach(player ->
                        send(new Notice(Notice.Type.TITLE_SUBTITLE, main, pluginConfig.alertTime), player));
                send(messageConfig.success, p);
                break;
            case "reload":
                pluginConfig.load();
                send(messageConfig.reloaded, p);
                break;
            default:
                send(new Notice(Notice.Type.CHAT, "&cNiepoprawny typ alertu"), p);
                break;
        }
    }

    @Override
    public List<String> tab(@NonNull Player player, @NonNull String[] args) {
        if (args.length != 1) return null;
        List<String> arguments = new ArrayList<>();
        arguments.add("chat");
        arguments.add("actionbar");
        arguments.add("title");
        arguments.add("title_subtitle");
        arguments.add("reload");
        return arguments;
    }
}
