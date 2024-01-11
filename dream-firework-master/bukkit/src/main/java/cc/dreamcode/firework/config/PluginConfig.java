package cc.dreamcode.firework.config;

import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@Configuration(child = "config.yml")
@Header("## Dream-FireWork (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {

    @Comment("Debug pokazuje dodatkowe informacje do konsoli. Lepiej wylaczyc. :P")
    public boolean debug = true;

    @Comment("Wyglad itemu fajerwerki")
    public ItemStack fireworkItem = ItemBuilder.of(XMaterial.FIREWORK_ROCKET.parseMaterial())
            .setName("&aFajerwerka")
            .setLore("&2Kliknij prawym aby uzyc")
            .addEnchant(Enchantment.DURABILITY, 1, false)
            .toItemStack();


    @Comment("Receptura fajerwerki")
    public Map<Character, XMaterial> ingredients = new MapBuilder<Character, XMaterial>()
            .put('1', XMaterial.FIREWORK_ROCKET)
            .put('2', XMaterial.FIREWORK_ROCKET)
            .put('3', XMaterial.FIREWORK_ROCKET)
            .put('4', XMaterial.FIREWORK_ROCKET)
            .put('5', XMaterial.FIREWORK_ROCKET)
            .put('6', XMaterial.FIREWORK_ROCKET)
            .put('7', XMaterial.FIREWORK_ROCKET)
            .put('8', XMaterial.FIREWORK_ROCKET)
            .put('9', XMaterial.FIREWORK_ROCKET)
            .build();



}
