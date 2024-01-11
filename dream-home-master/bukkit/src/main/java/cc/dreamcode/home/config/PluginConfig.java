package cc.dreamcode.home.config;

import cc.dreamcode.menu.serdes.bukkit.BukkitMenuBuilder;
import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import cc.dreamcode.platform.bukkit.persistence.StorageConfig;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@Configuration(child = "config.yml")
@Header("## Dream-Home (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {

    @Comment("Uzupelnij ponizsze menu danymi.")
    public StorageConfig storageConfig = new StorageConfig();

    @Comment("Przypisz permisje do kazdego slotu home w gui")
    public Map<Integer, String> homeLimits = new MapBuilder<Integer, String>()
            .put(18, "twojserwer.gracz")
            .put(20, "twojserwer.vip")
            .put(22, "twojserwer.vip")
            .put(24, "twojserwer.vip+")
            .put(26, "twojserwer.mvp")
            .build();

    public BukkitMenuBuilder homeMenuBuilder = new BukkitMenuBuilder(
            "Domy",
            5,
            true,
            new MapBuilder<Integer, ItemStack>()
                    .put(18, new ItemStack(Material.STONE))
                    .put(20, new ItemStack(Material.STONE))
                    .put(22, new ItemStack(Material.STONE))
                    .put(24, new ItemStack(Material.STONE))
                    .put(26, new ItemStack(Material.STONE))
                    .build());

    @Comment("Jak bedzie wygladal przedmiot w gui home, ktory bedzie oznaczal dostepny home dla danego gracza")
    public ItemStack availableHome = new ItemBuilder(XMaterial.GREEN_WOOL.parseMaterial())
            .setName("&aDom #{number}")
            .setLore("&7Nacisnij LPM, aby przeteleportowac sie do tego domu",
                    "&7Nacisnij PPM, aby zapisac lokalizacje tego domu")
            .setAmount(1)
            .toItemStack();

    @Comment("Jak bedzie wygladal przedmiot w gui home, ktory bedzie oznaczal niedostepny home dla danego gracza")
    public ItemStack notAvailableHome = new ItemBuilder(XMaterial.RED_WOOL.parseMaterial())
            .setName("&cDom #{number}")
            .setLore("&7Ten dom jest niedostepny dla Ciebie.",
                    "&7Zakup range na twojserwer.pl aby otrzymac",
                    "&7dostep do wiekszej ilosci domow")
            .setAmount(1)
            .toItemStack();

    @Comment("Do danej permisji przypisz czas teleportacji (GRACZ NIE MOZE MIEC 2 PERMISJI NA RAZ!!!)")
    public Map<String, Integer> teleportationTime = new MapBuilder<String, Integer>()
            .put("twojserwer.vip", 3)
            .put("twojserwer.gracz", 5)
            .build();
}
