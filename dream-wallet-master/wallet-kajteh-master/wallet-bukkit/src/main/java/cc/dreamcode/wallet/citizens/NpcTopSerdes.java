package cc.dreamcode.wallet.citizens;

import cc.dreamcode.wallet.top.TopType;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;

public class NpcTopSerdes implements ObjectSerializer<NpcTop> {
    @Override
    public boolean supports(@NonNull Class<? super NpcTop> type) {
        return NpcTop.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull NpcTop object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("npcId", object.getId());
        data.add("top", object.getTop());
        data.add("topType", object.getType());
    }

    @Override
    public NpcTop deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        int npcId = data.get("npcId", Integer.class);
        int top = data.get("top", Integer.class);
        TopType type = data.get("topType", TopType.class);

        return new NpcTop(npcId, top, type);
    }
}
