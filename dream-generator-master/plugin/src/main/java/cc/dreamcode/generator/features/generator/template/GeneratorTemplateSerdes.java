package cc.dreamcode.generator.features.generator.template;

import com.google.common.collect.ImmutableMap;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Map;

public class GeneratorTemplateSerdes implements ObjectSerializer<GeneratorTemplate> {
    @Override
    public boolean supports(@NonNull Class<? super GeneratorTemplate> type) {
        return GeneratorTemplate.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull GeneratorTemplate object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("item", object.getItemStack());
        data.add("regeneration-speed", object.getRegenerationSpeed());
        data.add("shaped-string", object.getShapedString());
        data.add("shaped", object.getShaped());
    }

    @Override
    public GeneratorTemplate deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        return new GeneratorTemplate(
                data.get("item", ItemStack.class),
                data.get("regeneration-speed", Integer.class),
                data.get("shaped-string", String.class),
                data.getAsMap("shaped", Character.class, Material.class)
        );
    }
}
