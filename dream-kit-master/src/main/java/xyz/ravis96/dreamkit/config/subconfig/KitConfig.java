package xyz.ravis96.dreamkit.config.subconfig;

import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import xyz.ravis96.dreamkit.features.kit.Kit;
import xyz.ravis96.dreamkit.helpers.ItemBuilder;

import java.util.*;

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class KitConfig extends OkaeriConfig {

    @Comment("Ile wierszy ma posiadac gui?")
    public int rows = 3;

    @Comment("Ile wierszy ma posiadac gui z zestawem przedmiotow?")
    public int rowsKit = 3;

    @Comment("Jak gui ma sie nazywac?")
    public String name = "&8&lZestawy";

    @Comment("Jak gui z zestawem przedmiotow ma sie nazywac?")
    public String nameKit = "&2&lOdbierz zestaw!";

    @Comment("Jak gui z podgladem przedmiotow ma sie nazywac?")
    public String nameKitView = "&c&lPodglad zestawu";

    @Comment({"Ustaw ulozenie itemow w twoim gui.", "Ustawione itemy sa zablokowane event'em."})
    public Map<Integer, ItemStack> items = new HashMap<Integer, ItemStack>() {{
        this.put(12, new ItemBuilder(XMaterial.IRON_BOOTS.parseMaterial()).setName("&8&lX-X").setLore(
                " &7&lKit &e&lVIP:",
                " &4Delay: &7%TIME%",
                " ",
                "&7Kliknij &alewym&7, aby &6odebrac zestaw&7.",
                "&7Kliknij &aprawym&7, aby obejrzec go.").toItemStack());
        this.put(13, new ItemBuilder(XMaterial.LEATHER_BOOTS.parseMaterial()).setName("&8&lX-X").setLore(
                " &7&lKit &7&lGracz:",
                " &4Delay: &7%TIME%",
                " ",
                "&7Kliknij &alewym&7, aby &6odebrac zestaw&7.",
                "&7Kliknij &aprawym&7, aby obejrzec go.").toItemStack());
        this.put(14, new ItemBuilder(XMaterial.GOLDEN_BOOTS.parseMaterial()).setName("&8&lX-X").setLore(
                " &7&lKit &6&lSVIP:",
                " &4Delay: &7%TIME%",
                " ",
                "&7Kliknij &alewym&7, aby &6odebrac zestaw&7.",
                "&7Kliknij &aprawym&7, aby obejrzec go.").toItemStack());
    }};

    @Comment({"Uzupelnij liste kitow tak, jak ci sie podoba.", "Domyslna permisja: dc.kit.[name]"})
    public List<Kit> kitList = Arrays.asList(
            new Kit("gracz", 600, Collections.singletonList(
                    new ItemStack(XMaterial.GOLDEN_APPLE.parseMaterial(), 2))),
            new Kit("vip", 600, Arrays.asList(
                    new ItemStack(XMaterial.ENDER_PEARL.parseMaterial(), 2),
                    new ItemStack(XMaterial.GOLDEN_APPLE.parseMaterial(), 12))),
            new Kit("svip", 600, Arrays.asList(
                    new ItemStack(XMaterial.ENDER_PEARL.parseMaterial(), 3),
                    new ItemStack(XMaterial.GOLDEN_APPLE.parseMaterial(), 14)))
    );

    @Comment("Jaki slot odpowiada danemu kitowi?")
    public Map<Integer, String> itemEvent = new HashMap<Integer, String>() {{
        this.put(12, "vip");
        this.put(13, "gracz");
        this.put(14, "svip");
    }};

    public ItemStack backButton = new ItemBuilder(XMaterial.REDSTONE.parseMaterial()).setName("&4Powrot do zestawow.").toItemStack();

}
