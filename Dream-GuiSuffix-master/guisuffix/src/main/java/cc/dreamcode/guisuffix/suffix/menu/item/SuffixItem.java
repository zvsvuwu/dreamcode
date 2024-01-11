package cc.dreamcode.guisuffix.suffix.menu.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
@AllArgsConstructor
public class SuffixItem {

    private final String name;

    private final Integer price;

    private final String suffix;

    private final int slot;

    private final ItemStack itemStack;
}
