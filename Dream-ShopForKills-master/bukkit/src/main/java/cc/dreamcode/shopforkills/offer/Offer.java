package cc.dreamcode.shopforkills.offer;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

@Data
@AllArgsConstructor
public class Offer {

    private ItemStack item;
    private int slot;
    private int kills;

}
