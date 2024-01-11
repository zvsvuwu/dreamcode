package pl.virutal.mobs.gui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class InventoryListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Inventory inventory = e.getClickedInventory();

        if (inventory == null)return;

        InventoryHolder inventoryHolder = inventory.getHolder();

        if (!(inventoryHolder instanceof GuiCreator))return;

        GuiCreator menu = (GuiCreator)inventoryHolder;
        e.setCancelled(true);
        menu.handleClickAction(e);
    }
}
