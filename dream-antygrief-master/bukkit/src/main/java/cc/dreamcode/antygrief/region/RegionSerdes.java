package cc.dreamcode.antygrief.region;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;

public class RegionSerdes implements ObjectSerializer<Region> {
    @Override
    public boolean supports(@NonNull Class<? super Region> type) {
        return Region.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull Region object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("world", object.getWorld());
        data.add("minx", object.getMinX());
        data.add("miny", object.getMinY());
        data.add("minz", object.getMinZ());
        data.add("maxx", object.getMaxX());
        data.add("maxy", object.getMaxY());
        data.add("maxz", object.getMaxZ());
    }

    @Override
    public Region deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        return new Region(
                data.get("world", String.class),
                data.get("minx", Double.class),
                data.get("miny", Double.class),
                data.get("minz", Double.class),
                data.get("maxx", Double.class),
                data.get("maxy", Double.class),
                data.get("maxz", Double.class)
        );
    }
}
