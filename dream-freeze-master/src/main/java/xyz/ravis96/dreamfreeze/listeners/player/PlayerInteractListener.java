package xyz.ravis96.dreamfreeze.listeners.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import xyz.ravis96.dreamfreeze.listeners.ListenerUse;

public class PlayerInteractListener extends ListenerUse {

    @EventHandler(ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent e) {
        if(this.pluginStorage.isFreeze() && !e.getPlayer().hasPermission("dc.freeze.bypass")) {
            e.setCancelled(true);
        }
    }
}
