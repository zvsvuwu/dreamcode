package cc.dreamcode.guisuffix.config.subconfig;

import cc.dreamcode.guisuffix.suffix.menu.item.SuffixItem;
import cc.dreamcode.menu.bukkit.BukkitMenuBuilder;
import cc.dreamcode.utilities.builder.ListBuilder;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;


@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MenuConfig extends OkaeriConfig {

    public BukkitMenuBuilder suffixMenu = new BukkitMenuBuilder("&6Wybierz suffix", 5,
            new MapBuilder<Integer, ItemStack>()
                    .put(new Integer[]{0, 8, 36, 44}, ItemBuilder.of(XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial(), 1).setName(" ").toItemStack())
                    .put(new Integer[]{1, 7, 9, 17, 27, 35, 37, 43}, ItemBuilder.of(XMaterial.LIME_STAINED_GLASS_PANE.parseMaterial(), 1).setName(" ").toItemStack()).build());

    @Comment("# Ustaw price na -1, jesli nie chcesz by mozna bylo kupic suffix")
    public List<SuffixItem> suffixes =
            new ListBuilder<SuffixItem>()
                    .add(new SuffixItem("bogacz", -1, " &e&lBOGACZ", 21, ItemBuilder.of(XMaterial.GOLD_INGOT.parseMaterial(), 1).setName("&7Suffix: &e&lBOGACZ{owned}").setLore(" ", "{details}").toItemStack()))
                    .add(new SuffixItem("krol", 100, " &6&lKRÓL", 22, ItemBuilder.of(XMaterial.GOLDEN_HELMET.parseMaterial(), 1).setName("&7Suffix: &6&lKRÓL{owned}").setLore("&7Cena: &e{price}$", "{details}").addFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS).toItemStack()))
                    .add(new SuffixItem("top1", 300, " &b&lTOP1", 23, ItemBuilder.of(XMaterial.DIAMOND.parseMaterial(), 1).setName("&7Suffix: &b&lTOP1{owned}").setLore("&7Cena: &e{price}$", "{details}").toItemStack()))
                    .build();

    public String owned = " &7(&a&lPOSIADANE&7)";

    public String chooseClick = "&aKliknij, aby wybrać ten suffix!";

    public String buyClick = "&aKliknij, aby kupic ten suffix!";

    public String notEnoughMoney = "&cNie stac cie na ten suffix!";

    public String noPermission = "&cNie masz dostepu do tego suffixu!";
}
