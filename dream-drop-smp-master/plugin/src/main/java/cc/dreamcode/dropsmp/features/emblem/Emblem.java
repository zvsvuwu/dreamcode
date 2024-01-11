package cc.dreamcode.dropsmp.features.emblem;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

public class Emblem {

    @Getter private final ItemStack emblem;
    @Getter private final double add;

    @Getter private final EmblemType emblemType;

    public Emblem(ItemStack itemStack, double statsAdd, EmblemType emblemType) {
        this.emblem = itemStack;
        this.add = statsAdd;
        this.emblemType = emblemType;
    }
}
