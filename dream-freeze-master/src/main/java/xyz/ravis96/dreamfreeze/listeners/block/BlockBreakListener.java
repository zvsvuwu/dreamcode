package xyz.ravis96.dreamfreeze.listeners.block;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import xyz.ravis96.dreamfreeze.listeners.ListenerUse;

public class BlockBreakListener extends ListenerUse {

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent e) {
        if(this.pluginStorage.isFreeze() && !e.getPlayer().hasPermission("dc.freeze.bypass")) {
            e.setCancelled(true);
        }
    }
}
