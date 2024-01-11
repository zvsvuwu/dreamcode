package cc.dreamcode.dropsmp.nms.v1_11_R1.inventory;

import cc.dreamcode.dropsmp.nms.api.inventory.InventoryAccessor;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class V1_11_R1_InventoryAccessor implements InventoryAccessor {
    @Override
    public ItemStack getItemInMainHand(@NonNull Player player) {
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if (itemInHand == null) {
            itemInHand = new ItemStack(Material.AIR);
        }

        return itemInHand;
    }

    @Override
    public ItemStack getItemInOffHand(@NonNull Player player) {
        ItemStack itemInOffHand = player.getInventory().getItemInOffHand();
        if (itemInOffHand == null) {
            itemInOffHand = new ItemStack(Material.AIR);
        }

        return itemInOffHand;
    }
}
