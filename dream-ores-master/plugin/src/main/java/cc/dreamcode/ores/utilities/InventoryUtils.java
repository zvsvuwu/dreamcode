package cc.dreamcode.ores.utilities;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class InventoryUtils {

    public void giveItems(Player p, ItemStack... items) {
        Inventory i = p.getInventory();
        HashMap<Integer, ItemStack> notStored = i.addItem(items);

        for (Map.Entry<Integer, ItemStack> integerItemStackEntry : notStored.entrySet()) {
            p.getWorld().dropItemNaturally(p.getLocation(), integerItemStackEntry.getValue());
        }
    }
}
