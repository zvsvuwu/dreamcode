package cc.dreamcode.shaman.config;

import cc.dreamcode.menu.bukkit.BukkitMenuBuilder;
import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import cc.dreamcode.platform.persistence.StorageConfig;
import cc.dreamcode.shaman.perk.PerkConfig;
import cc.dreamcode.utilities.builder.ListBuilder;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Configuration(
        child = "config.yml"
)
@Header("## Dream-Szaman (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {
    @Comment("Debug pokazuje dodatkowe informacje do konsoli. Lepiej wylaczyc. :P")
    public boolean debug = true;

    @Comment("Uzupelnij ponizsze menu danymi.")
    public StorageConfig storageConfig = new StorageConfig("dreamszaman");

    @Comment("Menu szamana:")
    public BukkitMenuBuilder shamanMenu = new BukkitMenuBuilder(
            "&8Szaman",
            3,
            true,
            false,
            new MapBuilder<Integer, ItemStack>()
                    .put(11, new ItemBuilder(XMaterial.APPLE.parseItem())
                            .toItemStack())
                    .put(12, new ItemBuilder(XMaterial.DIAMOND_BOOTS.parseItem())
                            .toItemStack())
                    .put(13, new ItemBuilder(XMaterial.DIAMOND_SWORD.parseItem())
                            .toItemStack())
                    .put(14, new ItemBuilder(XMaterial.BOW.parseItem())
                            .toItemStack())
                    .put(15, new ItemBuilder(XMaterial.GOLDEN_BOOTS.parseItem())
                            .toItemStack())
                    .build()
    );


    @Comment("Jak ma byc wyswietlany perk w gui?")
    public List<String> perkDisplay = new ListBuilder<String>()
            .add("&8» &fAktualny poziom: &b{level}")
            .add("&f")
            .add("&8» &fPoziomy:")
            .add("{levels}")
            .add("&f")
            .add("&8» &fKoszt: &b{cost}")
            .build();

    @Comment("Wyswietalnie zmiennej {levels} jako dostepne poziomy.")
    public String levelsDisplay = " &8• &b{lvl} &8- &f{perk-desc}";

    @Comment("Wyswietlany koszt jesli ustawiony jest item oraz monety.")
    public String costDisplayBoth = "&6{item} &7oraz &6{money}$";

    @Comment("Wyswietlany koszt jestli ustawiony jest tylko item.")
    public String costDisplayOnlyItem = "&6{item}";

    @Comment("Wyswiatlany koszt jestli ustawione sa tylko monety.")
    public String costDisplayOnlyMoney = "&6{money}";

    @Comment("Wyświetlany koszt jesli gracz ma max. level.")
    public String costMax = "&cMAX LEVEL";

    @Comment("Konfiguracja perkow:")
    public PerkConfig perks = new PerkConfig();


}
