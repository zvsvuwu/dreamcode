package cc.dreamcode.enderchest.config;

import cc.dreamcode.enderchest.ec.EnderchestType;
import cc.dreamcode.menu.bukkit.BukkitMenuBuilder;
import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import cc.dreamcode.platform.persistence.StorageConfig;
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

import java.util.HashMap;
import java.util.List;

@Configuration(
        child = "config.yml"
)
@Header("## Dream-Enderchest (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {
    @Comment("Debug pokazuje dodatkowe informacje do konsoli. Lepiej wylaczyc. :P")
    public boolean debug = true;

    @Comment("Uzupelnij ponizsze menu danymi.")
    public StorageConfig storageConfig = new StorageConfig("dreamenderchest");

    public List<EnderchestType> enderchestTypes = new ListBuilder<EnderchestType>()
            .add(new EnderchestType(1, "&FGRACZ", null))
            .add(new EnderchestType(2, "&FGRACZ", null))
            .add(new EnderchestType(3, "&FGRACZ", null))
            .add(new EnderchestType(4, "&6VIP", "ec.vip"))
            .add(new EnderchestType(5, "&6VIP", "ec.vip"))
            .add(new EnderchestType(6, "&eSVIP", "ec.svip"))
            .add(new EnderchestType(7, "&eSVIP", "ec.svip"))
            .add(new EnderchestType(8, "&aMVIP", "ec.mvip"))
            .add(new EnderchestType(9, "&aMVIP", "ec.mvip"))
            .build();

    public BukkitMenuBuilder enderchestMainMenu = new BukkitMenuBuilder(
            "&8Enderchest Menu",
            1,
            true,
            false,
            new HashMap<>()
    );

    public BukkitMenuBuilder enderchestMenu = new BukkitMenuBuilder(
            "Enderchest: ",
            4,
            false,
            false,
            new MapBuilder<Integer, ItemStack>()
                    .put(27, new ItemBuilder(XMaterial.RED_DYE.parseMaterial()).setName("&cPoprzedni").toItemStack())
                    .put(35, new ItemBuilder(XMaterial.GREEN_DYE.parseMaterial()).setName("&aNastepny").toItemStack())
                    .build()
    );

    public int previousSlot = 27;
    public int nextSlot = 35;

    public String enderchestName = "&6Enderchest #{page}";
    public List<String> enderchestLore = new ListBuilder<String>()
            .add(" &8> &7Dostep od rangi: {group}")
            .add(" &8> &aKliknij, LEWYM aby przejsc!")
            .build();

    public int saveInteraval = 1200;



}
