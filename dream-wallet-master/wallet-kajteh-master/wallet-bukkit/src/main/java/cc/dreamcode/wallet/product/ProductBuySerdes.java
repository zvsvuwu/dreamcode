package cc.dreamcode.wallet.product;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;

import java.time.Instant;
import java.util.Date;

public class ProductBuySerdes implements ObjectSerializer<ProductBuy> {
    @Override
    public boolean supports(@NonNull Class<? super ProductBuy> type) {
        return ProductBuy.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull ProductBuy object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("id", object.getId());
        data.add("date", object.getDate());
    }

    @Override
    public ProductBuy deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        return new ProductBuy(
                data.get("id", String.class),
                data.get("date", Instant.class)
        );
    }
}
