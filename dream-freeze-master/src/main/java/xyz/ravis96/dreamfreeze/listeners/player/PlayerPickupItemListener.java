package xyz.ravis96.dreamfreeze.listeners.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerPickupItemEvent;
import xyz.ravis96.dreamfreeze.listeners.ListenerUse;

public class PlayerPickupItemListener extends ListenerUse {

    @EventHandler(ignoreCancelled = true)
    public void onPickup(PlayerPickupItemEvent e) {
        if(this.pluginStorage.isFreeze() && !e.getPlayer().hasPermission("dc.freeze.bypass")) {
            e.setCancelled(true);
        }
    }
}
