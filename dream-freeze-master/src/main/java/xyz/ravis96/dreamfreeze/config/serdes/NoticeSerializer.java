package xyz.ravis96.dreamfreeze.config.serdes;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import xyz.ravis96.dreamfreeze.nms.notice.Notice;

public class NoticeSerializer implements ObjectSerializer<Notice> {
    @Override
    public boolean supports(Class<? super Notice> type) {
        return Notice.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull Notice object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("type", object.getType());
        data.add("text", object.getText());
    }

    @Override
    public Notice deserialize(DeserializationData data, GenericsDeclaration generics) {
        return new Notice(data.get("type", Notice.Type.class),
                data.get("text", String.class));
    }
}
