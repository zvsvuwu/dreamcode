package cc.dreamcode.ffa.user.menu;

import cc.dreamcode.ffa.config.MessageConfig;
import cc.dreamcode.ffa.config.PluginConfig;
import cc.dreamcode.ffa.user.User;
import cc.dreamcode.ffa.util.InventoryUtil;
import cc.dreamcode.menu.bukkit.BukkitMenuBuilder;
import cc.dreamcode.menu.bukkit.base.BukkitMenu;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import eu.okaeri.tasker.core.Tasker;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Map;

@RequiredArgsConstructor
public class SaveInventoryMenu {

    private final User user;
    private final Player player;

    private final Tasker tasker;

    private final PluginConfig pluginConfig;
    private final MessageConfig messageConfig;

    public void open() {
        BukkitMenuBuilder menuBuilder = this.pluginConfig.saveInventoryMenu;
        BukkitMenu menu = menuBuilder.buildEmpty();
        menu.setDisposeWhenClose(true);

        for (Map.Entry<Integer, ItemStack> entry : menuBuilder.getItems().entrySet()) {
            final int slot = entry.getKey();
            final ItemStack item = entry.getValue();

            menu.setItem(slot, ItemBuilder.of(item)
                    .fixColors()
                    .toItemStack(), event -> {
                if (!item.hasItemMeta()) {
                    return;
                }
                if (item.getItemMeta().hasItemFlag(ItemFlag.HIDE_DESTROYS)) {
                    user.setInventory(new ArrayList<>());
                    this.messageConfig.resetedInventory.send(player);
                    this.tasker.newSharedChain(player.getUniqueId().toString())
                            .async(() -> user.save())
                            .execute();
                    InventoryUtil.setupInventory(player, user, this.pluginConfig);
                } else if (item.getItemMeta().hasItemFlag(ItemFlag.HIDE_ENCHANTS)) {
                    user.getInventory().clear();
                    for (int i = 0; i < player.getInventory().getContents().length; i++) {
                        user.getInventory().add(i, player.getInventory().getItem(i));
                    }
                    user.getArmor().clear();
                    for (int i = 0; i < player.getInventory().getArmorContents().length; i++) {
                        user.getArmor().add(i, player.getInventory().getArmorContents()[i]);
                    }
                    this.messageConfig.savedInventory.send(player);
                    this.tasker.newSharedChain(player.getUniqueId().toString())
                            .async(() -> user.save())
                            .execute();
                }
            });
        }

        menu.open(player);
    }

}