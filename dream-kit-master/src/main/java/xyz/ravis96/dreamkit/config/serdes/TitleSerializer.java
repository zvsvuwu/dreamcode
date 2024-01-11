package xyz.ravis96.dreamkit.config.serdes;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import xyz.ravis96.dreamkit.helpers.TitleBuilder;

public class TitleSerializer implements ObjectSerializer<TitleBuilder> {
    @Override
    public boolean supports(@NonNull Class<? super TitleBuilder> type) {
        return TitleBuilder.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull TitleBuilder object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("title", object.getTitle());
        data.add("subtitle", object.getSubtitle());
        data.add("fade-in", object.getFadeIn());
        data.add("stay", object.getStay());
        data.add("fade-out", object.getFadeOut());
    }

    @Override
    public TitleBuilder deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        return new TitleBuilder(
                data.get("title", String.class),
                data.get("subtitle", String.class),
                data.get("fade-in", Integer.class),
                data.get("stay", Integer.class),
                data.get("fade-out", Integer.class)
        );
    }
}
