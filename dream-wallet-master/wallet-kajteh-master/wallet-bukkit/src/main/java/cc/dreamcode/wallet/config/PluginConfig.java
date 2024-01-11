package cc.dreamcode.wallet.config;

import cc.dreamcode.menu.bukkit.BukkitMenuBuilder;
import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import cc.dreamcode.platform.persistence.StorageConfig;
import cc.dreamcode.utilities.builder.ListBuilder;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import cc.dreamcode.wallet.citizens.NpcTop;
import cc.dreamcode.wallet.top.TopType;
import cc.dreamcode.wallet.product.Product;
import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration(
        child = "config.yml"
)
@Header("## Dream-Wallet (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {
    @Comment("Debug pokazuje dodatkowe informacje do konsoli. Lepiej wylaczyc. :P")
    public boolean debug = true;

    @Comment("")
    @Comment("Suffix pieniędzy")
    public String moneySuffix = "zł";

    @Comment("")
    @Comment("Jak ma wygladac podstawa menu dla komendy /portfel?")
    public BukkitMenuBuilder walletMenuBuilder = new BukkitMenuBuilder("&8[&bM&8] Portfel: {nick}", 5, new MapBuilder<Integer, ItemStack>()
            .put(new Integer[]{0, 8, 36, 44}, new ItemBuilder(XMaterial.BLUE_STAINED_GLASS_PANE.parseMaterial()).setName(" ").toItemStack())
            .put(new Integer[]{1, 7, 9, 17, 27, 35, 37, 43}, new ItemBuilder(XMaterial.LIGHT_BLUE_STAINED_GLASS_PANE.parseMaterial()).setName(" ").toItemStack())
            .put(new Integer[]{2, 3, 5, 6, 18, 26, 38, 39, 41, 42}, new ItemBuilder(XMaterial.WHITE_STAINED_GLASS_PANE.parseMaterial()).setName(" ").toItemStack())
            .put(16, new ItemBuilder(XMaterial.KNOWLEDGE_BOOK.parseItem())
                    .setName("&6&lStan twojego portfela")
                    .setLore("&8&l| &7Posiadasz: &f{money}")
                    .toItemStack())
            .put(25, new ItemBuilder(XMaterial.PAPER.parseItem())
                    .setName("&6&lJak doladowac portfel?")
                    .setLore("&8&l| &7Dzieki codziennym nagrodom (&f/odbierznagrode&7)",
                            "&8&l| &7Za pomoca zakupu na: &fdreamcode.cc")
                    .toItemStack())
            .put(34, new ItemBuilder(XMaterial.CHEST_MINECART.parseItem())
                    .setName("&6&lCodzienna nagroda")
                    .setLore("&8&l| &7Kliknij, aby odebrac codzienna nagrode!")
                    .toItemStack())
            .build());

    @Comment("")
    @Comment("Pod jakimi slotem ma byc mozliwosc odebrania nagrody?")
    @Comment("Jesli nie ma takiego slota, ustaw -1.")
    public int receiveDailyRewardSlot = 34;

    @Comment("")
    @Comment("Po jakim czasie gracz bedzie mogl odebrac nastepna nagrode?")
    public Duration rewardCoolDown = Duration.ofDays(1L);

    @Comment("")
    @Comment("Jaka kwote gracz ma otrzymac po odebraniu nagrody?")
    public double minReward = 0.01D;
    public double maxReward = 0.15D;

    @Comment("")
    @Comment("Jakie produkty maja sie znajdowac w menu portfela?")
    @Comment("Przykladowy produkt zostal wygenerowany przez plugin, kazdy nastepny mozna dodawac od myslnika.")
    public List<Product> productList = new ListBuilder<Product>()
            .add(new Product(
                    "vip",
                    "VIP",
                    100.0D,
                    new ArrayList<>(),
                    new ListBuilder<String>()
                            .add("lp user {nick} parent add vip")
                            .build(),
                    11,
                    new ItemBuilder(XMaterial.PAPER.parseItem())
                            .setName("&7Ranga &e[VIP] &f{nick}")
                            .setLore("",
                                    "&7Koszt rangi: &f{cost}",
                                    "",
                                    "&7Kliknij, aby &azakupic &7produkt.")
                            .toItemStack()
            ))
            .add(new Product(
                    "svip",
                    "SVIP",
                    200.0D,
                    new ArrayList<>(),
                    new ListBuilder<String>()
                            .add("lp user {nick} parent add svip")
                            .build(),
                    12,
                    new ItemBuilder(XMaterial.BOOK.parseItem())
                            .setName("&7Ranga &6[SVIP] &f{nick}")
                            .setLore("",
                                    "&7Koszt rangi: &f{cost}",
                                    "",
                                    "&7Kliknij, aby &azakupic &7produkt.")
                            .toItemStack()
            ))
            .add(new Product(
                    "diamond64",
                    "DIAMOND64",
                    50.0D,
                    new ListBuilder<ItemStack>()
                            .add(new ItemStack(Material.DIAMOND, 64))
                            .build(),
                    new ArrayList<>(),
                    13,
                    new ItemBuilder(XMaterial.DIAMOND.parseItem())
                            .setName("&9Diamenty &7(64x)")
                            .setLore("",
                                    "&7Koszt rangi: &f{cost}",
                                    "",
                                    "&7Kliknij, aby &azakupic &7produkt.")
                            .toItemStack()
            ))
            .build();

    @Comment("")
    @Comment("Jak ma wygladac menu od zakupu przedmiotu?")
    public BukkitMenuBuilder purchaseMenuBuilder = new BukkitMenuBuilder("&8[&bM&8] Zakup przedmiotu: &6{name}", 3, new MapBuilder<Integer, ItemStack>()
            .put(new Integer[]{10, 11}, new ItemBuilder(XMaterial.LIME_DYE.parseItem())
                    .setName("&aZakup ten produkt")
                    .toItemStack())
            .put(13, new ItemBuilder(XMaterial.PAPER.parseItem())
                    .setName("&7Nazwa produktu: &f{name}")
                    .setLore("&7Koszt: &f{cost}",
                            "",
                            "&7Wybierz &ajedna &7z dostepnych opcji.")
                    .toItemStack())
            .put(new Integer[]{15, 16}, new ItemBuilder(XMaterial.RED_DYE.parseItem())
                    .setName("&cPowrót do menu glównego")
                    .toItemStack())
            .build());

    @Comment("")
    @Comment("Pod jakimi slotami ma sie znajdowac przycisk od zatwierdzania zakupu produktu?")
    public List<Integer> purchaseProductSlot = Arrays.asList(10, 11);

    @Comment("")
    @Comment("Pod jakimi slotami ma sie znajdowac przycisk od anulowania oferty i powrotu do menu glownego?")
    public List<Integer> declineOfferSlot = Arrays.asList(15, 16);

    @Comment("")
    @Comment("Ile ma być topek pieniędzy oraz wydanych pieniędzy?")
    public int topsAmount = 50;

    @Comment("")
    @Comment("Placeholdery w PlaceholderAPI:")
    @Comment("%dream-wallet_daily-reward% - pobiera pozostały czas do nagrody")
    @Comment("%dream-wallet_money% - pobiera pieniądze gracza")
    @Comment("%dream-wallet_player-top-money-{top}% - pobiera nick gracza z miejscem {top} w rankingu posiadanych pieniędzy")
    @Comment("%dream-wallet_money-top-money-{top}% - pobiera pieniądze gracza z miejscem {top} w rankingu posiadanych pieniędzy")
    @Comment("%dream-wallet_player-top-money-spent-{top}% - pobiera nick gracza z miejscem {top} w rankingu wydanych pieniędzy")
    @Comment("%dream-wallet_money-top-money-spent-{top}% - pobiera pieniądze gracza z miejscem {top} w rankingu wydanych pieniędzy")

    @Comment("")
    @Comment("Czy wsparcie dla Citizens ma być włączone?")
    public boolean npcTopEnabled = false;

    @Comment("")
    @Comment("Aby ta funkcja zadziałała potrzebujesz pluginu Citizens")
    public List<NpcTop> npcTopSkins = Arrays.asList(
            new NpcTop(1, 1, TopType.MONEY),
            new NpcTop(2, 2, TopType.MONEY),
            new NpcTop(3, 3, TopType.MONEY),
            new NpcTop(4, 1, TopType.MONEY_SPENT),
            new NpcTop(5, 2, TopType.MONEY_SPENT),
            new NpcTop(6, 3, TopType.MONEY_SPENT)
    );

    public String defaultPlayerNick = "error404";

    @Comment("")
    @Comment("Jesli chcesz wykorzystac swoja baze danych, uzupelnij dane ponizej.")
    public StorageConfig storageConfig = new StorageConfig("dreamwallet");
}
