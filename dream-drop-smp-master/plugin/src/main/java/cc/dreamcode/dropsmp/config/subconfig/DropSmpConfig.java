package cc.dreamcode.dropsmp.config.subconfig;

import cc.dreamcode.dropsmp.builder.ItemBuilder;
import cc.dreamcode.dropsmp.features.emblem.Emblem;
import cc.dreamcode.dropsmp.features.emblem.EmblemType;
import cc.dreamcode.dropsmp.features.items.Item;
import com.google.common.collect.ImmutableList;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class DropSmpConfig extends OkaeriConfig {

    @Comment("Komenda ktora ma sie wykonywac jesli gracz straci wszystkie statystyki")
    public String command = "ban {player} Gameover";

    @Comment("Czy po zabiciu gracza zabójca ma dostawać jakiś % wszystkich statystyk")
    public boolean giveStatssAfterKill = true;

    @Comment("Wartosc ktora ma sie dodawac do statystyk po zabiciu")
    public double numberOfStatsAddAfterKill = 10.0;

    @Comment("Maksymalna wartosc sily")
    public double maxNumerOfStrengthStats = 250.0;

    @Comment("Maksymalna wartosc predkosci (maksylamna wartosc to 500)")
    public double maxNumerOfSpeedStats = 500;

    @Comment("Maksymalna wartosc ochrony")
    public double maxNumerOfResistanceStats = 250.0;

    @Comment({"Szansa na trafienie case'a po zabiciu gracza",
    "Jesli wartosc wynosi 0 case'a nie bedzie dalo sie trafic"})
    public double chanceToDropChest = 40.0;

    @Comment("Case ktory wypada po zabiciu gracza")
    public ItemStack cases = new ItemBuilder(Material.CHEST, 1)
            .setName("&8> &5Case &8<")
            .setLore("&8> &7Postaw na ziemi aby otworzyc")
            .toItemStack();

    @Comment("Itemy ktore dropią z case'a")
    public List<Item> items = new ImmutableList.Builder<Item>()
            .add(new Item(new ItemBuilder(Material.DIAMOND_SWORD)
                    .setName("&8> &7Emblemant Sily &8<")
                    .setLore("&8> &7Emblemant dodaje 10% do sily")
                    .toItemStack(), 10.0))
            .add(new Item(new ItemBuilder(Material.FEATHER)
                    .setName("&8> &7Emblemant Szybkosci &8<")
                    .setLore("&8> &7Emblemant dodaje 10% do sily")
                    .toItemStack(), 10.0))
            .add(new Item(new ItemBuilder(Material.CHAINMAIL_CHESTPLATE)
                    .setName("&8> &7Emblemant Ochrony &8<")
                    .setLore("&8> &7Emblemant dodaje 10% do sily")
                    .toItemStack(), 10))
            .add( new Item(new ItemBuilder(Material.NETHER_STAR)
                    .setName("&8> &7Emblemant Odrodzenia &8<")
                    .setLore("&8> &7Emblemant restartuje wszystkie statystyki")
                    .toItemStack(), 10))
            .add(new Item(new ItemBuilder(Material.DIAMOND_PICKAXE)
                    .setName("&8> &3Legendarny kilof&8<")
                    .addEnchant(Enchantment.DIG_SPEED, 6)
                    .toItemStack(), 10))
            .build();

    @Comment({"Wszystkie emblemanty",
    "Rodzaje emblematów: RESISTANCE, STRENGTH, SPEED"})
    public List<Emblem> emblemList = new ImmutableList.Builder<Emblem>()
            .add(new Emblem(new ItemBuilder(Material.DIAMOND_SWORD)
                    .setName("&8> &7Emblemant Sily &8<")
                    .setLore("&8> &7Emblemant dodaje 10% do sily")
                    .toItemStack(), 10.0, EmblemType.STRENGTH))
            .add(new Emblem(new ItemBuilder(Material.FEATHER)
                    .setName("&8> &7Emblemant Szybkosci &8<")
                    .setLore("&8> &7Emblemant dodaje 10% do sily")
                    .toItemStack(), 10.0, EmblemType.SPEED))
            .add(new Emblem(new ItemBuilder(Material.CHAINMAIL_CHESTPLATE)
                    .setName("&8> &7Emblemant Ochrony &8<")
                    .setLore("&8> &7Emblemant dodaje 10% do sily")
                    .toItemStack(), 10, EmblemType.RESISTANCE))
            .add(new Emblem(new ItemBuilder(Material.NETHER_STAR)
                    .setName("&8> &7Emblemant Odrodzenia &8<")
                    .setLore("&8> &7Emblemant restartuje wszystkie statystyki")
                    .toItemStack(), 100, EmblemType.RESTART))
            .build();

}
