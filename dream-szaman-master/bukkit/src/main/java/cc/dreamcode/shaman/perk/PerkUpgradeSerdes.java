package cc.dreamcode.shaman.perk;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

public class PerkUpgradeSerdes implements ObjectSerializer<PerkUpgrade> {
    @Override
    public boolean supports(@NonNull Class<? super PerkUpgrade> type) {
        return PerkUpgrade.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull PerkUpgrade object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("cost-item", object.getCost());
        data.add("cost-money", object.getMoney());
        data.add("value", object.getValue());
    }

    @Override
    public PerkUpgrade deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        ItemStack cost = data.get("cost-item", ItemStack.class);
        int money = data.get("cost-money", Integer.class);
        double value = data.get("value", Double.class);
        return new PerkUpgrade(cost, money, value);
    }
}
