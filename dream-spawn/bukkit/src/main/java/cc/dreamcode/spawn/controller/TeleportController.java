package cc.dreamcode.spawn.controller;

import cc.dreamcode.spawn.SpawnManager;
import cc.dreamcode.spawn.SpawnPlugin;
import cc.dreamcode.spawn.config.MessageConfig;
import cc.dreamcode.spawn.config.PluginConfig;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.Objects;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class TeleportController implements Listener {

    private final SpawnPlugin spawnPlugin;
    private final SpawnManager spawnManager;
    private final PluginConfig config;
    private final MessageConfig messageConfig;

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if(this.spawnManager.isPlayerTeleporting(player)) {
            this.spawnManager.removeTeleport(player);
            this.config.teleportEffects.forEach(player::removePotionEffect);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if ((event.getFrom().getBlockX() == Objects.requireNonNull(event.getTo()).getBlockX()) && (event.getFrom().getBlockZ() == event.getTo().getBlockZ()) &&
                (event.getFrom().getBlockY() == event.getTo().getBlockY())) {
            return;
        }

        if(!this.spawnManager.isPlayerTeleporting(event.getPlayer())) return;

        Player player = event.getPlayer();

        this.messageConfig.moveMessage.send(player);
        this.spawnManager.removeTeleport(player);
        this.config.teleportEffects.forEach(player::removePotionEffect);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if(this.config.teleportAfterDeath) {
            event.setRespawnLocation(this.spawnManager.getSpawnLocation());
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(this.config.teleportAfterJoin) {
            player.teleport(this.spawnManager.getSpawnLocation());
            return;
        }

        if(this.config.teleportAfterFirstJoin && !player.hasPlayedBefore()) {
            player.teleport(this.spawnManager.getSpawnLocation());
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if(this.spawnManager.isPlayerTeleporting(player)) {
            this.spawnManager.removeTeleport(player);
            this.config.teleportEffects.forEach(player::removePotionEffect);
        }
        if(this.config.autoRespawn) {
            this.spawnPlugin.getServer().getScheduler().runTask(this.spawnPlugin, () -> player.spigot().respawn());
        }
    }
}
