package cc.dreamcode.leaderboard.config.leaderboard;

import cc.dreamcode.leaderboard.LeaderboardType;
import cc.dreamcode.leaderboard.builder.ItemBuilder;
import cc.dreamcode.leaderboard.builder.MapBuilder;
import cc.dreamcode.menu.serdes.bukkit.BukkitMenuBuilder;
import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.ImmutableMap;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class LeaderboardConfig extends OkaeriConfig {

    @Comment("Jaki ustawic nick, gdy na danym miejscu nie znajdzie gracza?")
    public String unknownUserName = "Brak";

    @Comment({"Ustaw menu, w ktorym beda wyswietlanie itemy w formie topki.",
            "Wystarczy, ze ustawisz placeholder np. {block-break_1_nick} i automatycznie plugin podmieni wartosc.",
            "Dostepne suffixy: (nick, count)",
            "Dostepne topki: (block-place, block-break, stone-place, stone-break, obsidian-place, obsidian-break, kill, death, refile-eat, kox-eat, pearl-throw, play-time, walk-distance)",
            "Uwaga: Topka i statystki gracza moga sie roznic, poniewaz topka jest aktualizowana asynchronicznie i statystyki moga sie nie zgadzac. (4x na minute sie aktualizuje)"})
    public BukkitMenuBuilder menuBuilder = new BukkitMenuBuilder("&8&lTopka", 4, true, new MapBuilder<Integer, ItemStack>()
            .put(10, new ItemBuilder(XMaterial.GRASS_BLOCK.parseItem())
                    .setName("&7&lPostawione &9&lbloki")
                    .setLore("&8&l&m-[-------- -+- --------]-",
                            "&f1.&7. &e{block-place_1_nick} &8(&c{block-place_1_count} &fblokow&8)",
                            "&f2.&7. &e{block-place_2_nick} &8(&c{block-place_2_count} &fblokow&8)",
                            "&f3.&7. &e{block-place_3_nick} &8(&c{block-place_3_count} &fblokow&8)",
                            "&f4.&7. &e{block-place_4_nick} &8(&c{block-place_4_count} &fblokow&8)",
                            "&f5.&7. &e{block-place_5_nick} &8(&c{block-place_5_count} &fblokow&8)",
                            "&f6.&7. &e{block-place_6_nick} &8(&c{block-place_6_count} &fblokow&8)",
                            "&f7.&7. &e{block-place_7_nick} &8(&c{block-place_7_count} &fblokow&8)",
                            "&f8.&7. &e{block-place_8_nick} &8(&c{block-place_8_count} &fblokow&8)",
                            "&f9.&7. &e{block-place_9_nick} &8(&c{block-place_9_count} &fblokow&8)",
                            "&f10.&7. &e{block-place_10_nick} &8(&c{block-place_10_count} &fblokow&8)",
                            "&8&l&m-[-------- -+- --------]-")
                    .toItemStack())
            .put(11, new ItemBuilder(XMaterial.STONE_PICKAXE.parseItem())
                    .setName("&7&lZniszczone &9&lbloki")
                    .setLore("&8&l&m-[-------- -+- --------]-",
                            "&f1.&7. &e{block-break_1_nick} &8(&c{block-break_1_count} &fblokow&8)",
                            "&f2.&7. &e{block-break_2_nick} &8(&c{block-break_2_count} &fblokow&8)",
                            "&f3.&7. &e{block-break_3_nick} &8(&c{block-break_3_count} &fblokow&8)",
                            "&f4.&7. &e{block-break_4_nick} &8(&c{block-break_4_count} &fblokow&8)",
                            "&f5.&7. &e{block-break_5_nick} &8(&c{block-break_5_count} &fblokow&8)",
                            "&f6.&7. &e{block-break_6_nick} &8(&c{block-break_6_count} &fblokow&8)",
                            "&f7.&7. &e{block-break_7_nick} &8(&c{block-break_7_count} &fblokow&8)",
                            "&f8.&7. &e{block-break_8_nick} &8(&c{block-break_8_count} &fblokow&8)",
                            "&f9.&7. &e{block-break_9_nick} &8(&c{block-break_9_count} &fblokow&8)",
                            "&f10.&7. &e{block-break_10_nick} &8(&c{block-break_10_count} &fblokow&8)",
                            "&8&l&m-[-------- -+- --------]-")
                    .toItemStack())
            .put(12, new ItemBuilder(XMaterial.STONE.parseItem())
                    .setName("&7&lPostawiony &9&lstone")
                    .setLore("&8&l&m-[-------- -+- --------]-",
                            "&f1.&7. &e{stone-place_1_nick} &8(&c{stone-place_1_count} &fblokow&8)",
                            "&f2.&7. &e{stone-place_2_nick} &8(&c{stone-place_2_count} &fblokow&8)",
                            "&f3.&7. &e{stone-place_3_nick} &8(&c{stone-place_3_count} &fblokow&8)",
                            "&f4.&7. &e{stone-place_4_nick} &8(&c{stone-place_4_count} &fblokow&8)",
                            "&f5.&7. &e{stone-place_5_nick} &8(&c{stone-place_5_count} &fblokow&8)",
                            "&f6.&7. &e{stone-place_6_nick} &8(&c{stone-place_6_count} &fblokow&8)",
                            "&f7.&7. &e{stone-place_7_nick} &8(&c{stone-place_7_count} &fblokow&8)",
                            "&f8.&7. &e{stone-place_8_nick} &8(&c{stone-place_8_count} &fblokow&8)",
                            "&f9.&7. &e{stone-place_9_nick} &8(&c{stone-place_9_count} &fblokow&8)",
                            "&f10.&7. &e{stone-place_10_nick} &8(&c{stone-place_10_count} &fblokow&8)",
                            "&8&l&m-[-------- -+- --------]-")
                    .toItemStack())
            .put(13, new ItemBuilder(XMaterial.IRON_PICKAXE.parseItem())
                    .setName("&7&lZniszczony &9&lstone")
                    .setLore("&8&l&m-[-------- -+- --------]-",
                            "&f1.&7. &e{stone-break_1_nick} &8(&c{stone-break_1_count} &fblokow&8)",
                            "&f2.&7. &e{stone-break_2_nick} &8(&c{stone-break_2_count} &fblokow&8)",
                            "&f3.&7. &e{stone-break_3_nick} &8(&c{stone-break_3_count} &fblokow&8)",
                            "&f4.&7. &e{stone-break_4_nick} &8(&c{stone-break_4_count} &fblokow&8)",
                            "&f5.&7. &e{stone-break_5_nick} &8(&c{stone-break_5_count} &fblokow&8)",
                            "&f6.&7. &e{stone-break_6_nick} &8(&c{stone-break_6_count} &fblokow&8)",
                            "&f7.&7. &e{stone-break_7_nick} &8(&c{stone-break_7_count} &fblokow&8)",
                            "&f8.&7. &e{stone-break_8_nick} &8(&c{stone-break_8_count} &fblokow&8)",
                            "&f9.&7. &e{stone-break_9_nick} &8(&c{stone-break_9_count} &fblokow&8)",
                            "&f10.&7. &e{stone-break_10_nick} &8(&c{stone-break_10_count} &fblokow&8)",
                            "&8&l&m-[-------- -+- --------]-")
                    .toItemStack())
            .put(14, new ItemBuilder(XMaterial.OBSIDIAN.parseItem())
                    .setName("&7&lPostawiony &9&lobsydian")
                    .setLore("&8&l&m-[-------- -+- --------]-",
                            "&f1.&7. &e{obsidian-place_1_nick} &8(&c{obsidian-place_1_count} &fblokow&8)",
                            "&f2.&7. &e{obsidian-place_2_nick} &8(&c{obsidian-place_2_count} &fblokow&8)",
                            "&f3.&7. &e{obsidian-place_3_nick} &8(&c{obsidian-place_3_count} &fblokow&8)",
                            "&f4.&7. &e{obsidian-place_4_nick} &8(&c{obsidian-place_4_count} &fblokow&8)",
                            "&f5.&7. &e{obsidian-place_5_nick} &8(&c{obsidian-place_5_count} &fblokow&8)",
                            "&f6.&7. &e{obsidian-place_6_nick} &8(&c{obsidian-place_6_count} &fblokow&8)",
                            "&f7.&7. &e{obsidian-place_7_nick} &8(&c{obsidian-place_7_count} &fblokow&8)",
                            "&f8.&7. &e{obsidian-place_8_nick} &8(&c{obsidian-place_8_count} &fblokow&8)",
                            "&f9.&7. &e{obsidian-place_9_nick} &8(&c{obsidian-place_9_count} &fblokow&8)",
                            "&f10.&7. &e{obsidian-place_10_nick} &8(&c{obsidian-place_10_count} &fblokow&8)",
                            "&8&l&m-[-------- -+- --------]-")
                    .toItemStack())
            .put(15, new ItemBuilder(XMaterial.DIAMOND_PICKAXE.parseItem())
                    .setName("&7&lZniszczony &9&lobsydian")
                    .setLore("&8&l&m-[-------- -+- --------]-",
                            "&f1.&7. &e{obsidian-break_1_nick} &8(&c{obsidian-break_1_count} &fblokow&8)",
                            "&f2.&7. &e{obsidian-break_2_nick} &8(&c{obsidian-break_2_count} &fblokow&8)",
                            "&f3.&7. &e{obsidian-break_3_nick} &8(&c{obsidian-break_3_count} &fblokow&8)",
                            "&f4.&7. &e{obsidian-break_4_nick} &8(&c{obsidian-break_4_count} &fblokow&8)",
                            "&f5.&7. &e{obsidian-break_5_nick} &8(&c{obsidian-break_5_count} &fblokow&8)",
                            "&f6.&7. &e{obsidian-break_6_nick} &8(&c{obsidian-break_6_count} &fblokow&8)",
                            "&f7.&7. &e{obsidian-break_7_nick} &8(&c{obsidian-break_7_count} &fblokow&8)",
                            "&f8.&7. &e{obsidian-break_8_nick} &8(&c{obsidian-break_8_count} &fblokow&8)",
                            "&f9.&7. &e{obsidian-break_9_nick} &8(&c{obsidian-break_9_count} &fblokow&8)",
                            "&f10.&7. &e{obsidian-break_10_nick} &8(&c{obsidian-break_10_count} &fblokow&8)",
                            "&8&l&m-[-------- -+- --------]-")
                    .toItemStack())
            .put(16, new ItemBuilder(XMaterial.DIAMOND_SWORD.parseItem())
                    .setName("&c&lZabojstwa")
                    .setLore("&8&l&m-[-------- -+- --------]-",
                            "&f1.&7. &e{kill_1_nick} &8(&c{kill_1_count} &fzabojstw&8)",
                            "&f2.&7. &e{kill_2_nick} &8(&c{kill_2_count} &fzabojstw&8)",
                            "&f3.&7. &e{kill_3_nick} &8(&c{kill_3_count} &fzabojstw&8)",
                            "&f4.&7. &e{kill_4_nick} &8(&c{kill_4_count} &fzabojstw&8)",
                            "&f5.&7. &e{kill_5_nick} &8(&c{kill_5_count} &fzabojstw&8)",
                            "&f6.&7. &e{kill_6_nick} &8(&c{kill_6_count} &fzabojstw&8)",
                            "&f7.&7. &e{kill_7_nick} &8(&c{kill_7_count} &fzabojstw&8)",
                            "&f8.&7. &e{kill_8_nick} &8(&c{kill_8_count} &fzabojstw&8)",
                            "&f9.&7. &e{kill_9_nick} &8(&c{kill_9_count} &fzabojstw&8)",
                            "&f10.&7. &e{kill_10_nick} &8(&c{kill_10_count} &fzabojstw&8)",
                            "&8&l&m-[-------- -+- --------]-")
                    .toItemStack())
            .put(19, new ItemBuilder(XMaterial.SKELETON_SKULL.parseItem())
                    .setName("&4&lSmierci")
                    .setLore("&8&l&m-[-------- -+- --------]-",
                            "&f1.&7. &e{death_1_nick} &8(&c{death_1_count} &fsmierci&8)",
                            "&f2.&7. &e{death_2_nick} &8(&c{death_2_count} &fsmierci&8)",
                            "&f3.&7. &e{death_3_nick} &8(&c{death_3_count} &fsmierci&8)",
                            "&f4.&7. &e{death_4_nick} &8(&c{death_4_count} &fsmierci&8)",
                            "&f5.&7. &e{death_5_nick} &8(&c{death_5_count} &fsmierci&8)",
                            "&f6.&7. &e{death_6_nick} &8(&c{death_6_count} &fsmierci&8)",
                            "&f7.&7. &e{death_7_nick} &8(&c{death_7_count} &fsmierci&8)",
                            "&f8.&7. &e{death_8_nick} &8(&c{death_8_count} &fsmierci&8)",
                            "&f9.&7. &e{death_9_nick} &8(&c{death_9_count} &fsmierci&8)",
                            "&f10.&7. &e{death_10_nick} &8(&c{death_10_count} &fsmierci&8)",
                            "&8&l&m-[-------- -+- --------]-")
                    .toItemStack())
            .put(20, new ItemBuilder(XMaterial.GOLDEN_APPLE.parseItem())
                    .setName("&7&lZjedzone &e&lrefy")
                    .setLore("&8&l&m-[-------- -+- --------]-",
                            "&f1.&7. &e{refile-eat_1_nick} &8(&c{refile-eat_1_count} &8- &fZjedzonych refow&8)",
                            "&f2.&7. &e{refile-eat_2_nick} &8(&c{refile-eat_2_count} &8- &fZjedzonych refow&8)",
                            "&f3.&7. &e{refile-eat_3_nick} &8(&c{refile-eat_3_count} &8- &fZjedzonych refow&8)",
                            "&f4.&7. &e{refile-eat_4_nick} &8(&c{refile-eat_4_count} &8- &fZjedzonych refow&8)",
                            "&f5.&7. &e{refile-eat_5_nick} &8(&c{refile-eat_5_count} &8- &fZjedzonych refow&8)",
                            "&f6.&7. &e{refile-eat_6_nick} &8(&c{refile-eat_6_count} &8- &fZjedzonych refow&8)",
                            "&f7.&7. &e{refile-eat_7_nick} &8(&c{refile-eat_7_count} &8- &fZjedzonych refow&8)",
                            "&f8.&7. &e{refile-eat_8_nick} &8(&c{refile-eat_8_count} &8- &fZjedzonych refow&8)",
                            "&f9.&7. &e{refile-eat_9_nick} &8(&c{refile-eat_9_count} &8- &fZjedzonych refow&8)",
                            "&f10.&7. &e{refile-eat_10_nick} &8(&c{refile-eat_10_count} &8- &fZjedzonych refow&8)",
                            "&8&l&m-[-------- -+- --------]-")
                    .toItemStack())
            .put(21, new ItemBuilder(XMaterial.ENCHANTED_GOLDEN_APPLE.parseItem())
                    .setName("&7&lZjedzone &6&lkoxy")
                    .setLore("&8&l&m-[-------- -+- --------]-",
                            "&f1.&7. &e{kox-eat_1_nick} &8(&c{kox-eat_1_count} &8- &fZjedzonych koxow&8)",
                            "&f2.&7. &e{kox-eat_2_nick} &8(&c{kox-eat_2_count} &8- &fZjedzonych koxow&8)",
                            "&f3.&7. &e{kox-eat_3_nick} &8(&c{kox-eat_3_count} &8- &fZjedzonych koxow&8)",
                            "&f4.&7. &e{kox-eat_4_nick} &8(&c{kox-eat_4_count} &8- &fZjedzonych koxow&8)",
                            "&f5.&7. &e{kox-eat_5_nick} &8(&c{kox-eat_5_count} &8- &fZjedzonych koxow&8)",
                            "&f6.&7. &e{kox-eat_6_nick} &8(&c{kox-eat_6_count} &8- &fZjedzonych koxow&8)",
                            "&f7.&7. &e{kox-eat_7_nick} &8(&c{kox-eat_7_count} &8- &fZjedzonych koxow&8)",
                            "&f8.&7. &e{kox-eat_8_nick} &8(&c{kox-eat_8_count} &8- &fZjedzonych koxow&8)",
                            "&f9.&7. &e{kox-eat_9_nick} &8(&c{kox-eat_9_count} &8- &fZjedzonych koxow&8)",
                            "&f10.&7. &e{kox-eat_10_nick} &8(&c{kox-eat_10_count} &8- &fZjedzonych koxow&8)",
                            "&8&l&m-[-------- -+- --------]-")
                    .toItemStack())
            .put(22, new ItemBuilder(XMaterial.ENDER_PEARL.parseItem())
                    .setName("&7&lRzucone &5&lperly")
                    .setLore("&8&l&m-[-------- -+- --------]-",
                            "&f1.&7. &e{pearl-throw_1_nick} &8(&c{pearl-throw_1_count} &8- &fRzuconych perel&8)",
                            "&f2.&7. &e{pearl-throw_2_nick} &8(&c{pearl-throw_2_count} &8- &fRzuconych perel&8)",
                            "&f3.&7. &e{pearl-throw_3_nick} &8(&c{pearl-throw_3_count} &8- &fRzuconych perel&8)",
                            "&f4.&7. &e{pearl-throw_4_nick} &8(&c{pearl-throw_4_count} &8- &fRzuconych perel&8)",
                            "&f5.&7. &e{pearl-throw_5_nick} &8(&c{pearl-throw_5_count} &8- &fRzuconych perel&8)",
                            "&f6.&7. &e{pearl-throw_6_nick} &8(&c{pearl-throw_6_count} &8- &fRzuconych perel&8)",
                            "&f7.&7. &e{pearl-throw_7_nick} &8(&c{pearl-throw_7_count} &8- &fRzuconych perel&8)",
                            "&f8.&7. &e{pearl-throw_8_nick} &8(&c{pearl-throw_8_count} &8- &fRzuconych perel&8)",
                            "&f9.&7. &e{pearl-throw_9_nick} &8(&c{pearl-throw_9_count} &8- &fRzuconych perel&8)",
                            "&f10.&7. &e{pearl-throw_10_nick} &8(&c{pearl-throw_10_count} &8- &fRzuconych perel&8)",
                            "&8&l&m-[-------- -+- --------]-")
                    .toItemStack())
            .put(23, new ItemBuilder(XMaterial.CLOCK.parseItem())
                    .setName("&7&lCzas &6&lgry")
                    .setLore("&8&l&m-[-------- -+- --------]-",
                            "&f1.&7. &e{play-time_1_nick} &8(&c{play-time_1_count} &8- &fPrzegranego czasu&8)",
                            "&f2.&7. &e{play-time_2_nick} &8(&c{play-time_2_count} &8- &fPrzegranego czasu&8)",
                            "&f3.&7. &e{play-time_3_nick} &8(&c{play-time_3_count} &8- &fPrzegranego czasu&8)",
                            "&f4.&7. &e{play-time_4_nick} &8(&c{play-time_4_count} &8- &fPrzegranego czasu&8)",
                            "&f5.&7. &e{play-time_5_nick} &8(&c{play-time_5_count} &8- &fPrzegranego czasu&8)",
                            "&f6.&7. &e{play-time_6_nick} &8(&c{play-time_6_count} &8- &fPrzegranego czasu&8)",
                            "&f7.&7. &e{play-time_7_nick} &8(&c{play-time_7_count} &8- &fPrzegranego czasu&8)",
                            "&f8.&7. &e{play-time_8_nick} &8(&c{play-time_8_count} &8- &fPrzegranego czasu&8)",
                            "&f9.&7. &e{play-time_9_nick} &8(&c{play-time_9_count} &8- &fPrzegranego czasu&8)",
                            "&f10.&7. &e{play-time_10_nick} &8(&c{play-time_10_count} &8- &fPrzegranego czasu&8)",
                            "&8&l&m-[-------- -+- --------]-")
                    .toItemStack())
            .put(24, new ItemBuilder(XMaterial.LEATHER_BOOTS.parseItem())
                    .setName("&7&lPokonany &c&ldystans")
                    .setLore("&8&l&m-[-------- -+- --------]-",
                            "&f1.&7. &e{walk-distance_1_nick} &8(&c{walk-distance_1_count} &8- &fPrzebytego dystansu&8)",
                            "&f2.&7. &e{walk-distance_2_nick} &8(&c{walk-distance_2_count} &8- &fPrzebytego dystansu&8)",
                            "&f3.&7. &e{walk-distance_3_nick} &8(&c{walk-distance_3_count} &8- &fPrzebytego dystansu&8)",
                            "&f4.&7. &e{walk-distance_4_nick} &8(&c{walk-distance_4_count} &8- &fPrzebytego dystansu&8)",
                            "&f5.&7. &e{walk-distance_5_nick} &8(&c{walk-distance_5_count} &8- &fPrzebytego dystansu&8)",
                            "&f6.&7. &e{walk-distance_6_nick} &8(&c{walk-distance_6_count} &8- &fPrzebytego dystansu&8)",
                            "&f7.&7. &e{walk-distance_7_nick} &8(&c{walk-distance_7_count} &8- &fPrzebytego dystansu&8)",
                            "&f8.&7. &e{walk-distance_8_nick} &8(&c{walk-distance_8_count} &8- &fPrzebytego dystansu&8)",
                            "&f9.&7. &e{walk-distance_9_nick} &8(&c{walk-distance_9_count} &8- &fPrzebytego dystansu&8)",
                            "&f10.&7. &e{walk-distance_10_nick} &8(&c{walk-distance_10_count} &8- &fPrzebytego dystansu&8)",
                            "&8&l&m-[-------- -+- --------]-")
                    .toItemStack())
            .put(25, new ItemBuilder(XMaterial.BOOK.parseItem())
                    .setName("&7&lTwoje &9&lstatystyki")
                    .setLore("&8&l&m-[-------- -+- --------]-",
                            "&7&l#{block-place_position} &f- &c{block-place} &8- &fPostawionych blokow",
                            "&7&l#{block-break_position} &f- &c{block-break} &8- &fZniszczonych blokow",
                            "&7&l#{stone-place_position} &f- &c{stone-place} &8- &fPostawionego stone'a",
                            "&7&l#{stone-break_position} &f- &c{stone-break} &8- &fZniszczonego stone'a",
                            "&7&l#{obsidian-place_position} &f- &c{obsidian-place} &8- &fPostawionego obsydianu",
                            "&7&l#{obsidian-break_position} &f- &c{obsidian-break} &8- &fZniszczonego obsydianu",
                            "&7&l#{kill_position} &f- &c{kill} &8- &fZabojstw",
                            "&7&l#{death_position} &f- &c{death} &8- &fZgonow",
                            "&7&l#{refile-eat_position} &f- &c{refile-eat} &8- &fZjedzonych refow",
                            "&7&l#{kox-eat_position} &f- &c{kox-eat} &8- &fZjedzonych koxow",
                            "&7&l#{pearl-throw_position} &f- &c{pearl-throw} &8- &fRzuconych perel",
                            "&7&l#{play-time_position} &f- &c{play-time} &8- &fPrzegranego czasu",
                            "&7&l#{walk-distance_position} &f- &c{walk-distance} &8- &fPrzebytego dystansu",
                            "&8&l&m-[-------- -+- --------]-")
                    .toItemStack())
            .build());

    @Comment("Pod jakim slotem maja znajdowac sie dane topki?")
    public Map<Integer, LeaderboardType> topTypeSlots = new MapBuilder<Integer, LeaderboardType>()
            .put(10, LeaderboardType.BLOCK_PLACE_COUNT)
            .put(11, LeaderboardType.BLOCK_BREAK_COUNT)
            .put(12, LeaderboardType.STONE_PLACE_COUNT)
            .put(13, LeaderboardType.STONE_BREAK_COUNT)
            .put(14, LeaderboardType.OBSIDIAN_PLACE_COUNT)
            .put(15, LeaderboardType.OBSIDIAN_BREAK_COUNT)
            .put(16, LeaderboardType.KILL_COUNT)
            .put(19, LeaderboardType.DEATH_COUNT)
            .put(20, LeaderboardType.REFILE_EAT_COUNT)
            .put(21, LeaderboardType.KOX_EAT_COUNT)
            .put(22, LeaderboardType.PEARL_THROW_COUNT)
            .put(23, LeaderboardType.PLAY_TIME_COUNT)
            .put(24, LeaderboardType.WALK_DISTANCE_COUNT)
            .build();

    @Comment("Jaka nazwe przyjazna ma przyjac kazda topka?")
    public Map<LeaderboardType, String> topDisplayNames = new MapBuilder<LeaderboardType, String>()
            .put(LeaderboardType.BLOCK_PLACE_COUNT, "postawionych blokow")
            .put(LeaderboardType.BLOCK_BREAK_COUNT, "znisczonych blokow")
            .put(LeaderboardType.STONE_PLACE_COUNT, "postawionego stone'a")
            .put(LeaderboardType.STONE_BREAK_COUNT, "zniszczonego stone'a")
            .put(LeaderboardType.OBSIDIAN_PLACE_COUNT, "postawionego obsydianu")
            .put(LeaderboardType.OBSIDIAN_BREAK_COUNT, "zniszczonego obsydianu")
            .put(LeaderboardType.KILL_COUNT, "zabojstw")
            .put(LeaderboardType.DEATH_COUNT, "smierci")
            .put(LeaderboardType.REFILE_EAT_COUNT, "zjedzonych refili")
            .put(LeaderboardType.KOX_EAT_COUNT, "zjedzonych koxow")
            .put(LeaderboardType.PEARL_THROW_COUNT, "rzuconych perel")
            .put(LeaderboardType.PLAY_TIME_COUNT, "czasu gry")
            .put(LeaderboardType.WALK_DISTANCE_COUNT, "przebytego dystansu")
            .build();

    @Comment("Ustaw konfiguracje menu otchlani:")
    public BukkitMenuBuilder leaderboardMenu = new BukkitMenuBuilder(
            "&8&lTopka &c{name} &7Strona: &a&l{page}&7&l",
            6,
            true,
            new ImmutableMap.Builder<Integer, ItemStack>()
                    .put(45, new ItemBuilder(XMaterial.RED_WOOL.parseItem())
                            .setName("&cPowrot do porzedniej strony.")
                            .setLore("&7Kliknij, aby zmienic strone.")
                            .toItemStack())
                    .put(46, new ItemBuilder(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
                            .setName(" ")
                            .toItemStack())
                    .put(47, new ItemBuilder(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
                            .setName(" ")
                            .toItemStack())
                    .put(48, new ItemBuilder(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
                            .setName(" ")
                            .toItemStack())
                    .put(49, new ItemBuilder(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
                            .setName(" ")
                            .toItemStack())
                    .put(50, new ItemBuilder(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
                            .setName(" ")
                            .toItemStack())
                    .put(51, new ItemBuilder(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
                            .setName(" ")
                            .toItemStack())
                    .put(52, new ItemBuilder(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
                            .setName(" ")
                            .toItemStack())
                    .put(53, new ItemBuilder(XMaterial.GREEN_WOOL.parseItem())
                            .setName("&aPrzejdz do nastepnej strony.")
                            .setLore("&7Kliknij, aby zmienic strone.")
                            .toItemStack())
                    .build()
    );

    @Comment("Pod ktorym slotem ma sie znajdowac przycisk od poprzedniej strony?")
    public int previousPageSlot = 45;

    @Comment("Pod ktorym slotem ma sie znajdowac przycisk od nastepnej strony?")
    public int nextPageSlot = 53;

    @Comment("Jaki item ma przybrac kazde miejsce w topce?")
    public ItemStack topItem = new ItemBuilder(XMaterial.BOOK.parseItem())
            .setName("&7Miejsce: &9&l{place}")
            .setLore("&7Nick: &c{nick}",
                    "&f- &c{value} &8- &f{display_name}")
            .toItemStack();
}
