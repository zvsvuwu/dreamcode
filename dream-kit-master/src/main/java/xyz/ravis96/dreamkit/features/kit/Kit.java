package xyz.ravis96.dreamkit.features.kit;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class Kit {
    
    private final String name;
    private final int coolDownTime;
    private final List<ItemStack> itemStackList;

    public String getPermission() {
        return "dc.kit." + this.name;
    }

}
