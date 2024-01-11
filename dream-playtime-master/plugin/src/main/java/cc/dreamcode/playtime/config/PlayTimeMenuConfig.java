package cc.dreamcode.playtime.config;

import cc.dreamcode.menu.serdes.bukkit.BukkitMenuBuilder;
import cc.dreamcode.utilities.builders.ListBuilder;
import cc.dreamcode.utilities.builders.MapBuilder;
import cc.dreamcode.utilities.bukkit.ItemBuilder;
import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PlayTimeMenuConfig extends OkaeriConfig {

    public BukkitMenuBuilder playTimeMenu = new BukkitMenuBuilder(
            "&6Twoj czas gry",
            3,
            true,
            new MapBuilder<Integer, ItemStack>()
                    .put(13, new ItemBuilder(XMaterial.REDSTONE_TORCH.parseMaterial())
                            .setName("&6Twoj czas gry na serwerze")
                            .addEnchant(Enchantment.DURABILITY, 3)
                            .setLore(new ListBuilder<String>()
                                    .add("&eTwoj czas gry: {czas}")
                                    .build())
                            .toItemStack())
                    .build()
    );
}
