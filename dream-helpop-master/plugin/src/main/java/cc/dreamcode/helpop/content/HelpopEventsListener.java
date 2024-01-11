package cc.dreamcode.helpop.content;

import eu.okaeri.injector.annotation.Inject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class HelpopEventsListener implements Listener {
    private @Inject HelpopManager helpopManager;

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (!p.hasPermission("dreamhelpop.admin")) return;
        helpopManager.getHelpopList().add(p.getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (!p.hasPermission("dreamhelpop.admin")) return;
        if (!helpopManager.getHelpopList().contains(p.getUniqueId())) return;
        helpopManager.getHelpopList().add(p.getUniqueId());
    }
}
