package cc.dreamcode.home.controller;

import cc.dreamcode.home.HomePlugin;
import cc.dreamcode.home.config.MessageConfig;
import cc.dreamcode.home.manager.HomeManager;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class HomeController implements Listener {
    private @Inject HomePlugin homePlugin;
    private @Inject MessageConfig messageConfig;
    private @Inject HomeManager homeManager;

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        this.checkAndRemove(event.getPlayer());
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!this.homeManager.getTimeMap().containsKey(event.getPlayer().getUniqueId())) return;
        if (!this.homeManager.getLocationMap().containsKey(event.getPlayer().getUniqueId())) return;

        if (event.getTo().getX() == event.getFrom().getX()
                && event.getTo().getY() == event.getFrom().getY()
                && event.getTo().getZ() == event.getFrom().getZ()) return;

        this.homeManager.getTimeMap().remove(event.getPlayer().getUniqueId());
        this.homeManager.getLocationMap().remove(event.getPlayer().getUniqueId());

        this.messageConfig.teleportationFailed.send(event.getPlayer());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        this.checkAndRemove(event.getEntity());
    }

    private void checkAndRemove(Player player) {
        if (!this.homeManager.getTimeMap().containsKey(player.getUniqueId())) return;
        if (!this.homeManager.getLocationMap().containsKey(player.getUniqueId())) return;

        this.homeManager.getTimeMap().remove(player.getUniqueId());
        this.homeManager.getLocationMap().remove(player.getUniqueId());
    }
}
