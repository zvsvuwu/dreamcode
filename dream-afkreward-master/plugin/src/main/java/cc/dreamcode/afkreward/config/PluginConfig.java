package cc.dreamcode.afkreward.config;

import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import cc.dreamcode.utilities.builder.ListBuilder;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@Configuration(child = "config.yml")
@Header("## Dream-AfkReward (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {

    @Comment("Lista nagród za bycie afk (sekunda, przedmiot)")
    public Map<Integer, ItemStack> afkItems = new MapBuilder<Integer, ItemStack>()
            .put(60, new ItemBuilder(Material.DIAMOND)
                    .setName("&3Diament za bycie afk przez minute")
                    .setLore(new ListBuilder<String>()
                            .add("linijka 1")
                            .add("linijka 2")
                            .add("linijka 3")
                            .add("itd")
                            .build())
                    .setAmount(1)
                    .addEnchant(Enchantment.DAMAGE_ALL, 5,true)
                    .toItemStack())
            .put(120, new ItemBuilder(Material.STICK)
                    .setName("&3Giga badyl za bycie afk 2 minuty")
                    .setLore(new ListBuilder<String>()
                            .add("superancki badyl")
                            .add("oto twoja nagroda")
                            .add("ciesz sie :)")
                            .build())
                    .setAmount(1)
                    .addEnchant(Enchantment.DAMAGE_ALL, 41,true)
                    .toItemStack())
            .build();
}
