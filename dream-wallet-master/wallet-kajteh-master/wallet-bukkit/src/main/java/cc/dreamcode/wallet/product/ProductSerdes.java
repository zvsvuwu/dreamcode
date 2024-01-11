package cc.dreamcode.wallet.product;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

public class ProductSerdes implements ObjectSerializer<Product> {
    @Override
    public boolean supports(@NonNull Class<? super Product> type) {
        return Product.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull Product object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("product-id", object.getId());
        data.add("product-name", object.getName());
        data.add("product-cost", object.getCost());
        data.add("product-items", object.getItems());
        data.add("product-commands", object.getCommands());

        data.add("product-slot-in-menu", object.getSlotInMenu());
        data.add("product-display-item", object.getDisplayItem());
    }

    @Override
    public Product deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        return new Product(
                data.get("product-id", String.class),
                data.get("product-name", String.class),
                data.get("product-cost", Double.class),
                data.getAsList("product-items", ItemStack.class),
                data.getAsList("product-commands", String.class),
                data.get("product-slot-in-menu", Integer.class),
                data.get("product-display-item", ItemStack.class)
        );
    }
}
