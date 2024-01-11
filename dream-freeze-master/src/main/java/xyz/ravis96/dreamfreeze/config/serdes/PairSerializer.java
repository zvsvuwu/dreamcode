package xyz.ravis96.dreamfreeze.config.serdes;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import xyz.ravis96.dreamfreeze.utils.pair.Pair;

public class PairSerializer implements ObjectSerializer<Pair> {
    @Override
    public boolean supports(Class<? super Pair> type) {
        return Pair.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull Pair object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("from", object.getLeft());
        data.add("to", object.getRight());
    }

    @Override
    public Pair deserialize(DeserializationData data, GenericsDeclaration generics) {
        return new Pair<>(data.get("from", Object.class),
                data.get("to", Object.class));
    }
}
