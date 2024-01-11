package cc.dreamcode.enderchest.ec;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.inventory.InventoryHolder;

@Data
@AllArgsConstructor
public class EnderchestViewer {

    private int slot;
    private InventoryHolder inventoryHolder;
}
