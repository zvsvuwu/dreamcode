package cc.dreamcode.sprawdzanie.content;

import cc.dreamcode.sprawdzanie.SprawdzaniePlugin;
import cc.dreamcode.sprawdzanie.command.CommandHandler;
import cc.dreamcode.sprawdzanie.config.MessageConfig;
import cc.dreamcode.sprawdzanie.config.PluginConfig;
import com.google.common.collect.ImmutableMap;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class PrzyznajeSieCommand extends CommandHandler {
    private @Inject PluginConfig pluginConfig;
    private @Inject MessageConfig messageConfig;
    private @Inject SprawdzanieManager sprawdzanieManager;

    public PrzyznajeSieCommand() {
        super("przyznajesie", null);
    }

    @Override
    public void handle(@NonNull CommandSender sender, @NonNull String[] args) {
        Player p = (Player) sender;

        if (!sprawdzanieManager.getSprawdzaniGracze().containsKey(p)) {
            send(messageConfig.senderNotBeingChecked, p);
            return;
        }
        sprawdzanieManager.getSprawdzaniGracze().remove(p);
        if (pluginConfig.sendAdmitCommand) {
            SprawdzaniePlugin.getSprawdzaniePlugin().getServer().dispatchCommand(
                    SprawdzaniePlugin.getSprawdzaniePlugin().getServer().getConsoleSender(),
                    pluginConfig.admitCommand
                            .replace("/", "")
                            .replace("{gracz}", p.getName()));
        } else {
            p.teleport(sprawdzanieManager.getSprawdzaniGracze().get(p));
            sprawdzanieManager.getSprawdzaniGracze().remove(p);
        }
        if (!pluginConfig.broadcastAdmitMessage) {
            SprawdzaniePlugin.getSprawdzaniePlugin().getServer().getOnlinePlayers().forEach(player ->
                    send(messageConfig.admitNotice, player, new ImmutableMap.Builder<String, Object>()
                            .put("gracz", p.getName())
                            .build()));
            return;
        }

        SprawdzaniePlugin.getSprawdzaniePlugin().getServer().getOnlinePlayers().forEach(player -> {
            if (!player.hasPermission("dreamspr.admin")) return;
            send(messageConfig.admitNotice, player, new ImmutableMap.Builder<String, Object>()
                    .put("gracz", p.getName())
                    .build());
        });
    }

    @Override
    public List<String> tab(@NonNull Player player, @NonNull String[] args) {
        return null;
    }
}
