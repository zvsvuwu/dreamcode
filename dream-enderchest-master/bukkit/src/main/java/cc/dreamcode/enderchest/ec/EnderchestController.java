package cc.dreamcode.enderchest.ec;

import cc.dreamcode.enderchest.config.MessageConfig;
import cc.dreamcode.enderchest.config.PluginConfig;
import cc.dreamcode.enderchest.menu.EnderchestMenu;
import cc.dreamcode.enderchest.user.User;
import cc.dreamcode.enderchest.user.UserManager;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

import java.util.Optional;

public class EnderchestController implements Listener {

    private @Inject EnderchestManager enderchestManager;
    private @Inject PluginConfig pluginConfig;
    private @Inject MessageConfig messageConfig;
    private @Inject EnderchestMenu enderchestMenu;
    private @Inject UserManager userManager;

    @EventHandler(priority = EventPriority.HIGH)
    public void onEc(InventoryOpenEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (event.getInventory().getType().equals(InventoryType.ENDER_CHEST)) {
            event.setCancelled(true);
            this.enderchestManager.openMenu((Player) event.getPlayer());
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Optional<User> userOptional = this.userManager.getUser(player.getUniqueId());
        EnderchestViewer enderchestViewer = this.enderchestMenu.getEnderchestViewerMap().getOrDefault(player.getUniqueId(), null);
        if (enderchestViewer == null) {
            return;
        }
        if (event.getInventory().getHolder().equals(enderchestViewer.getInventoryHolder())) {
            userOptional.ifPresent(user -> this.enderchestManager.updateInventory(user, enderchestViewer.getSlot(), event.getInventory().getContents()));
        }

    }
}
