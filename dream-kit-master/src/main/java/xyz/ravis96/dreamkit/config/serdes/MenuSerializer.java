package xyz.ravis96.dreamkit.config.serdes;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;
import xyz.ravis96.dreamkit.features.menu.Menu;

public class MenuSerializer implements ObjectSerializer<Menu> {
    @Override
    public boolean supports(@NonNull Class<? super Menu> type) {
        return Menu.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull Menu object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("name", object.getName());
        data.add("rows", object.getRows());
        data.addAsMap("items", object.getItems(), Integer.class, ItemStack.class);
    }

    @Override
    public Menu deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        return new Menu(data.get("name", String.class),
                data.get("rows", Integer.class),
                data.getAsMap("items", Integer.class, ItemStack.class));
    }
}
