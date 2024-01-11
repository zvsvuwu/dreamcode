package cc.dreamcode.dropsmp.features.items;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

public class ItemSerdes implements ObjectSerializer<Item> {
    @Override
    public boolean supports(@NonNull Class<? super Item> type) {
        return Item.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull Item object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("itemStack", object.getItemStack());
        data.add("chance", object.getChance());
    }

    @Override
    public Item deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        return new Item(
                data.get("itemStack", ItemStack.class),
                data.get("chance", Double.class)
        );
    }
}