package cc.dreamcode.guisuffix.config;

import cc.dreamcode.notice.minecraft.MinecraftNoticeType;
import cc.dreamcode.notice.minecraft.bukkit.BukkitNotice;
import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;


@Configuration(
        child = "message.yml"
)
@Headers({
        @Header("## Dream-GUISuffix (Message-Config) ##"),
        @Header("Dostepne type: (CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE)")
})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessageConfig extends OkaeriConfig {

    public BukkitNotice noPermission = new BukkitNotice(MinecraftNoticeType.CHAT, "&cNie posiadasz uprawnien ({permission}).");

    public BukkitNotice commandCooldown = new BukkitNotice(MinecraftNoticeType.CHAT, "&cPoczekaj jeszcze {time} sekund przed uzyciem tej komendy");

    public BukkitNotice playerOffline = new BukkitNotice(MinecraftNoticeType.CHAT, "&cGracz jest offline");

    public BukkitNotice suffixGiven = new BukkitNotice(MinecraftNoticeType.CHAT, "&7Nadano suffix {suffix} graczowi {player}");

    public BukkitNotice suffixReceived = new BukkitNotice(MinecraftNoticeType.CHAT, "&7Otrzymałeś suffix {formatted}&7.", "&7Odbierz go uzywajac komendy &a/suffix");

    public BukkitNotice invalidSuffix = new BukkitNotice(MinecraftNoticeType.CHAT, "&cPodany suffix jest nieprawidlowy");

    public BukkitNotice commandUsage = new BukkitNotice(MinecraftNoticeType.CHAT, "&cPoprawne uzycie: &7{usage}");

    public BukkitNotice notPlayer = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Musisz byc graczem, aby to zrobic.");

    public BukkitNotice unknownProblem = new BukkitNotice(MinecraftNoticeType.CHAT, "&cWystapil niezidentyfikowany problem, zglos sie do administracji");

    public BukkitNotice suffixChanged = new BukkitNotice(MinecraftNoticeType.CHAT, "&7Twoj suffix to teraz: &r{formatted}");

    public BukkitNotice suffixRemoved = new BukkitNotice(MinecraftNoticeType.CHAT, "&7Twoj suffix zostal usuniety");

    public BukkitNotice noSuffix = new BukkitNotice(MinecraftNoticeType.CHAT, "&cNie posiadasz suffix'u");

    public BukkitNotice suffixInUse = new BukkitNotice(MinecraftNoticeType.CHAT, "&cUzywasz juz tego suffixu");

    public BukkitNotice hasSuffix = new BukkitNotice(MinecraftNoticeType.CHAT, "&cGracz juz posiada ten suffix");

    public BukkitNotice menuOpened = new BukkitNotice(MinecraftNoticeType.CHAT, "&aOtworzyles menu");

    public BukkitNotice configurationReloaded = new BukkitNotice(MinecraftNoticeType.CHAT, "&aKonfiguracja pluginu zostala przeladowana");

    public BukkitNotice badTime = new BukkitNotice(MinecraftNoticeType.CHAT, "&cPodany czas jest nieprawidlowy.");

    public BukkitNotice noPermissionSuffix = new BukkitNotice(MinecraftNoticeType.CHAT, "&cNie masz dostepu do tego suffixu");

    public BukkitNotice notEnoughMoney = new BukkitNotice(MinecraftNoticeType.CHAT, "&cNie stac cie na ten suffix!");

    public BukkitNotice suffixPurchased = new BukkitNotice(MinecraftNoticeType.CHAT, "&aSuffix {suffix} zostal zakupiony za {price}$");

    public BukkitNotice cooldownSet = new BukkitNotice(MinecraftNoticeType.CHAT, "&aCooldown komendy /{command} od teraz wynosi {time}");

    @Exclude
    public BukkitNotice adminSuffixCommandUsage = new BukkitNotice(MinecraftNoticeType.CHAT, "&7Dostepne komendy:", "&a/asuffix reload", "&a/asuffix cooldown (suffix/usunsuffix) (czas w sekundach)");
}