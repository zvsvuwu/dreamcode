package cc.dreamcode.fortune.fortune;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import org.bukkit.Material;

public class FortuneSerdes implements ObjectSerializer<Fortune> {

    @Override
    public boolean supports(@NonNull Class<? super Fortune> type) {
        return Fortune.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull Fortune object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("material", object.getMaterial());
        data.add("level", object.getLevel());
    }

    @Override
    public Fortune deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        return new Fortune(
                data.get("material", Material.class),
                data.get("level", int.class)
        );
    }
}