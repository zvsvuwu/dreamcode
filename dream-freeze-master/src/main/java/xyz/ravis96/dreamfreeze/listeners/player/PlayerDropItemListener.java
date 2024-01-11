package xyz.ravis96.dreamfreeze.listeners.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;
import xyz.ravis96.dreamfreeze.listeners.ListenerUse;

public class PlayerDropItemListener extends ListenerUse {

    @EventHandler(ignoreCancelled = true)
    public void onDrop(PlayerDropItemEvent e) {
        if(this.pluginStorage.isFreeze() && !e.getPlayer().hasPermission("dc.freeze.bypass")) {
            e.setCancelled(true);
        }
    }
}
