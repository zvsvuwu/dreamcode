package cc.dreamcode.home.config;

import cc.dreamcode.notice.NoticeType;
import cc.dreamcode.notice.bukkit.BukkitNotice;
import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.Headers;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

@Configuration(child = "message.yml")
@Headers({
        @Header("## Dream-Home (Message-Config) ##"),
        @Header("Dostepne type: (CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE)")
})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessageConfig extends OkaeriConfig {

    public BukkitNotice usage = new BukkitNotice(NoticeType.CHAT, "&7Poprawne uzycie: &c{usage}");
    public BukkitNotice noPermission = new BukkitNotice(NoticeType.CHAT, "&4Nie posiadasz uprawnien.");
    public BukkitNotice noPlayer = new BukkitNotice(NoticeType.CHAT, "&4Podanego gracza &cnie znaleziono.");
    public BukkitNotice playerIsOffline = new BukkitNotice(NoticeType.CHAT, "&4Podany gracz &cjest offline.");
    public BukkitNotice notPlayer = new BukkitNotice(NoticeType.CHAT, "&4Nie jestes graczem.");
    public BukkitNotice notNumber = new BukkitNotice(NoticeType.CHAT, "&4Podana liczba &cnie jest cyfra.");
    public BukkitNotice playerIsMe = new BukkitNotice(NoticeType.CHAT, "&4Nie rob tego &cna sobie.");

    public BukkitNotice homeNotSet = new BukkitNotice(NoticeType.CHAT, "&cNie mozesz przeteleportowac sie do domu, ktorego nie ustawiles");
    public BukkitNotice homeSetSuccess = new BukkitNotice(NoticeType.CHAT, "&aPomyslnie ustawiono dom");
    public BukkitNotice teleportationNotice = new BukkitNotice(NoticeType.TITLE_SUBTITLE, "&cTeleportacja...%NEWLINE%&7Pozostalo &c{time} &7sekund");
    public BukkitNotice teleportationSuccess = new BukkitNotice(NoticeType.CHAT, "&aPomyslnie przeteleportowano na home");
    public BukkitNotice teleportationFailed = new BukkitNotice(NoticeType.CHAT, "&cTeleportacja zostala przerwana, poniewaz sie ruszyles");
}
