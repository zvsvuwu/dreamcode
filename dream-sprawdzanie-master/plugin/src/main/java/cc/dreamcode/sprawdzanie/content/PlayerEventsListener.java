package cc.dreamcode.sprawdzanie.content;

import cc.dreamcode.sprawdzanie.SprawdzaniePlugin;
import cc.dreamcode.sprawdzanie.config.MessageConfig;
import cc.dreamcode.sprawdzanie.config.PluginConfig;
import cc.dreamcode.sprawdzanie.notice.NoticeSender;
import com.google.common.collect.ImmutableMap;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerEventsListener implements Listener, NoticeSender {
    private @Inject PluginConfig pluginConfig;
    private @Inject MessageConfig messageConfig;
    private @Inject SprawdzanieManager sprawdzanieManager;

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        if (!sprawdzanieManager.getSprawdzaniGracze().containsKey(p)) return;
        if (!pluginConfig.sendLogoutCommand) return;

        if (!pluginConfig.broadcastLogoutMessage) {
            SprawdzaniePlugin.getSprawdzaniePlugin().getServer().getOnlinePlayers().forEach(player ->
                send(messageConfig.playerLogoutMessage, player, new ImmutableMap.Builder<String, Object>()
                        .put("gracz", p.getName())
                        .build()));
        } else {
            SprawdzaniePlugin.getSprawdzaniePlugin().getServer().getOnlinePlayers().forEach(player -> {
                if (!player.hasPermission("dreamspr.admin")) return;
                send(messageConfig.playerLogoutMessage, player, new ImmutableMap.Builder<String, Object>()
                        .put("gracz", p.getName())
                        .build());
            });
        }
        SprawdzaniePlugin.getSprawdzaniePlugin().getServer().getScheduler().runTask(SprawdzaniePlugin.getSprawdzaniePlugin(), () ->
           SprawdzaniePlugin.getSprawdzaniePlugin().getServer().dispatchCommand(
                   SprawdzaniePlugin.getSprawdzaniePlugin().getServer().getConsoleSender(),
                   pluginConfig.logoutCommand
                           .replace("/", "")
                           .replace("{gracz}", p.getName())));
        sprawdzanieManager.getSprawdzaniGracze().remove(p);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (!sprawdzanieManager.getSprawdzaniGracze().containsKey(p)) return;
        if (pluginConfig.mouseMovementIfBeingChecked) {
            if (e.getFrom().getZ() == e.getTo().getZ() && e.getFrom().getX() == e.getTo().getX()) return;
            e.setCancelled(true);
            return;
        }
        if (!pluginConfig.moveIfBeingChecked) e.setCancelled(true);
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        if (!pluginConfig.blockCommands) return;
        if (!sprawdzanieManager.getSprawdzaniGracze().containsKey(p)) return;
        String[] args = e.getMessage().split(" ");
        if (!pluginConfig.commandList.contains(args[0].toLowerCase())) return;
        send(messageConfig.commandsBlocked, p);
        e.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();
        if (!sprawdzanieManager.getSprawdzaniGracze().containsKey(p)) return;
        if (pluginConfig.damage) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (!sprawdzanieManager.getSprawdzaniGracze().containsKey(p)) return;
        if (pluginConfig.breakBlocks) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (!sprawdzanieManager.getSprawdzaniGracze().containsKey(p)) return;
        if (pluginConfig.placeBlocks) return;
        e.setCancelled(true);
    }
}
