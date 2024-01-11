package cc.dreamcode.antygrief.controller;

import cc.dreamcode.antygrief.AntyGriefPlugin;
import cc.dreamcode.antygrief.config.MessageConfig;
import cc.dreamcode.antygrief.config.PluginConfig;
import cc.dreamcode.antygrief.hook.PluginHookManager;
import cc.dreamcode.antygrief.hook.funnyguilds.FunnyGuildsHook;
import cc.dreamcode.antygrief.manager.AntyGriefManager;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

public class AntyGriefController implements Listener {
    private @Inject AntyGriefPlugin antyGriefPlugin;
    private @Inject PluginConfig pluginConfig;
    private @Inject MessageConfig messageConfig;
    private @Inject AntyGriefManager antyGriefManager;
    private @Inject PluginHookManager pluginHookManager;

    @EventHandler
    public void blockPlaceEvent(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if (this.pluginConfig.usePermissionBypass) {
            if (player.hasPermission("dream-antigrief.bypass")) return;
        }

        AtomicBoolean onGuildRegion = new AtomicBoolean(false);

        if (this.pluginConfig.ignoreGuild) {
            this.pluginHookManager.get(FunnyGuildsHook.class).ifPresent(funnyGuildsHook -> {
                onGuildRegion.set(funnyGuildsHook.onGuildRegion(event.getBlockPlaced().getLocation()));
            });

            if (onGuildRegion.get()) return;
        }

        AtomicBoolean isInRegion = new AtomicBoolean(false);

        if (this.pluginConfig.useRegions) {
            this.pluginConfig.regions.forEach(region -> {

                Location minLoc = new Location(
                        this.antyGriefPlugin.getServer().getWorld(region.getWorld()),
                        region.getMinX(),
                        region.getMinY(),
                        region.getMinZ());

                Location maxLoc = new Location(
                        this.antyGriefPlugin.getServer().getWorld(region.getWorld()),
                        region.getMaxX(),
                        region.getMaxY(),
                        region.getMaxZ()
                );

                if (!event.getBlockPlaced().getWorld().equals(minLoc.getWorld())) return;
                if (!event.getBlockPlaced().getLocation().toVector().isInAABB(minLoc.toVector(), maxLoc.toVector())) return;

                isInRegion.set(true);
            });

            if (isInRegion.get()) return;
        }

        this.pluginConfig.blocksList.forEach(material -> {

            if (event.getBlockPlaced().getType() != material) return;
            if (event.getBlockPlaced().getLocation().getY() < this.pluginConfig.yLevel) return;

            if (this.pluginConfig.sendNotice) {
                if (!this.antyGriefManager.getPlayersOnCooldown().containsKey(player.getUniqueId())
                        || System.currentTimeMillis() - this.antyGriefManager.getPlayersOnCooldown().get(player.getUniqueId())
                        >= this.pluginConfig.messageCooldown * 1000L) {
                    this.antyGriefManager.getPlayersOnCooldown().put(player.getUniqueId(), System.currentTimeMillis());
                    this.messageConfig.blockPlaced.send(player, Collections.singletonMap(
                            "time",
                            this.pluginConfig.removeTime));
                }
            }

            this.antyGriefPlugin.getServer().getScheduler().runTaskLater(
                    this.antyGriefPlugin, () ->  {
                        if (event.getBlockPlaced().getType() != material) return;
                        event .getBlockPlaced().setType(Material.AIR);
                    },
                    this.pluginConfig.removeTime * 20L);
        });
    }
}
