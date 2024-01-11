package cc.dreamcode.shulker;

import cc.dreamcode.menu.bukkit.base.BukkitMenu;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

@Data
public class Shulker {

    private final UUID ownerUuid;
    private final BukkitMenu shulkerMenu;
    private final ItemStack shulkerItem;

}
