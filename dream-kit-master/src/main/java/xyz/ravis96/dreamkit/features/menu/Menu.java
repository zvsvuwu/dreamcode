package xyz.ravis96.dreamkit.features.menu;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;
import xyz.ravis96.dreamkit.helpers.ItemReplacer;
import xyz.ravis96.dreamkit.utils.ChatUtil;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public class Menu {

    private final String name;
    private final int rows;
    private final Map<Integer, ItemStack> items;

    public MenuBase build() {
        return new MenuBase(ChatUtil.fixColor(this.name), this.rows);
    }

    public MenuBase buildWithItem() {
        MenuBase menuBase = new MenuBase(ChatUtil.fixColor(this.name), this.rows);
        this.items.forEach(((integer, itemStack) ->
                menuBase.setItem(integer, new ItemReplacer(itemStack).fixColors())));

        return menuBase;
    }
}
