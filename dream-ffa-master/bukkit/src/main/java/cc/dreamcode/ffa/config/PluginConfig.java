package cc.dreamcode.ffa.config;

import cc.dreamcode.ffa.deposit.DepositItem;
import cc.dreamcode.menu.bukkit.BukkitMenuBuilder;
import cc.dreamcode.notice.minecraft.MinecraftNoticeType;
import cc.dreamcode.notice.minecraft.bukkit.BukkitNotice;
import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import cc.dreamcode.platform.persistence.StorageConfig;
import cc.dreamcode.utilities.builder.ListBuilder;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.requireNonNull;

/**
 * PluginConfig.java
 * Purpose: The PluginConfig is a class utilised by other classes to access plugin-related to things.
 * * @author trueman96
 * @version 1.0-beta.1
 * @since 2023-11-24
 */
@Configuration(child = "config.yml")
@Header("## Dream-FFA (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public final class PluginConfig extends OkaeriConfig {

    @Comment("Debug pokazuje dodatkowe informacje do konsoli. Lepiej wyłączyć. :P")
    public boolean debug = true;

    @Comment("Uzupełnij poniższe menu danymi.")
    public StorageConfig storageConfig = new StorageConfig("dreamffa");

    @Comment({
            "Itemki które gracz automatycznie ubiera, po wejściu na serwer",
            "Dostepne type: (HAND, OFF_HAND, FEET, LEGS, CHEST, HEAD)"
    })
    public Map<EquipmentSlot, ItemStack> equipmentAfterJoin = new MapBuilder<EquipmentSlot, ItemStack>()
            .put(EquipmentSlot.CHEST, ItemBuilder.of(requireNonNull(XMaterial.DIAMOND_CHESTPLATE.parseMaterial())).toItemStack())
            .build();

    @Comment({
            "Identyfikator placeholdera użytkownika",
            "Np teraz będzie tak: (" +
                    "%ffa-user_name%, " +
                    "%ffa-user_points%, " +
                    "%ffa-user_kill-streak%, " +
                    "%ffa-user_max-kill-streak%" +
                    "%ffa-user_kills%, " +
                    "%ffa-user_deaths%, " +
                    "%ffa-user_assists%, " +
                    ")"
    })
    public String userPlaceholderIdentifier = "ffa-user";

    @Comment({
            "Identyfikator placeholderu topek użytkowników",
            "Np teraz będzie tak: (%ffa-tops_points%, %ffa-tops_kill-streak%, %ffa-tops_max-kill-streak%)"
    })
    public String userRankingPlaceholderIdentifier = "ffa-tops";

    @Comment({
            "Itemki które gracz dostaje do ekwipunku, po wejściu na serwer",
            "Dostepne type: (HAND, OFF_HAND, FEET, LEGS, CHEST, HEAD)"
    })
    public List<ItemStack> itemsAfterJoin = new ListBuilder<ItemStack>()
            .add(ItemBuilder.of(requireNonNull(XMaterial.STONE_SWORD.parseMaterial())).toItemStack())
            .build();

    @Comment("Lokalizacja spawna")
    public Location spawnLocation = new Location(Bukkit.getWorlds().get(0), 0, 100, 0);

    @Comment("Początkowa wartość punktów")
    public int initialValueOfPoints = 1000;

    @Comment("Czy itemy po zabiciu gracza mają być usuwane z ziemi, i dodawane do ekwipunku zabójcy?")
    public boolean addItemsToKillerInventory = false;

    @Comment("Ilość punktów do odjęcia po popełnieniu samobójstwa przez gracza.")
    public int valueOfPointsToRemoveAfterSuicide = 5;

    @Comment("Długość trwania walki po zabiciu gracza.")
    public Duration combatTimeAfterKill = Duration.ofSeconds(15);

    @Comment("Długość trwania walki po zaatakowaniu gracza.")
    public Duration combatTimeAfterDamage = Duration.ofSeconds(21);

    @Comment("Czy title po zabiciu gracza ma być włączony?")
    public boolean killTitle = true;

    @Comment("Czy title po asyscie gracza ma być włączony?")
    public boolean assistTitle = true;

    @Comment("Długość trwania asysty po zaatakowaniu gracza")
    public long assistTimeAfterDamage = TimeUnit.SECONDS.toMillis(61);

    @Comment("Zablokowane komendy podczas antylogouta")
    public List<String> disallowedCommands = new ListBuilder<String>()
            .addAll(Arrays.asList(
                    "wb",
                    "workbench",
                    "kit",
                    "home",
                    "sethome",
                    "spawn",
                    "tpa",
                    "tpaccept",
                    "warp",
                    "ec",
                    "heal",
                    "feed",
                    "repair"
            ))
            .build();

    @Comment("Itemki których nadmiar będzie zabierany")
    public List<DepositItem> depositItems = Arrays.asList(
            new DepositItem(
                    BukkitNotice.of(MinecraftNoticeType.CHAT, "&7Zabrano nadmiar &8(&7{amount}&8) &7refili z ekwipunku!"),
                    12,
                    new ItemStack(Material.GOLDEN_APPLE)
            )
    );

    @Comment("Nie usuwaj flag (HIDE_DESTROYS, HIDE_ENCHANTS) bo bez nich gui nie będzie funkcjonować!")
    public BukkitMenuBuilder saveInventoryMenu = new BukkitMenuBuilder("&6&lZapisywanie ekwipunku", 3, new MapBuilder<Integer, ItemStack>()
            .put(10, ItemBuilder.of(XMaterial.BOOK.parseItem())
                    .setName("&cResetuj")
                    .addFlags(ItemFlag.HIDE_DESTROYS)
                    .toItemStack())
            .put(16, ItemBuilder.of(XMaterial.BOOK.parseItem())
                    .addFlags(ItemFlag.HIDE_ENCHANTS)
                    .setName("&aZapisz")
                    .toItemStack())
            .put(0, XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
            .put(1, XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
            .put(2, XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
            .put(3, XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
            .put(4, XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
            .put(5, XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
            .put(6, XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
            .put(7, XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
            .put(8, XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
            .put(9, XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
            .put(11, XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
            .put(12, XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
            .put(13, XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
            .put(14, XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
            .put(15, XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
            .put(17, XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
            .put(18, XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
            .put(19, XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
            .put(20, XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
            .put(21, XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
            .put(22, XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
            .put(23, XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
            .put(24, XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
            .put(25, XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
            .put(26, XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
            .build());

    public BukkitNotice spawnLocationUpdated = BukkitNotice.of(MinecraftNoticeType.CHAT, "&aZaktualizowano lokalizacje spawna!");
    public String spawnLocationPermission = "dreamffa.spawnlocation";

    @Comment("Czy gracze mają być respawnowani automatycznie po śmierci?")
    public boolean autoRespawn = true;
    @Comment("Czy gracz który zginął ma mieć wyświetlany title z informacjami kto zabił?")
    public boolean killedTitle = true;
}