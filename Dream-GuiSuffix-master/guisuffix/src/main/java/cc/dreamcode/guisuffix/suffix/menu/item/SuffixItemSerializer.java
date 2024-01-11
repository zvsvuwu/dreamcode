package cc.dreamcode.guisuffix.suffix.menu.item;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

public class SuffixItemSerializer implements ObjectSerializer<SuffixItem> {

    @Override
    public boolean supports(@NonNull Class<? super SuffixItem> type) {
        return SuffixItem.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull SuffixItem object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("name", object.getName());
        data.add("price", object.getPrice());
        data.add("suffix", object.getSuffix());
        data.add("slot", object.getSlot());
        data.add("item", object.getItemStack());
    }

    @Override
    public SuffixItem deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        String name = data.get("name", String.class);
        int price = data.get("price", Integer.class);
        String suffix = data.get("suffix", String.class);
        int slot = data.get("slot", Integer.class);
        ItemStack item = data.get("item", ItemStack.class);

        return new SuffixItem(name, price, suffix, slot, item);
    }
}
