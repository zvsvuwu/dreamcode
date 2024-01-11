package cc.dreamcode.leaderboard.controllers;

import cc.dreamcode.leaderboard.model.user.User;
import cc.dreamcode.leaderboard.model.user.UserRepository;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Objects;

public class LeaderboardController implements Listener {

    private @Inject UserRepository userRepository;

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockPlace(BlockPlaceEvent e) {
        final Player player = e.getPlayer();
        final User user = this.userRepository.findOrCreateByPlayer(player).join();

        user.setBlockPlaceCount(user.getBlockPlaceCount() + 1);

        if (e.getBlockPlaced().getType().equals(Material.STONE)) {
            user.setStonePlaceCount(user.getStonePlaceCount() + 1);
        }

        if (e.getBlockPlaced().getType().equals(Material.OBSIDIAN)) {
            user.setObsidianPlaceCount(user.getObsidianPlaceCount() + 1);
        }

        user.saveAsync();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent e) {
        final Player player = e.getPlayer();
        final User user = this.userRepository.findOrCreateByPlayer(player).join();

        user.setBlockBreakCount(user.getBlockBreakCount() + 1);

        if (e.getBlock().getType().equals(Material.STONE)) {
            user.setStoneBreakCount(user.getStoneBreakCount() + 1);
        }

        if (e.getBlock().getType().equals(Material.OBSIDIAN)) {
            user.setObsidianBreakCount(user.getObsidianBreakCount() + 1);
        }

        user.saveAsync();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDeath(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        final Player target = (Player) e.getEntity();
        if (e.getDamage() < target.getHealth()) {
            return;
        }

        final User userTarget = this.userRepository.findOrCreateByPlayer(target).join();
        userTarget.setDeathCount(userTarget.getDeathCount() + 1);
        userTarget.saveAsync();

        if (!(e instanceof EntityDamageByEntityEvent)) {
            return;
        }

        EntityDamageByEntityEvent damageByEntityEvent = (EntityDamageByEntityEvent) e;
        if (damageByEntityEvent.getDamager() == null || !(damageByEntityEvent.getDamager() instanceof Player)) {
            return;
        }

        final Player killer = (Player) damageByEntityEvent.getDamager();
        final User userKiller = this.userRepository.findOrCreateByPlayer(killer).join();
        userKiller.setKillCount(userKiller.getKillCount() + 1);
        userKiller.saveAsync();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerItemConsume(PlayerItemConsumeEvent e) {
        if (!e.getItem().getType().name().contains("GOLDEN_APPLE")) {
            return;
        }

        final Player player = e.getPlayer();
        final User user = this.userRepository.findOrCreateByPlayer(player).join();

        if (e.getItem().getType().name().startsWith("ENCHANTED") ||
                e.getItem().getDurability() == 1) {
            user.setKoxEatCount(user.getKoxEatCount() + 1);
        }
        else {
            user.setRefileEatCount(user.getRefileEatCount() + 1);
        }

        user.saveAsync();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        if (!e.getCause().equals(PlayerTeleportEvent.TeleportCause.ENDER_PEARL)) {
            return;
        }

        final Player player = e.getPlayer();
        final User user = this.userRepository.findOrCreateByPlayer(player).join();

        user.setPearlThrowCount(user.getPearlThrowCount() + 1);
        user.saveAsync();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerMove(PlayerMoveEvent e) {
        final Location fromLoc = e.getFrom();
        final Location toLoc = e.getTo();

        if (e.getTo() == null) {
            return;
        }

        final Player player = e.getPlayer();
        if (!(Objects.equals(fromLoc.getWorld(), toLoc.getWorld()) &&
                fromLoc.getBlockX() == toLoc.getBlockX() &&
                fromLoc.getBlockY() == toLoc.getBlockY() &&
                fromLoc.getBlockZ() == toLoc.getBlockZ())) {
            final User user = this.userRepository.findOrCreateByPlayer(player).join();

            user.setWalkDistance(user.getWalkDistance() + 1);
            user.saveAsync();
        }
    }
}
