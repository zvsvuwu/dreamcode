package xyz.ravis96.dreamfreeze.listeners.player;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import xyz.ravis96.dreamfreeze.listeners.ListenerUse;

import java.util.Objects;

public class PlayerMoveListener extends ListenerUse {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onMove(PlayerMoveEvent e) {
        if(this.pluginStorage.isFreeze() && !e.getPlayer().hasPermission("dc.freeze.bypass")) {
            if(this.hasChangedBlockCoord(e.getFrom(), Objects.requireNonNull(e.getTo()))) {
                e.setCancelled(true);
            }
        }
    }

    private boolean hasChangedBlockCoord(final Location fromLoc, final Location toLoc) {
        return !(Objects.equals(fromLoc.getWorld(), toLoc.getWorld())
                && fromLoc.getBlockX() == toLoc.getBlockX()
                && fromLoc.getBlockY() == toLoc.getBlockY()
                && fromLoc.getBlockZ() == toLoc.getBlockZ());
    }
}
