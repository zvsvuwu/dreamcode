package cc.dreamcode.wallet.product;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Data
@RequiredArgsConstructor
public class Product {

    private final String id;
    private final String name;
    private final double cost;
    private final List<ItemStack> items;
    private final List<String> commands;

    private final int slotInMenu;
    private final ItemStack displayItem;

}
