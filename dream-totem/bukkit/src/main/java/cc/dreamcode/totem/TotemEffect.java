package cc.dreamcode.totem;

import lombok.Data;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.Serializable;
import java.util.List;

@Data
public class TotemEffect implements Serializable {
    private final PotionEffectType potionEffectType;
    private final boolean forOpponent;
    private final int duration;
    private final int amplifier;
    private final int price;
    private final int slot;
    private final Material material;
    private final String displayName;
    private final List<String> lore;
    private final List<ItemStack> cost;

    public TotemEffect(PotionEffectType potionEffectType, boolean forOpponent, int duration, int amplifier, int price, int slot, Material material, String displayName, List<String> lore, List<ItemStack> cost) {
        this.potionEffectType = potionEffectType;
        this.forOpponent = forOpponent;
        this.duration = duration;
        this.amplifier = amplifier;
        this.price = price;
        this.slot = slot;
        this.material = material;
        this.displayName = displayName;
        this.lore = lore;
        this.cost = cost;
    }

    public PotionEffect getPotionEffect(){
        return new PotionEffect(potionEffectType, duration, amplifier);
    }
}
