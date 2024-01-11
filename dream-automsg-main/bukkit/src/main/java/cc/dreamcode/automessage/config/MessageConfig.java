package cc.dreamcode.automessage.config;

import cc.dreamcode.notice.NoticeType;
import cc.dreamcode.notice.bukkit.BukkitNotice;
import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.Headers;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

@Configuration(
        child = "message.yml"
)
@Headers({
        @Header("## Dream-AutoMessage (Message-Config) ##"),
        @Header("Dostepne type: (CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE)")
})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessageConfig extends OkaeriConfig {

    public BukkitNotice usage = new BukkitNotice(NoticeType.CHAT, "&7Poprawne uzycie: &c{usage}");
    public BukkitNotice noPermission = new BukkitNotice(NoticeType.CHAT, "&4Nie posiadasz uprawnien.");
    public BukkitNotice noPlayer = new BukkitNotice(NoticeType.CHAT, "&4Podanego gracza &cnie znaleziono.");
    public BukkitNotice notNumber = new BukkitNotice(NoticeType.CHAT, "&4Podana liczba &cnie jest cyfra.");
    public BukkitNotice reloaded = new BukkitNotice(NoticeType.CHAT, "&4Konfiguracja pluginu zostala pomyslnie zaladowana!");
    public BukkitNotice reloadError = new BukkitNotice(NoticeType.CHAT, "&4Wystapil blad podczas wczytywania konfiguracji pluginu!");
    public BukkitNotice timeUpdated = new BukkitNotice(NoticeType.CHAT, "&4Pomyslnie zmieniles interval wysylanych wiadomosci na {time}");

}
