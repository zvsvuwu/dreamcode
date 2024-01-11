package cc.dreamcode.tops.config;

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
import org.bukkit.inventory.ItemStack;

@Configuration(
        child = "config.yml"
)
@Header("## Dream-Tops (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {
    @Comment("Debug pokazuje dodatkowe informacje do konsoli. Lepiej wylaczyc. :P")
    public boolean debug = true;

    @Comment("Ile osob plugin ma przechowywac w topce?")
    public long topSize = 27L;

    @Comment("Jak ma wygladac menu od topek?")
    public BukkitMenuBuilder topMenuBuilder = new BukkitMenuBuilder("Dream-Tops", 6, true, new MapBuilder<Integer, ItemStack>()
            .build());

    //WYKOPANE BLOKI

    @Comment("Wielkosc topki wykopanych blokow")
    public int blocksMinedTopSize = 10;

    @Comment("W jakim slocie ma byc topka wykopanych blokow")
    public int blocksMinedTopSlot = 20;

    @Comment("Nazwa placeholdera wykopanych blokow np blocksMinedTop(tutaj numer w topce)")
    public String blocksMinedTopPlaceHolder = "blocksMinedTop";

    @Comment("Nazwa placeholdera wykopanych blokow np blocksMinedTopAmount(tutaj numer w topce)")
    public String blocksMinedTopAmountPlaceHolder = "blocksMinedTopAmount";

    @Comment("Nazwa placeholdera wykopanych blokow gdy nie ma tam gracza")
    public String blocksMinedTopNonePlaceHolder = "Brak";

    @Comment({"Jak ma wygladac item, ktory bedzie reprezentowal osoby w topce wykopanych blokow?"})
    public ItemStack blocksBrokenTopItem = new ItemBuilder(XMaterial.DIAMOND_PICKAXE.parseItem())
            .setName("&6&lTOPKA WYKOPANYCH BLOKOW")
            .setLore(
                    "&8#1 &f{blocksMinedTop1} &6({blocksMinedTopAmount1} wykopanych blokow)",
                    "&8#2 &f{blocksMinedTop2} &6({blocksMinedTopAmount2} wykopanych blokow)",
                    "&8#3 &f{blocksMinedTop3} &6({blocksMinedTopAmount3} wykopanych blokow)",
                    "&8#4 &f{blocksMinedTop4} &6({blocksMinedTopAmount4} wykopanych blokow)",
                    "&8#5 &f{blocksMinedTop5} &6({blocksMinedTopAmount5} wykopanych blokow)",
                    "&8#6 &f{blocksMinedTop6} &6({blocksMinedTopAmount6} wykopanych blokow)",
                    "&8#7 &f{blocksMinedTop7} &6({blocksMinedTopAmount7} wykopanych blokow)",
                    "&8#8 &f{blocksMinedTop8} &6({blocksMinedTopAmount8} wykopanych blokow)",
                    "&8#9 &f{blocksMinedTop9} &6({blocksMinedTopAmount9} wykopanych blokow)",
                    "&8#10 &f{blocksMinedTop10} &6({blocksMinedTopAmount10} wykopanych blokow)")
            .toItemStack();

    //WYKOPANE DIAXY

    @Comment("Wielkosc topki wykopanych diaxow")
    public int diamondsMinedTopSize = 10;

    @Comment("W jakim slocie ma byc topka wykopanych diaxow")
    public int diamondsMinedTopSlot = 21;

    @Comment("Nazwa placeholdera wykopanych diaxow np diamondsMinedTop(tutaj numer w topce)")
    public String diamondsMinedTopPlaceHolder = "diamondsMinedTop";

    @Comment("Nazwa placeholdera wykopanych diaxow np diamondsMinedTopAmount(tutaj numer w topce)")
    public String diamondsMinedTopAmountPlaceHolder = "diamondsMinedTopAmount";

    @Comment("Nazwa placeholdera wykopanych diaxow gdy nie ma tam gracza")
    public String diamondsMinedTopNonePlaceHolder = "Brak";

    @Comment({"Jak ma wygladac item, ktory bedzie reprezentowal osoby w topce wykopanych diaxow?"})
    public ItemStack diamondsBrokenTopItem = new ItemBuilder(XMaterial.DIAMOND_ORE.parseItem())
            .setName("&6&lTOPKA WYKOPANYCH DIAMENTOW")
            .setLore(
                    "&8#1 &f{diamondsMinedTop1} &6({diamondsMinedTopAmount1} wykopanych diaxow)",
                    "&8#2 &f{diamondsMinedTop2} &6({diamondsMinedTopAmount2} wykopanych diaxow)",
                    "&8#3 &f{diamondsMinedTop3} &6({diamondsMinedTopAmount3} wykopanych diaxow)",
                    "&8#4 &f{diamondsMinedTop4} &6({diamondsMinedTopAmount4} wykopanych diaxow)",
                    "&8#5 &f{diamondsMinedTop5} &6({diamondsMinedTopAmount5} wykopanych diaxow)",
                    "&8#6 &f{diamondsMinedTop6} &6({diamondsMinedTopAmount6} wykopanych diaxow)",
                    "&8#7 &f{diamondsMinedTop7} &6({diamondsMinedTopAmount7} wykopanych diaxow)",
                    "&8#8 &f{diamondsMinedTop8} &6({diamondsMinedTopAmount8} wykopanych diaxow)",
                    "&8#9 &f{diamondsMinedTop9} &6({diamondsMinedTopAmount9} wykopanych diaxow)",
                    "&8#10 &f{diamondsMinedTop10} &6({diamondsMinedTopAmount10} wykopanych diaxow)")
            .toItemStack();


    //WYKOPANE ZELAZO

    @Comment("Wielkosc topki wykopanego zelaza")
    public int ironMinedTopSize = 10;

    @Comment("W jakim slocie ma byc topka wykopanego zelaza")
    public int ironMinedTopSlot = 24;

    @Comment("Nazwa placeholdera wykopanego zelaza np ironMinedTop(tutaj numer w topce)")
    public String ironMinedTopPlaceHolder = "ironMinedTop";

    @Comment("Nazwa placeholdera wykopanego zelaza np ironMinedTopAmount(tutaj numer w topce)")
    public String ironMinedTopAmountPlaceHolder = "ironMinedTopAmount";

    @Comment("Nazwa placeholdera wykopanego zelaza gdy nie ma tam gracza")
    public String ironMinedTopNonePlaceHolder = "Brak";

    @Comment({"Jak ma wygladac item, ktory bedzie reprezentowal osoby w topce wykopanego zelaza?"})
    public ItemStack ironBrokenTopItem = new ItemBuilder(XMaterial.IRON_ORE.parseItem())
            .setName("&6&lTOPKA WYKOPANEGO ZELAZA")
            .setLore(
                    "&8#1 &f{ironMinedTop1} &6({ironMinedTopAmount1} wykopanego zelaza)",
                    "&8#2 &f{ironMinedTop2} &6({ironMinedTopAmount2} wykopanego zelaza)",
                    "&8#3 &f{ironMinedTop3} &6({ironMinedTopAmount3} wykopanego zelaza)",
                    "&8#4 &f{ironMinedTop4} &6({ironMinedTopAmount4} wykopanego zelaza)",
                    "&8#5 &f{ironMinedTop5} &6({ironMinedTopAmount5} wykopanego zelaza)",
                    "&8#6 &f{ironMinedTop6} &6({ironMinedTopAmount6} wykopanego zelaza)",
                    "&8#7 &f{ironMinedTop7} &6({ironMinedTopAmount7} wykopanego zelaza)",
                    "&8#8 &f{ironMinedTop8} &6({ironMinedTopAmount8} wykopanego zelaza)",
                    "&8#9 &f{ironMinedTop9} &6({ironMinedTopAmount9} wykopanego zelaza)",
                    "&8#10 &f{ironMinedTop10} &6({ironMinedTopAmount10} wykopanego zelaza)")
            .toItemStack();


    //WYKOPANE EMERALDY

    @Comment("Wielkosc topki wykopanych emeraldow")
    public int emeraldsMinedTopSize = 10;

    @Comment("W jakim slocie ma byc topka wykopanych emeraldow")
    public int emeraldsMinedTopSlot = 22;

    @Comment("Nazwa placeholdera wykopanych emeraldow np emeraldsMinedTop(tutaj numer w topce)")
    public String emeraldsMinedTopPlaceHolder = "emeraldsMinedTop";

    @Comment("Nazwa placeholdera wykopanych emeraldow np emeraldsMinedTopAmount(tutaj numer w topce)")
    public String emeraldsMinedTopAmountPlaceHolder = "emeraldsMinedTopAmount";

    @Comment("Nazwa placeholdera wykopanych emeraldow gdy nie ma tam gracza")
    public String emeraldsMinedTopNonePlaceHolder = "Brak";

    @Comment({"Jak ma wygladac item, ktory bedzie reprezentowal osoby w topce wykopanych emeraldow?"})
    public ItemStack emeraldsBrokenTopItem = new ItemBuilder(XMaterial.EMERALD_ORE.parseItem())
            .setName("&6&lTOPKA WYKOPANYCH EMERALDOW")
            .setLore(
                    "&8#1 &f{emeraldsMinedTop1} &6({emeraldsMinedTopAmount1} wykopanych emeraldow)",
                    "&8#2 &f{emeraldsMinedTop2} &6({emeraldsMinedTopAmount2} wykopanych emeraldow)",
                    "&8#3 &f{emeraldsMinedTop3} &6({emeraldsMinedTopAmount3} wykopanych emeraldow)",
                    "&8#4 &f{emeraldsMinedTop4} &6({emeraldsMinedTopAmount4} wykopanych emeraldow)",
                    "&8#5 &f{emeraldsMinedTop5} &6({emeraldsMinedTopAmount5} wykopanych emeraldow)",
                    "&8#6 &f{emeraldsMinedTop6} &6({emeraldsMinedTopAmount6} wykopanych emeraldow)",
                    "&8#7 &f{emeraldsMinedTop7} &6({emeraldsMinedTopAmount7} wykopanych emeraldow)",
                    "&8#8 &f{emeraldsMinedTop8} &6({emeraldsMinedTopAmount8} wykopanych emeraldow)",
                    "&8#9 &f{emeraldsMinedTop9} &6({emeraldsMinedTopAmount9} wykopanych emeraldow)",
                    "&8#10 &f{emeraldsMinedTop10} &6({emeraldsMinedTopAmount10} wykopanych emeraldow)")
            .toItemStack();

    //WYKOPANE ZLOTO

    @Comment("Wielkosc topki wykopanego zlota")
    public int goldMinedTopSize = 10;

    @Comment("W jakim slocie ma byc topka wykopanego zlota")
    public int goldMinedTopSlot = 23;

    @Comment("Nazwa placeholdera wykopanego zlota np goldMinedTop(tutaj numer w topce)")
    public String goldMinedTopPlaceHolder = "goldMinedTop";

    @Comment("Nazwa placeholdera wykopanego zlota np goldMinedTopAmount(tutaj numer w topce)")
    public String goldMinedTopAmountPlaceHolder = "goldMinedTopAmount";

    @Comment("Nazwa placeholdera wykopanego zlota gdy nie ma tam gracza")
    public String goldMinedTopNonePlaceHolder = "Brak";

    @Comment({"Jak ma wygladac item, ktory bedzie reprezentowal osoby w topce wykopanego zlota?"})
    public ItemStack goldBrokenTopItem = new ItemBuilder(XMaterial.GOLD_ORE.parseItem())
            .setName("&6&lTOPKA WYKOPANEGO ZLOTA")
            .setLore(
                    "&8#1 &f{goldMinedTop1} &6({goldMinedTopAmount1} wykopanego zlota)",
                    "&8#2 &f{goldMinedTop2} &6({goldMinedTopAmount2} wykopanego zlota)",
                    "&8#3 &f{goldMinedTop3} &6({goldMinedTopAmount3} wykopanego zlota)",
                    "&8#4 &f{goldMinedTop4} &6({goldMinedTopAmount4} wykopanego zlota)",
                    "&8#5 &f{goldMinedTop5} &6({goldMinedTopAmount5} wykopanego zlota)",
                    "&8#6 &f{goldMinedTop6} &6({goldMinedTopAmount6} wykopanego zlota)",
                    "&8#7 &f{goldMinedTop7} &6({goldMinedTopAmount7} wykopanego zlota)",
                    "&8#8 &f{goldMinedTop8} &6({goldMinedTopAmount8} wykopanego zlota)",
                    "&8#9 &f{goldMinedTop9} &6({goldMinedTopAmount9} wykopanego zlota)",
                    "&8#10 &f{goldMinedTop10} &6({goldMinedTopAmount10} wykopanego zlotaw)")
            .toItemStack();

    //WYKOPANY LAPIS

    @Comment("Wielkosc topki wykopanego lapisu")
    public int lapisMinedTopSize = 10;

    @Comment("W jakim slocie ma byc topka wykopanego lapisu")
    public int lapisMinedTopSlot = 29;

    @Comment("Nazwa placeholdera wykopanego lapisu np lapisMinedTop(tutaj numer w topce)")
    public String lapisMinedTopPlaceHolder = "lapisMinedTop";

    @Comment("Nazwa placeholdera wykopanego lapisu np lapisMinedTopAmount(tutaj numer w topce)")
    public String lapisMinedTopAmountPlaceHolder = "lapisMinedTopAmount";

    @Comment("Nazwa placeholdera wykopanego lapisu gdy nie ma tam gracza")
    public String lapisMinedTopNonePlaceHolder = "Brak";

    @Comment({"Jak ma wygladac item, ktory bedzie reprezentowal osoby w topce wykopanego zlota?"})
    public ItemStack lapisBrokenTopItem = new ItemBuilder(XMaterial.LAPIS_ORE.parseItem())
            .setName("&6&lTOPKA WYKOPANEGO LAPISU")
            .setLore(
                    "&8#1 &f{lapisMinedTop1} &6({lapisMinedTopAmount1} wykopanego lapisu)",
                    "&8#2 &f{lapisMinedTop2} &6({lapisMinedTopAmount2} wykopanego lapisu)",
                    "&8#3 &f{lapisMinedTop3} &6({lapisMinedTopAmount3} wykopanego lapisu)",
                    "&8#4 &f{lapisMinedTop4} &6({lapisMinedTopAmount4} wykopanego lapisu)",
                    "&8#5 &f{lapisMinedTop5} &6({lapisMinedTopAmount5} wykopanego lapisu)",
                    "&8#6 &f{lapisMinedTop6} &6({lapisMinedTopAmount6} wykopanego lapisu)",
                    "&8#7 &f{lapisMinedTop7} &6({lapisMinedTopAmount7} wykopanego lapisu)",
                    "&8#8 &f{lapisMinedTop8} &6({lapisMinedTopAmount8} wykopanego lapisu)",
                    "&8#9 &f{lapisMinedTop9} &6({lapisMinedTopAmount9} wykopanego lapisu)",
                    "&8#10 &f{lapisMinedTop10} &6({lapisMinedTopAmount10} wykopanego lapisu)")
            .toItemStack();

    //SMIERCI

    @Comment("Wielkosc topki smierci")
    public int deathsTopSize = 10;

    @Comment("W jakim slocie ma byc topka smierci")
    public int deathsTopSlot = 30;

    @Comment("Nazwa placeholdera smierci np deathsTop(tutaj numer w topce)")
    public String deathsTopPlaceHolder = "deathsTop";

    @Comment("Nazwa placeholdera smierci np deathsTopAmount(tutaj numer w topce)")
    public String deathsTopAmountPlaceHolder = "deathsTopAmount";

    @Comment("Nazwa placeholdera smierci gdy nie ma tam gracza")
    public String deathsTopNonePlaceHolder = "Brak";

    @Comment({"Jak ma wygladac item, ktory bedzie reprezentowal osoby w topce smierci?"})
    public ItemStack deathsTopItem = new ItemBuilder(XMaterial.PLAYER_HEAD.parseItem())
            .setName("&6&lTOPKA SMIERCI")
            .setLore(
                    "&8#1 &f{deathsTop1} &6({deathsTopAmount1} smierci)",
                    "&8#2 &f{deathsTop2} &6({deathsTopAmount2} smierci)",
                    "&8#3 &f{deathsTop3} &6({deathsTopAmount3} smierci)",
                    "&8#4 &f{deathsTop4} &6({deathsTopAmount4} smierci)",
                    "&8#5 &f{deathsTop5} &6({deathsTopAmount5} smierci)",
                    "&8#6 &f{deathsTop6} &6({deathsTopAmount6} smierci)",
                    "&8#7 &f{deathsTop7} &6({deathsTopAmount7} smierci)",
                    "&8#8 &f{deathsTop8} &6({deathsTopAmount8} smierci)",
                    "&8#9 &f{deathsTop9} &6({deathsTopAmount9} smierci)",
                    "&8#10 &f{deathsTop10} &6({deathsTopAmount10} smierci)")
            .toItemStack();


    //ZJEDZONE KOXY


    @Comment("Wielkosc topki zjedzonych koxow")
    public int kgoldEatenTopSize = 10;

    @Comment("W jakim slocie ma byc topka zjedzonych koxow")
    public int kgoldEatenTopSlot = 31;

    @Comment("Nazwa placeholdera zjedzonych koxow np kgoldEatenTop(tutaj numer w topce)")
    public String kgoldEatenTopPlaceHolder = "kgoldEatenTop";

    @Comment("Nazwa placeholdera zjedzonych koxow np kgoldTopAmount(tutaj numer w topce)")
    public String kgoldEatenTopAmountPlaceHolder = "kgoldEatenTopAmount";

    @Comment("Nazwa placeholdera zjedzonych koxow gdy nie ma tam gracza")
    public String kgoldEatenTopNonePlaceHolder = "Brak";

    @Comment({"Jak ma wygladac item, ktory bedzie reprezentowal osoby w topce zjedzonych koxow?"})
    public ItemStack kgoldEatenTopItem = new ItemBuilder(XMaterial.ENCHANTED_GOLDEN_APPLE.parseItem())
            .setName("&6&lTOPKA ZJEDZONYCH KOXOW")
            .setLore(
                    "&8#1 &f{kgoldEatenTop1} &6({kgoldEatenTopAmount1} zjedzonych koxow)",
                    "&8#2 &f{kgoldEatenTop2} &6({kgoldEatenTopAmount2} zjedzonych koxow)",
                    "&8#3 &f{kgoldEatenTop3} &6({kgoldEatenTopAmount3} zjedzonych koxow)",
                    "&8#4 &f{kgoldEatenTop4} &6({kgoldEatenTopAmount4} zjedzonych koxow)",
                    "&8#5 &f{kgoldEatenTop5} &6({kgoldEatenTopAmount5} zjedzonych koxow)",
                    "&8#6 &f{kgoldEatenTop6} &6({kgoldEatenTopAmount6} zjedzonych koxow)",
                    "&8#7 &f{kgoldEatenTop7} &6({kgoldEatenTopAmount7} zjedzonych koxow)",
                    "&8#8 &f{kgoldEatenTop8} &6({kgoldEatenTopAmount8} zjedzonych koxow)",
                    "&8#9 &f{kgoldEatenTop9} &6({kgoldEatenTopAmount9} zjedzonych koxow)",
                    "&8#10 &f{kgoldEatenTop10} &6({kgoldEatenTopAmount10} zjedzonych koxow)")
            .toItemStack();

    //ZJEDZONE REFY

    @Comment("Wielkosc topki zjedzonych refow")
    public int goldEatenTopSize = 10;

    @Comment("W jakim slocie ma byc topka zjedzonych refow")
    public int goldEatenTopSlot = 32;

    @Comment("Nazwa placeholdera zjedzonych refow np goldEatenTop(tutaj numer w topce)")
    public String goldEatenTopPlaceHolder = "goldEatenTop";

    @Comment("Nazwa placeholdera zjedzonych refow np goldEatenTopAmount(tutaj numer w topce)")
    public String goldEatenTopAmountPlaceHolder = "goldEatenTopAmount";

    @Comment("Nazwa placeholdera zjedzonych refow gdy nie ma tam gracza")
    public String goldEatenTopNonePlaceHolder = "Brak";

    @Comment({"Jak ma wygladac item, ktory bedzie reprezentowal osoby w topce zjedzonych refow?"})
    public ItemStack goldEatenTopItem = new ItemBuilder(XMaterial.GOLDEN_APPLE.parseItem())
            .setName("&6&lTOPKA ZJEDZONYCH REFOW")
            .setLore(
                    "&8#1 &f{goldEatenTop1} &6({goldEatenTopAmount1} zjedzonych refow)",
                    "&8#2 &f{goldEatenTop2} &6({goldEatenTopAmount2} zjedzonych refow)",
                    "&8#3 &f{goldEatenTop3} &6({goldEatenTopAmount3} zjedzonych refow)",
                    "&8#4 &f{goldEatenTop4} &6({goldEatenTopAmount4} zjedzonych refow)",
                    "&8#5 &f{goldEatenTop5} &6({goldEatenTopAmount5} zjedzonych refow)",
                    "&8#6 &f{goldEatenTop6} &6({goldEatenTopAmount6} zjedzonych refow)",
                    "&8#7 &f{goldEatenTop7} &6({goldEatenTopAmount7} zjedzonych refow)",
                    "&8#8 &f{goldEatenTop8} &6({goldEatenTopAmount8} zjedzonych refow)",
                    "&8#9 &f{goldEatenTop9} &6({goldEatenTopAmount9} zjedzonych refow)",
                    "&8#10 &f{goldEatenTop10} &6({goldEatenTopAmount10} zjedzonych refow)")
            .toItemStack();

    //ZABOJSTWA

    @Comment("Wielkosc topki zabojstw")
    public int killsTopSize = 10;

    @Comment("W jakim slocie ma byc topka zabojstw")
    public int killsTopSlot = 33;

    @Comment("Nazwa placeholdera zabojstw np killsTop(tutaj numer w topce)")
    public String killsTopPlaceHolder = "killsTop";

    @Comment("Nazwa placeholdera zabojstw np killsTopAmount(tutaj numer w topce)")
    public String killsTopAmountPlaceHolder = "killsTopAmount";

    @Comment("Nazwa placeholdera zzabojstw gdy nie ma tam gracza")
    public String killsTopNonePlaceHolder = "Brak";

    @Comment({"Jak ma wygladac item, ktory bedzie reprezentowal osoby w topce zabojstw?"})
    public ItemStack killsTopItem = new ItemBuilder(XMaterial.DIAMOND_SWORD.parseItem())
            .setName("&6&lTOPKA ZABOJSTW")
            .setLore(
                    "&8#1 &f{killsTop1} &6({killsTopAmount1} zabojstw)",
                    "&8#2 &f{killsTop2} &6({killsTopAmount2} zabojstw)",
                    "&8#3 &f{killsTop3} &6({killsTopAmount3} zabojstw)",
                    "&8#4 &f{killsTop4} &6({killsTopAmount4} zabojstw)",
                    "&8#5 &f{killsTop5} &6({killsTopAmount5} zabojstw)",
                    "&8#6 &f{killsTop6} &6({killsTopAmount6} zabojstw)",
                    "&8#7 &f{killsTop7} &6({killsTopAmount7} zabojstw)",
                    "&8#8 &f{killsTop8} &6({killsTopAmount8} zabojstw)",
                    "&8#9 &f{killsTop9} &6({killsTopAmount9} zabojstw)",
                    "&8#10 &f{killsTop10} &6({killsTopAmount10} zabojstw)")
            .toItemStack();

    //PELNE STATYSTYKI


    @Comment("Nazwa placeholdera wykopanych blokow np blocksMined w statystykach ogolnych")
    public String blocksMinedPlaceHolder = "blocksMined";

    @Comment("Nazwa placeholdera wykopanych diamentow np diamondsMined w statystykach ogolnych")
    public String diamondsMinedPlaceHolder = "diamondsMined";

    @Comment("Nazwa placeholdera wykopanych emeraldow np emeraldsMined w statystykach ogolnych")
    public String emeraldsMinedPlaceHolder = "emeraldsMined";

    @Comment("Nazwa placeholdera wykopanego zlota np goldMined w statystykach ogolnych")
    public String goldMinedPlaceHolder = "goldMined";

    @Comment("Nazwa placeholdera wykopanego zelaza np ironMined w statystykach ogolnych")
    public String ironMinedPlaceHolder = "ironMined";

    @Comment("Nazwa placeholdera wykopanego lapisu np lapisMined w statystykach ogolnych")
    public String lapisMinedPlaceHolder = "lapisMined";

    @Comment("Nazwa placeholdera smierci np deaths w statystykach ogolnych")
    public String deathsPlaceHolder = "deaths";

    @Comment("Nazwa placeholdera zjedzonych koxow np kgoldEaten w statystykach ogolnych")
    public String kgoldEatenPlaceHolder = "kgoldEaten";

    @Comment("Nazwa placeholdera zjedzonych refow np goldEaten w statystykach ogolnych")
    public String goldEatenPlaceHolder = "goldEaten";

    @Comment("Nazwa placeholdera zabojstw np kills w statystykach ogolnych")
    public String killsPlaceHolder = "kills";

    @Comment("W jakim slocie maja byc statystyki ogolne")
    public int statsSlot = 4;

    @Comment({"Jak ma wygladac item, ktory bedzie reprezentowal statystyki ogolne gracza?"})
    public ItemStack statsItem = new ItemBuilder(XMaterial.PAPER.parseItem())
            .setName("&6&lTwoje statystyki")
            .setLore(
                    "&8Wykopane bloki: &6{blocksMined}",
                    "&8Wykopane diamenty: &6{diamondsMined}",
                    "&8Wykopane emeraldy: &6{emeraldsMined}",
                    "&8Wykopane zloto: &6{goldMined}",
                    "&8Wykopane zelazo: &6{ironMined}",
                    "&8Wykopany lapis: &6{lapisMined}",
                    "&8Smierci: &6{deaths}",
                    "&8Zjedzone koxy: &6{kgoldEaten}",
                    "&8Zjedzone refy: &6{goldEaten}",
                    "&8Kille: &6{kills}")
            .toItemStack();

    @Comment("Uzupelnij ponizsze menu danymi.")
    public StorageConfig storageConfig = new StorageConfig("dreamtops");
}
