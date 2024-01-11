package cc.dreamcode.shopforkills.offer;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

public class OfferSerializer implements ObjectSerializer<Offer> {
    @Override
    public boolean supports(@NonNull Class<? super Offer> type) {
        return Offer.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull Offer object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("item", object.getItem());
        data.add("slot", object.getSlot());
        data.add("kills", object.getKills());
    }

    @Override
    public Offer deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        ItemStack itemStack = data.get("item", ItemStack.class);
        int slot = data.get("slot", Integer.class);
        int kills = data.get("kills", Integer.class);
        return new Offer(itemStack, slot, kills);
    }
}
