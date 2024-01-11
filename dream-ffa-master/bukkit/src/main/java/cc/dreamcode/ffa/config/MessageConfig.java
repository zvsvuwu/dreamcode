package cc.dreamcode.ffa.config;

import cc.dreamcode.notice.minecraft.MinecraftNotice;
import cc.dreamcode.notice.minecraft.MinecraftNoticeType;
import cc.dreamcode.notice.minecraft.bukkit.BukkitNotice;
import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import org.bukkit.entity.Player;

import java.util.Map;

/**
 * MessageConfig.java
 * Purpose: The MessageConfig class is utilised by other classes to access messages sent to users.
 * * @author trueman96
 * @version 1.0-beta.1
 * @since 2023-11-24
 */
@Configuration(child = "message.yml")
@Headers({
        @Header("## Dream-FFA (Message-Config) ##"),
        @Header("Dostepne type: (DO_NOT_SEND, CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE)")
})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public final class MessageConfig extends OkaeriConfig {

    public BukkitNotice usage = new BukkitNotice(MinecraftNoticeType.CHAT, "&cPoprawne uzycie: &7{usage}");
    public BukkitNotice noPermission = new BukkitNotice(MinecraftNoticeType.CHAT, "&cNie posiadasz uprawnien.");
    public BukkitNotice notPlayer = new BukkitNotice(MinecraftNoticeType.CHAT, "&cNie jestes graczem.");

    @Comment("Dostępne placeholdery: (points-to-remove, victim)")
    public BukkitNotice playerSuicideAnnounce = new BukkitNotice(MinecraftNoticeType.CHAT, "&c{victim} &8(&7-{points-to-remove}&8) &cpopełnił samobójstwo!");

    @Comment("Dostępne placeholdery: (points-to-add, points-to-remove, victim, killer, killer-health)")
    public BukkitNotice playerKillAnnounce = new BukkitNotice(MinecraftNoticeType.CHAT, "&c{victim} &8(&7-{points-to-remove}&8) &czostał zabity przez &c{killer} &8(&7+{points-to-add}&8, &c{killer-health}❤&8)");

    @Comment("Dostępne placeholdery: (points-to-add, points-to-remove, victim, killer, killer-health)")
    public BukkitNotice playerKillTitleKiller = new BukkitNotice(MinecraftNoticeType.TITLE_SUBTITLE,
            "&aZabójstwo!", "&cZabiłeś gracza {victim} &8(&7+{points-to-add}, &c{killer-health}❤&8)");
    @Comment("Dostępne placeholdery: (points-to-add, points-to-remove, victim, killer, killer-health)")
    public BukkitNotice playerKillTitleKilled = BukkitNotice.of(MinecraftNoticeType.TITLE_SUBTITLE,
            "&cZginąłeś!", "&cZostałeś zabity przez gracza {killer} &8(&7-{points-to-remove}, &c{killer-health}❤&8)");

    @Comment("Dostępne placeholdery: (points-to-remove, victim)")
    public BukkitNotice playerKillTitleSuicide = BukkitNotice.of(MinecraftNoticeType.TITLE_SUBTITLE,
            "&cZginąłeś!", "&cPopełniłeś samobójstwo &8(&7-{points-to-remove}&8)");
    @Comment("Dostępne placeholdery: (points-to-add, points-to-remove, victim, killer, killer-health, points-to-add-assistant)")
    public BukkitNotice playerAssistTitleAssistant = BukkitNotice.of(MinecraftNoticeType.TITLE_SUBTITLE,
            "&aAsysta!", "&cAsystowałeś przy zabiciu {victim} &8(&7+{points-to-add-assistant}, &c{assistant-health}❤&8)");

    @Comment("Dostępne placeholdery: (points-to-add, points-to-remove, victim, killer, killer-health, points-to-add-assistant)")
    public BukkitNotice playerAssistAnnounce = BukkitNotice.of(MinecraftNoticeType.TITLE_SUBTITLE,
            "&c{assistant} asystował przy zabiciu {victim} &8(&7+{ASSISTANT_POINTS}&8, &c{ASSISTANT_HEARTS}❤&8)");
    public BukkitNotice healthInfo = BukkitNotice.of(MinecraftNoticeType.ACTION_BAR, "&7HP zaatakowanego gracza: &c{health}");
    public BukkitNotice commandIsDisallowedWhileInCombat = BukkitNotice.of(MinecraftNoticeType.CHAT, "&cTa komenda zostala zablokowana podczas walki!");
    public BukkitNotice playerLoggedOutWhileInCombat = BukkitNotice.of(MinecraftNoticeType.CHAT, "&cGracz {player} wylogował się podczas walki!");

    public BukkitNotice combatInfo = BukkitNotice.of(MinecraftNoticeType.ACTION_BAR,
            "&c&lAntiLogout &8(&c{combat-time}&7, {combat-progress}&7, &c{combat-percent}&6%&8)");

    @Comment("Symbol używany w placeholderze: combat-progress")
    public String progressSymbol = "■";

    @Comment("Dostępne placeholdery: (current_ks, maximum_ks)")
    public BukkitNotice killStreakInfo = BukkitNotice.of(MinecraftNoticeType.CHAT,
            "&8» &7Aktualny KS: &f{current_ks}",
            "&8» &7Maksymalny KS: &f{maximum_ks}"
    );

    @Comment("Dostępne placeholdery: (killer, current_ks, maximum_ks)")
    public Map<Integer, BukkitNotice> killStreakAnnounce = new MapBuilder<Integer, BukkitNotice>()
            .put(1, BukkitNotice.of(MinecraftNoticeType.CHAT, "&cGracz {killer} osiągnął killstreak {current_ks}"))
            .put(10, BukkitNotice.of(MinecraftNoticeType.CHAT, "&cGracz {killer} osiągnął killstreak {current_ks}"))
            .build();

    @Comment("Dostępne placeholdery: (items-saved)")
    public BukkitNotice savedInventory = BukkitNotice.of(MinecraftNoticeType.CHAT, "&aPomyślnie zapisano obecny stan ekwipunku");
    public BukkitNotice resetedInventory = BukkitNotice.of(MinecraftNoticeType.CHAT, "&aPomyślnie zresetowano stan zapisanego ekwipunku.");

    @Comment({
            "Format topki punktów po użyciu placeholder'a",
            "Dostępne placeholdery: (user-name, user-points)"
    })
    public String pointsRankingFormat = "&f{user-name} &8[&7{user-points}&8]";
    public String pointsRankingNotFound = "&cBrak!";
    public String killStreakRankingFormat = "&f{user-name} &8[&7{user-ks}&8]";
    public String killStreakRankingNotFound = "&cBrak!";
    public String maxKillStreakRankingFormat = "&f{user-name} &8[&7{user-max-ks}&8]";
    public String maxKillStreakRankingNotFound = "&cBrak!";
    public BukkitNotice reloaded = BukkitNotice.of(MinecraftNoticeType.CHAT, "&aPrzeładowano konfiguracje!");
}
