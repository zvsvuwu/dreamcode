package cc.dreamcode.home.rawlocation;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;

public class RawLocationSerdes implements ObjectSerializer<RawLocation> {
    @Override
    public boolean supports(@NonNull Class<? super RawLocation> type) {
        return RawLocation.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull RawLocation object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("world-name", object.getWorldName());
        data.add("x", object.getX());
        data.add("y", object.getY());
        data.add("z", object.getZ());

        if (object.getYaw() != 0.0F) {
            data.add("yaw", object.getYaw());
        }

        if (object.getPitch() != 0.0F) {
            data.add("pitch", object.getPitch());
        }
    }

    @Override
    public RawLocation deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        if (data.containsKey("yaw") && data.containsKey("pitch")) {
            return new RawLocation(
                    data.get("world-name", String.class),
                    data.get("x", Double.class),
                    data.get("y", Double.class),
                    data.get("z", Double.class),
                    data.get("yaw", Float.class),
                    data.get("pitch", Float.class)
            );
        }

        return new RawLocation(
                data.get("world-name", String.class),
                data.get("x", Double.class),
                data.get("y", Double.class),
                data.get("z", Double.class)
        );
    }
}
