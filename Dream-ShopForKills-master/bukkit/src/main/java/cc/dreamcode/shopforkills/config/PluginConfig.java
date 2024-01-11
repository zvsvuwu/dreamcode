package cc.dreamcode.shopforkills.config;

import cc.dreamcode.menu.bukkit.BukkitMenuBuilder;
import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import cc.dreamcode.platform.persistence.StorageConfig;
import cc.dreamcode.shopforkills.offer.Offer;
import cc.dreamcode.utilities.builder.ListBuilder;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import com.cryptomorin.xseries.XEnchantment;
import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;
import java.util.List;

@Configuration(
        child = "config.yml"
)
@Header("## Dream-ShopForKills (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {
    @Comment("Debug pokazuje dodatkowe informacje do konsoli. Lepiej wylaczyc. :P")
    public boolean debug = true;

    @Comment("Uzupelnij ponizsze menu danymi.")
    public StorageConfig storageConfig = new StorageConfig("dreamshopee");

    public BukkitMenuBuilder shopMenu = new BukkitMenuBuilder(
            "&8Sklep za kille",
            1,
            new MapBuilder<Integer, ItemStack>()
                    .put(new Integer[] {0,1,2,3,4,5,6,7},
                            ItemBuilder.of(XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()).toItemStack())
                    .put(8, ItemBuilder.of(Material.PAPER).setName("&6Informacje")
                            .setLore("&6Twoje kille: &f{kills}")
                            .toItemStack())
                    .build()
    );

    public List<String> costLore = new ListBuilder<String>()
            .add("&f")
            .add("&6Koszt: &f{kills}zabojstw")
            .build();

    public List<Offer> offers = new ListBuilder<Offer>()
            .add(new Offer(ItemBuilder.of(XMaterial.DIAMOND_SWORD.parseMaterial())
                    .addEnchant(XEnchantment.DAMAGE_ALL.getEnchant(), 5)
                    .addEnchant(XEnchantment.FIRE_ASPECT.getEnchant(), 2)
                    .setName("&cMiecz")
                    .toItemStack(), 0, 3))
            .add(new Offer(ItemBuilder.of(XMaterial.DIAMOND_SWORD.parseMaterial())
                    .addEnchant(XEnchantment.DIG_SPEED.getEnchant(), 5)
                    .addEnchant(XEnchantment.LOOT_BONUS_BLOCKS.getEnchant(), 3)
                    .addEnchant(XEnchantment.DURABILITY.getEnchant(), 3)
                    .setName("&cMiecz")
                    .toItemStack(), 1, 5))
            .build();

    @Comment("Czy plugin ma zablokowac nabijanie statystyk na graczach o tym samym adresie ip?")
    public boolean preventStatFarmBySameAddress = true;

    @Comment("Czy plugin ma zablokowac nabijanie statystyk na tych samych graczach?")
    public boolean preventStatFarmBySamePlayer = true;

    @Comment("Po jakim czasie gracz moze znowu nabijac statystyki na tym samym graczu?")
    public Duration allowSamePlayerToIncreaseNumberOfKillsOverTime = Duration.ofMinutes(3);

}
