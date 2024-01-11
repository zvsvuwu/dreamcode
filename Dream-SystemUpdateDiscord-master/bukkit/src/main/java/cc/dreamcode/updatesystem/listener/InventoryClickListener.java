package cc.dreamcode.updatesystem.listener;

import cc.dreamcode.updatesystem.config.MessageConfig;
import cc.dreamcode.updatesystem.menu.MessageMenu;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @Inject private MessageConfig messageConfig;
    @Inject private MessageMenu messageMenu;

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getInventory() == null || event.getInventory().getHolder() == null) {
            return;
        }
        if (event.getInventory().getHolder().equals(this.messageMenu.getMessageMenu().getInventory().getHolder())) {
            this.messageConfig.inventoryClickMessage.send(event.getWhoClicked());
        }
    }
}
