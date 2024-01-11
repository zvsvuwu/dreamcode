package cc.dreamcode.enderchest.ec;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;

public class EnderchestTypeSerdes implements ObjectSerializer<EnderchestType> {
    @Override
    public boolean supports(@NonNull Class<? super EnderchestType> type) {
        return EnderchestType.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull EnderchestType object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("slot", object.getSlot());
        data.add("group", object.getGroup());
        data.add("permission", object.getPermission());
    }

    @Override
    public EnderchestType deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        int slot = data.get("slot", Integer.class);
        String group = data.get("group", String.class);
        String permission = data.get("permission", String.class);
        return new EnderchestType(slot, group, permission);
    }
}
