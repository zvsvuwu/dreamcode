package cc.dreamcode.enderchest.ec;

import cc.dreamcode.enderchest.config.PluginConfig;
import cc.dreamcode.enderchest.menu.EnderchestMainMenu;
import cc.dreamcode.enderchest.menu.EnderchestMenu;
import cc.dreamcode.enderchest.user.User;
import cc.dreamcode.enderchest.user.UserManager;
import cc.dreamcode.menu.bukkit.base.BukkitMenu;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EnderchestManager {

    private @Inject PluginConfig pluginConfig;
    private @Inject UserManager userManager;
    private @Inject EnderchestMainMenu enderchestMainMenu;
    private @Inject EnderchestMenu enderchestMenu;

    public void updateInventory(User user, int slot, ItemStack[] content) {
        for (int i = 0; i < content.length; i++) {
            ItemStack is = content[i];
            if (is == null || is.getType().equals(Material.AIR)) {
                continue;
            }
            if (is.isSimilar(this.pluginConfig.enderchestMenu.getItems().get(this.pluginConfig.previousSlot)) ||
                    is.isSimilar(this.pluginConfig.enderchestMenu.getItems().get(this.pluginConfig.nextSlot))) {
                content[i] = null;
            }
        }
        user.getItems().put(slot, content);
    }

    public void openMenu(Player player) {
        BukkitMenu bukkitMenu = this.enderchestMainMenu.build(player);
        bukkitMenu.open(player);
    }
}
