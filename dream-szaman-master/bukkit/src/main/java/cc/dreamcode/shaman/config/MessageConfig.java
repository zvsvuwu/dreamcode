package cc.dreamcode.shaman.config;

import cc.dreamcode.notice.minecraft.MinecraftNoticeType;
import cc.dreamcode.notice.minecraft.bukkit.BukkitNotice;
import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Comments;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.Headers;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

@Configuration(
        child = "message.yml"
)
@Headers({
        @Header("## Dream-szaman (Message-Config) ##"),
        @Header("Dostepne type: (CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE)")
})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessageConfig extends OkaeriConfig {

    public BukkitNotice usage = new BukkitNotice(MinecraftNoticeType.CHAT, "&7Poprawne uzycie: &c{usage}");

    public BukkitNotice perkAdminCommand = new BukkitNotice(MinecraftNoticeType.CHAT,
            "&bPolecenia administracyjne:",
            "&f/szaman reset <nick> &8- &7resetuje wszystkie ulepszenia.",
            "&f/szaman set <health/speed/damage/spellVamp/fall> <lvl> <player> &8- &7ustawia level ulepszenia dla danego gracza.",
            "&f/szaman get <player> &8- &7wyswietla informacje o ulepszeniach gracza.",
            "&f/szaman reload &8- &7reload configu.");
    public BukkitNotice resetSuccess = new BukkitNotice(MinecraftNoticeType.CHAT, "&bStatystyki gracza zostaly zresetowane!");
    public BukkitNotice resetFail = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Ten gracz ma już zresetowane statystyki!");
    public BukkitNotice perkGetCommand = new BukkitNotice(MinecraftNoticeType.CHAT,
            "&bStatystyki gracza:",
            "&fPerk zycia: &b{perk-health}lvl",
            "&fPerk szybkosci: &b{perk-speed}lvl",
            "&fPerk sily: &b{perk-damage}lvl",
            "&fPerk wampiryzmu: &b{perk-spellvamp}lvl",
            "&fPerk upadku: &b{perk-fall}lvl");
    public BukkitNotice transactionFail = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Transakcja sie nie powiodla!");
    public BukkitNotice perkNotExists = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Nie odnaleziono takiego perka, dostepne: &chealth/speed/damage/spellVamp/fall");
    public BukkitNotice perkLevelNotExists = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Level {level} nie istnieje dla tego perka.");
    public BukkitNotice incorrectLvl = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Level nie jest liczba!");
    public BukkitNotice updateCommandLevel = new BukkitNotice(MinecraftNoticeType.CHAT, "&6Perk: &b{perk} &6gracza: &b{player} &6zostal zaaktualizowany do poziomu: &b{level}");
    public BukkitNotice noPermission = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Nie posiadasz uprawnien.");
    public BukkitNotice noPlayer = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Podanego gracza &cnie znaleziono.");
    public BukkitNotice upgradeMax = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Ten perk ma juz maxymalny poziom.");
    public BukkitNotice upgradeHavingNoItems = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Nie posiadasz wymaganych przedmiotów do ulepszenia.");
    public BukkitNotice upgradeHavingNoMoney = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Nie posiadasz tyle $.");
    public BukkitNotice upgradeSuccess = new BukkitNotice(MinecraftNoticeType.CHAT, "&6{perk} &bzostal ulepszony do poziomu: &6{level}.");
    public BukkitNotice playerFallPerkMessage = new BukkitNotice(MinecraftNoticeType.CHAT, "&fFallPerk > &bNie otrzymales obrazen od updaku!");
    public BukkitNotice playerDamageMessage = new BukkitNotice(MinecraftNoticeType.ACTION_BAR, "&fDamagePerk > &bZadales: &f{damage} &bzamiast: &f{predamage}");
    public BukkitNotice playerSpellVampMessage = new BukkitNotice(MinecraftNoticeType.ACTION_BAR, "&fSpellVampPerk > &bUleczyles sie: &f{heal}❤");
    @Comments(value = {
            @Comment("Czy powyzsze dwie wiadomosci maja byc wyswietlane jako jedna?"),
            @Comment("true - zostanie wyswietlone playerDamageAndSpellVampMessage tylko i wylacznie wtedy jesli gracz zada wiecej obrazen oraz sie uleczy"),
            @Comment("false - wiadomosci zostana wyswietlone oddzielnie")
    })
    public boolean bothMessageInOne = true;
    public BukkitNotice playerDamageAndSpellVampMessage = new BukkitNotice(MinecraftNoticeType.ACTION_BAR, "{damagemessage} {spellvampmessage}");
    public BukkitNotice reload = new BukkitNotice(MinecraftNoticeType.CHAT, "&aPliki konfiguracyjne zostaly przeladowane!");


}
