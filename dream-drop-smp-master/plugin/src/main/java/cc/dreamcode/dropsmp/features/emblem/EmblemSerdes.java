package cc.dreamcode.dropsmp.features.emblem;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;
public class EmblemSerdes implements ObjectSerializer<Emblem> {
    @Override
    public boolean supports(@NonNull Class<? super Emblem> type) {
        return Emblem.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull Emblem object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("emblem", object.getEmblem());
        data.add("add", object.getAdd());
        data.add("emblemType", object.getEmblemType());
    }

    @Override
    public Emblem deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        return new Emblem(
                data.get("emblem", ItemStack.class),
                data.get("add", Double.class),
                data.get("emblemType", EmblemType.class)
        );
    }
}