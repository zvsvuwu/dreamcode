package cc.dreamcode.enderchest.item;

import cc.dreamcode.enderchest.util.BukkitSerialization;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class ArrayItemStackSerdes implements ObjectSerializer<ItemStack[]> {
    @Override
    public boolean supports(@NonNull Class<? super ItemStack[]> type) {
        return ItemStack[].class.isAssignableFrom(type);
    }

    @Override
    public void serialize(ItemStack @NonNull [] object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("items", BukkitSerialization.itemStackArrayToBase64(object));
    }

    @Override
    public ItemStack[] deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        String items = data.get("items", String.class);
        try {
            return BukkitSerialization.itemStackArrayFromBase64(items);
        }
        catch (IOException e) {
            return new ItemStack[0];
        }
    }
}
