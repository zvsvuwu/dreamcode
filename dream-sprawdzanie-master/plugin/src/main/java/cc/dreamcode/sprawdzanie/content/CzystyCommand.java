package cc.dreamcode.sprawdzanie.content;

import cc.dreamcode.sprawdzanie.SprawdzaniePlugin;
import cc.dreamcode.sprawdzanie.command.CommandHandler;
import cc.dreamcode.sprawdzanie.command.annotations.RequiredPermission;
import cc.dreamcode.sprawdzanie.config.MessageConfig;
import cc.dreamcode.sprawdzanie.config.PluginConfig;
import com.google.common.collect.ImmutableMap;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@RequiredPermission(permission = "dreamspr.admin")
public class CzystyCommand extends CommandHandler {
    private @Inject PluginConfig pluginConfig;
    private @Inject MessageConfig messageConfig;
    private @Inject SprawdzanieManager sprawdzanieManager;

    public CzystyCommand() {
        super("czysty", null);
    }

    @Override
    public void handle(@NonNull CommandSender sender, @NonNull String[] args) {
        Player p = (Player) sender;
        if (args.length != 1) {
            send(messageConfig.usage, p, new ImmutableMap.Builder<String, Object>()
                    .put("usage", "/czysty <gracz>")
                    .build());
            return;
        }
        Player t = SprawdzaniePlugin.getSprawdzaniePlugin().getServer().getPlayerExact(args[0]);
        if (t == null) {
            send(messageConfig.noPlayer, p);
            return;
        }
        if (t == p) {
            send(messageConfig.playerIsMe, p);
            return;
        }
        if (t.hasPermission("dreamrtp.admin")) {
            send(messageConfig.playerIsAdmin, p);
            return;
        }
        if (!sprawdzanieManager.getSprawdzaniGracze().containsKey(t)) {
            send(messageConfig.playerNotBeingChecked, p);
            return;
        }
        if (pluginConfig.tpBack) {
            t.teleport(sprawdzanieManager.getSprawdzaniGracze().get(t));
        }
        sprawdzanieManager.getSprawdzaniGracze().remove(t);
        send(messageConfig.clearedPlayer, p, new ImmutableMap.Builder<String, Object>()
                .put("gracz", t.getName())
                .build());
        t.getActivePotionEffects().forEach(potionEffect ->
                t.removePotionEffect(potionEffect.getType()));
        if (!pluginConfig.broadcastClearMessage) {
            SprawdzaniePlugin.getSprawdzaniePlugin().getServer().getOnlinePlayers().forEach(player ->
                    send(messageConfig.clearNotice, player, new ImmutableMap.Builder<String, Object>()
                            .put("gracz", t.getName())
                            .build()));
            return;
        }
        SprawdzaniePlugin.getSprawdzaniePlugin().getServer().getOnlinePlayers().forEach(player -> {
            if (!player.hasPermission("dreamspr.admin")) return;
            send(messageConfig.clearNotice, player, new ImmutableMap.Builder<String, Object>()
                    .put("gracz", t.getName())
                    .build());
        });
    }

    @Override
    public List<String> tab(@NonNull Player player, @NonNull String[] args) {
        if (args.length == 1) {
            List<String> arguments = new ArrayList<>();
            sprawdzanieManager.getSprawdzaniGracze().forEach((player1, location) ->
                arguments.add(player1.getName()));
            return arguments;
        }
        return null;
    }
}
