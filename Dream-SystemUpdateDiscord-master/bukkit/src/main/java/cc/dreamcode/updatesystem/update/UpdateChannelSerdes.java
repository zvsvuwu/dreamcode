package cc.dreamcode.updatesystem.update;

import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;

public class UpdateChannelSerdes implements ObjectSerializer<UpdateChannel> {
    @Override
    public boolean supports(@NonNull Class<? super UpdateChannel> type) {
        return UpdateChannel.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull UpdateChannel object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("material", object.getMaterial());
        data.add("categoryName", object.getCategoryName());
        data.add("categoryDisplay", object.getCategoryDisplay());
    }

    @Override
    public UpdateChannel deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        XMaterial xMaterial = data.get("material", XMaterial.class);
        String categoryName = data.get("categoryName", String.class);
        String categoryDisplay = data.get("categoryDisplay", String.class);
        return new UpdateChannel(xMaterial, categoryName, categoryDisplay);
    }
}
