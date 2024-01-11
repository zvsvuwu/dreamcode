package xyz.ravis96.dreamkit.config.serdes;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;
import xyz.ravis96.dreamkit.features.kit.Kit;

public class KitSerializer implements ObjectSerializer<Kit> {
    @Override
    public boolean supports(@NonNull Class<? super Kit> type) {
        return Kit.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull Kit object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("name", object.getName());
        data.add("cooldown-time", object.getCoolDownTime());
        data.add("item-list", object.getItemStackList());
    }

    @Override
    public Kit deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        return new Kit(data.get("name", String.class),
                data.get("cooldown-time", Integer.class),
                data.getAsList("item-list", ItemStack.class));
    }
}
