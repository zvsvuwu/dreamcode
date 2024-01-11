package cc.dreamcode.ffa.util;

import cc.dreamcode.ffa.config.PluginConfig;
import cc.dreamcode.ffa.user.User;
import cc.dreamcode.utilities.bukkit.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Map;

public class InventoryUtil {

    public static void setupInventory(Player player, User user, PluginConfig pluginConfig) {
        final PlayerInventory inventory = player.getInventory();
        inventory.clear();
        inventory.setArmorContents(null);
        player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));
        int added = 0, addedArmor = 0;
        if (!user.getInventory().isEmpty()) {
            for (int i = 0; i < user.getInventory().size() - 1; i++) {
                ItemStack item = user.getInventory().get(i);
                if (item == null) {
                    continue;
                }
                if (item.getType().equals(Material.AIR)) {
                    continue;
                }
                added++;
                player.getInventory().setItem(i, item);
            }
        }

        if (!user.getArmor().isEmpty()) {
            for (int i = 0; i < user.getArmor().size() - 1; i++) {
                ItemStack item = user.getArmor().get(i);
                if (item == null) {
                    continue;
                }
                if (item.getType().equals(Material.AIR)) {
                    continue;
                }
                addedArmor++;
                if (i == 0) {
                    inventory.setBoots(item);
                } else if (i == 1) {
                    inventory.setLeggings(item);
                } else if (i == 2) {
                    inventory.setChestplate(item);
                } else if (i == 3) {
                    inventory.setHelmet(item);
                }
            }
        }

        if (added == 0) {
            ItemUtil.addItems(pluginConfig.itemsAfterJoin, inventory);
        }

        if (addedArmor == 0) {
            for (Map.Entry<EquipmentSlot, ItemStack> entry : pluginConfig.equipmentAfterJoin.entrySet()) {
                final EquipmentSlot key = entry.getKey();
                final ItemStack value = entry.getValue();
                switch (key) {
                    case HAND:
                        inventory.setItemInHand(value);
                        break;
                    case FEET:
                        inventory.setBoots(value);
                        break;
                    case LEGS:
                        inventory.setLeggings(value);
                        break;
                    case CHEST:
                        inventory.setChestplate(value);
                        break;
                    case HEAD:
                        inventory.setHelmet(value);
                        break;
                }
            }
        }

        player.updateInventory();
    }
}
