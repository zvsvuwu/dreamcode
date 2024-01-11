package xyz.ravis96.dreamfreeze.listeners.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import xyz.ravis96.dreamfreeze.listeners.ListenerUse;

public class InventoryClickListener extends ListenerUse {

    @EventHandler(ignoreCancelled = true)
    public void onClick(InventoryClickEvent e) {
        if(this.pluginStorage.isFreeze() && !e.getWhoClicked().hasPermission("dc.freeze.bypass")) {
            e.setCancelled(true);
        }
    }
}
