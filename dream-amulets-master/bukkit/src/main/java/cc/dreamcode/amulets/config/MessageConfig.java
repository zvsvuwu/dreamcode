package cc.dreamcode.amulets.config;

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
        @Header("## Dream-Amulets (Message-Config) ##"),
        @Header("Dostepne type: (DO_NOT_SEND, CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE)")
})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessageConfig extends OkaeriConfig {

    public BukkitNotice usage = new BukkitNotice(NoticeType.CHAT, "&7Poprawne użycie: &c{usage}");
    public BukkitNotice noPermission = new BukkitNotice(NoticeType.CHAT, "&4Nie posiadasz uprawnień.");
    public BukkitNotice notPlayer = new BukkitNotice(NoticeType.CHAT, "&4Nie jesteś graczem.");
    public BukkitNotice amuletNotFound = new BukkitNotice(NoticeType.CHAT, "&cNie znaleziono amuletu o podanej nazwie.");
    public BukkitNotice playerNotFound = new BukkitNotice(NoticeType.CHAT, "&cNie znaleziono gracza o podanej nazwie.");
    public BukkitNotice amuletGivenSuccess = new BukkitNotice(NoticeType.CHAT, "&aDałeś {amulet} dla {player}");
    public BukkitNotice amuletReceived = new BukkitNotice(NoticeType.CHAT, "&aOtzymałeś {amulet}");
    public BukkitNotice amuletUsed = new BukkitNotice(NoticeType.CHAT, "&aUżyłeś {amulet}");

    public BukkitNotice amuletsEnabled = new BukkitNotice(NoticeType.CHAT, "&aWłączyłeś globalnie amulety");

    public BukkitNotice amuletsDisabled = new BukkitNotice(NoticeType.CHAT, "&cWyłączyłeś globalnie amulety");
}
