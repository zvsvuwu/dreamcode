package xyz.ravis96.dreamfreeze.listeners.block;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import xyz.ravis96.dreamfreeze.listeners.ListenerUse;

public class BlockPlaceListener extends ListenerUse {

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent e) {
        if(this.pluginStorage.isFreeze() && !e.getPlayer().hasPermission("dc.freeze.bypass")) {
            e.setCancelled(true);
        }
    }
}
