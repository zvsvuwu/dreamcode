package cc.dreamcode.shaman.perk;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

@Data
@AllArgsConstructor
public class PerkUpgrade {
    private ItemStack cost;
    private int money;
    private double value;
}
