package cc.dreamcode.dropsmp.features.items;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

public class Item {

    @Getter private final ItemStack itemStack;

    @Getter private final double chance;

    public Item(ItemStack itemStack, double chance) {
        this.itemStack = itemStack;
        this.chance = chance;
    }
}
