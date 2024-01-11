package cc.dreamcode.shaman.perk;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;

import java.util.Map;

public class PerkSerdes implements ObjectSerializer<Perk> {
    @Override
    public boolean supports(@NonNull Class<? super Perk> type) {
        return Perk.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull Perk object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("name", object.getName());
        data.add("guiName", object.getGuiName());
        data.add("slot", object.getSlot());
        data.add("desc", object.getPerkDesc());
        data.add("upgrades", object.getUpgrades());
    }

    @Override
    public Perk deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        String name = data.get("name", String.class);
        String guiName = data.get("guiName", String.class);
        int slot = data.get("slot", Integer.class);
        String desc = data.get("desc", String.class);
        Map<Integer, PerkUpgrade> upgrades = data.getAsMap("upgrades", Integer.class, PerkUpgrade.class);
        return new Perk(name, guiName, slot, desc, upgrades);
    }
}
