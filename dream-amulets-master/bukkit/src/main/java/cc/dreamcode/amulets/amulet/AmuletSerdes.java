package cc.dreamcode.amulets.amulet;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.List;

public class AmuletSerdes implements ObjectSerializer<Amulet> {
    @Override
    public boolean supports(@NonNull Class<? super Amulet> type) {
        return Amulet.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull Amulet object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("id", object.getAmuletId());
        data.add("display-name", object.getAmuletDisplayName());
        data.add("itemstack", object.getItemStack());
        data.add("permanent-effect-offhand", object.isPermanentEffectOffhand());
        data.add("amulet-effects", object.getAmuletEffects());
    }

    @Override
    public Amulet deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        String id = data.get("id", String.class);
        String displayName = data.get("display-name", String.class);
        ItemStack itemStack = data.get("itemstack", ItemStack.class);
        boolean permanentEffectOffhand = data.get("permanent-effect-offhand", boolean.class);
        List<PotionEffect> amuletEffectList = data.getAsList("amulet-effects", PotionEffect.class);
        return new Amulet(id, displayName, itemStack, permanentEffectOffhand, amuletEffectList);
    }
}
