package cc.dreamcode.antygrief.config;

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
        @Header("## Dream-AntyGrief (Message-Config) ##"),
        @Header("Dostepne type: (CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE)")
})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessageConfig extends OkaeriConfig {

    public BukkitNotice noPermission = new BukkitNotice(NoticeType.CHAT, "&4Nie posiadasz uprawnien.");
    public BukkitNotice notPlayer = new BukkitNotice(NoticeType.CHAT, "&4Nie jestes graczem.");

    public BukkitNotice blockPlaced = new BukkitNotice(NoticeType.CHAT, "&6&lAntyGrief &7> &aBlok ktory postawiles zostanie usuniety za &6{time} &asekund");
    public BukkitNotice reloadedConfig = new BukkitNotice(NoticeType.CHAT, "&aPomyslnie przeladowano config");
}
