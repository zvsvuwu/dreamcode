package cc.dreamcode.dropsmp.nms.v1_16_R3.inventory;

import cc.dreamcode.dropsmp.nms.api.inventory.InventoryAccessor;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class V1_16_R3_InventoryAccessor implements InventoryAccessor {
    @Override
    public ItemStack getItemInMainHand(@NonNull Player player) {
        return player.getInventory().getItemInMainHand();
    }

    @Override
    public ItemStack getItemInOffHand(@NonNull Player player) {
        return player.getInventory().getItemInOffHand();
    }
}
