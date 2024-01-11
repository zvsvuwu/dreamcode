package cc.dreamcode.disableeffects.listener;

import cc.dreamcode.disableeffects.config.PluginConfig;
import cc.dreamcode.disableeffects.manager.RegionManager;
import cc.dreamcode.disableeffects.util.EffectRemoveUtil;
import cc.dreamcode.disableeffects.worldguard.WorldGuardStorage;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class EffectController implements Listener {

    private @Inject WorldGuardStorage worldGuardStorage;
    private @Inject RegionManager regionManager;
    private @Inject PluginConfig pluginConfig;

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!this.worldGuardStorage.isFindWorldGuard()) {
            return;
        }

        if (event.isCancelled()) {
            return;
        }

        if (event.getTo().getBlockX() != event.getFrom().getBlockX() || event.getTo().getBlockZ() != event.getFrom().getBlockZ()) {
            if (this.worldGuardStorage.getWorldGuardHook().isInBlockedRegion(event.getTo())) {
                EffectRemoveUtil.removeAllEffects(event.getPlayer());
            }
        }
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (this.regionManager.isInBlockedRegion(event.getTo())) {
            EffectRemoveUtil.removeAllEffects(event.getPlayer());
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (this.regionManager.isInBlockedRegion(event.getPlayer().getLocation())) {
            EffectRemoveUtil.removeAllEffects(event.getPlayer());
        }
    }

}
