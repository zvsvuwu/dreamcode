package cc.dreamcode.dropsmp.nms.api.inventory;

import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface InventoryAccessor {

    ItemStack getItemInMainHand(@NonNull Player player);

    ItemStack getItemInOffHand(@NonNull Player player);

}
