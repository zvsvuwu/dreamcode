package cc.dreamcode.afkreward.controller;

import cc.dreamcode.afkreward.manager.AfkRewardManager;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class AfkRewardController implements Listener {
    private @Inject AfkRewardManager afkRewardManager;

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        this.afkRewardManager.getAfkPlayers().put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent event) {
        this.afkRewardManager.getAfkPlayers().remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void playerMove(PlayerMoveEvent event) {
        if (event.getFrom().getZ() == event.getTo().getZ() && event.getFrom().getX() == event.getTo().getX()) return;
        this.afkRewardManager.getAfkPlayers().put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
    }
}
