package cc.dreamcode.sprawdzanie.content;

import cc.dreamcode.sprawdzanie.SprawdzaniePlugin;
import cc.dreamcode.sprawdzanie.command.CommandHandler;
import cc.dreamcode.sprawdzanie.command.annotations.RequiredPermission;
import cc.dreamcode.sprawdzanie.config.MessageConfig;
import cc.dreamcode.sprawdzanie.config.PluginConfig;
import com.google.common.collect.ImmutableMap;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@RequiredPermission(permission = "dreamspr.admin")
public class SprawdzCommand extends CommandHandler {
    private @Inject PluginConfig pluginConfig;
    private @Inject MessageConfig messageConfig;
    private @Inject SprawdzanieManager sprawdzanieManager;

    public SprawdzCommand() {
        super("sprawdz", null);
    }

    @Override
    public void handle(@NonNull CommandSender sender, @NonNull String[] args) {
        Player p = (Player) sender;
        if (args.length != 1) {
            send(messageConfig.usage, p, new ImmutableMap.Builder<String, Object>()
                    .put("usage", "/sprawdz <gracz>")
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
        sprawdzanieManager.getSprawdzaniGracze().put(t, t.getLocation());
        if (pluginConfig.useCheckroom) {
            Location checkroom = new Location(
                    SprawdzaniePlugin.getSprawdzaniePlugin().getServer().getWorld(pluginConfig.world),
                    pluginConfig.x,
                    pluginConfig.y,
                    pluginConfig.z,
                    pluginConfig.yaw,
                    pluginConfig.pitch);
            t.teleport(checkroom);
        }
        p.teleport(t);
        if (pluginConfig.clearEffectBefore) {
            t.getActivePotionEffects().forEach(potionEffect ->
                    t.removePotionEffect(potionEffect.getType()));
        }
        if (pluginConfig.addEffects) {
            pluginConfig.effectsToAdd.forEach(t::addPotionEffect);
        }
        send(messageConfig.checkMessage, t);
        send(messageConfig.adminCheckMessage, p, new ImmutableMap.Builder<String, Object>()
                .put("gracz", t.getName())
                .build());
        if (!pluginConfig.broadcastCheckMessage) {
            SprawdzaniePlugin.getSprawdzaniePlugin().getServer().getOnlinePlayers().forEach(player ->
                    send(messageConfig.checkNotice, player, new ImmutableMap.Builder<String, Object>()
                            .put("gracz", t.getName())
                            .build()));
            return;
        }
        SprawdzaniePlugin.getSprawdzaniePlugin().getServer().getOnlinePlayers().forEach(player -> {
            if (!player.hasPermission("dreamspr.admin")) return;
            send(messageConfig.checkNotice, player, new ImmutableMap.Builder<String, Object>()
                    .put("gracz", t.getName())
                    .build());
        });
    }

    @Override
    public List<String> tab(@NonNull Player player, @NonNull String[] args) {
        if (args.length == 1) {
            List<String> arguments = new ArrayList<>();
            SprawdzaniePlugin.getSprawdzaniePlugin().getServer().getOnlinePlayers().forEach(player1 -> {
                arguments.add(player1.getName());
            });
            return arguments;
        }
        return null;
    }
}
