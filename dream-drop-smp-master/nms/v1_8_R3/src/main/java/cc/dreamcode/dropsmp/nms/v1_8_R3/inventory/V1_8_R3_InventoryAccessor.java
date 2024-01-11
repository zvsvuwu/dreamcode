package cc.dreamcode.dropsmp.nms.v1_8_R3.inventory;

import cc.dreamcode.dropsmp.nms.api.inventory.InventoryAccessor;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class V1_8_R3_InventoryAccessor implements InventoryAccessor {
    @Override
    public ItemStack getItemInMainHand(@lombok.NonNull Player player) {
        ItemStack itemInHand = player.getInventory().getItemInHand();
        if (itemInHand == null) {
            itemInHand = new ItemStack(Material.AIR);
        }

        return itemInHand;
    }

    @Override
    public ItemStack getItemInOffHand(@NonNull Player player) {
        return new ItemStack(Material.AIR);
    }
}
