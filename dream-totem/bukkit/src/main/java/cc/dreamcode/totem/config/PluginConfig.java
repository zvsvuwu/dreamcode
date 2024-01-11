package cc.dreamcode.totem.config;

import cc.dreamcode.menu.bukkit.BukkitMenuBuilder;
import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import cc.dreamcode.platform.persistence.StorageConfig;
import cc.dreamcode.totem.TotemEffect;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

@Configuration(
        child = "config.yml"
)
@Header("## Dream-Totem (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {
    @Comment("Debug pokazuje dodatkowe informacje do konsoli. Lepiej wylaczyc. :P")
    public boolean debug = true;

    @Comment("Uzupelnij ponizsze menu danymi.")
    public StorageConfig storageConfig = new StorageConfig("dreamtotem");

    @Comment("Ustaw efekty (duration: 20 = 1 sekunda).")
    public Map<String, TotemEffect> effects = new MapBuilder<String, TotemEffect>()
            .put("strength", new TotemEffect(PotionEffectType.INCREASE_DAMAGE,
                    false,
                    100,
                    2,
                    0,
                    12,
                    XMaterial.TOTEM_OF_UNDYING.parseMaterial(),
                    "&bSiła III &8(&35s&8)",
                    Arrays.asList("&7Cena: &f{price}&7$", "&7Wymagane przedmioty: &f16 Bloków złota", " ", "§8x §7Kliknij §bPPM §7aby zakupić."),
                    Collections.singletonList(new ItemBuilder(XMaterial.SUNFLOWER.parseMaterial(),
                                    16)
                                    .setName("&8> &6MONETA &8<")
                                    .setLore(Arrays.asList("Waluta serwerowa", "Możesz ją użyć w menu totemów"))
                                    .addEnchant(Enchantment.DAMAGE_ALL, 1, true)
                            .toItemStack())))

            .put("speed", new TotemEffect(PotionEffectType.SPEED,
                    false,
                    60,
                    2,
                    0,
                    13,
                    XMaterial.TOTEM_OF_UNDYING.parseMaterial(),
                    "&bSzybkość III &8(&33s&8)",
                    Arrays.asList("&7Cena: &f{price}&7$", "&7Wymagane przedmioty: &f16 Bloków złota", " ", "§8x §7Kliknij §bPPM §7aby zakupić."),
                    Collections.singletonList(new ItemStack(XMaterial.GOLD_BLOCK.parseMaterial(), 16))))

            .put("resistance", new TotemEffect(PotionEffectType.DAMAGE_RESISTANCE,
                    false,
                    40,
                    1,
                    0,
                    14,
                    XMaterial.TOTEM_OF_UNDYING.parseMaterial(),
                    "&bOdporność II &8(&32s&8)",
                    Arrays.asList("&7Cena: &f{price}&7$", "&7Wymagane przedmioty: &f16 Bloków złota", " ", "§8x §7Kliknij §bPPM §7aby zakupić."),
                    Collections.singletonList(new ItemStack(XMaterial.GOLD_BLOCK.parseMaterial(), 16))))
            .build();


    @Comment("Ustaw menu:")
    public BukkitMenuBuilder totemMenu = new BukkitMenuBuilder(
            "&6&lWybór efektów",
            3,
            new MapBuilder<Integer, ItemStack>()
                    .put(4, new ItemBuilder(XMaterial.HOPPER.parseItem())
                            .setName("&8> &6&lWYBIERZ EFEKT &8<")
                            .toItemStack())
                    .put(22, new ItemBuilder(XMaterial.HOPPER.parseItem())
                            .setName("&8> &6&lWYBIERZ EFEKT &8<")
                            .toItemStack())
                    .build()
    );

    @Comment("Czy ustawić tło menu")
    public boolean fillInventory = true;

    @Comment("Ustaw przedmiot, który ma być na tle menu")
    public ItemStack fillMenuItem = new ItemBuilder(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
            .setName(" ")
            .toItemStack();
}
