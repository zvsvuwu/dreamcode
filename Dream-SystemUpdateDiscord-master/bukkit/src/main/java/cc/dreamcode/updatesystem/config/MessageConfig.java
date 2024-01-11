package cc.dreamcode.updatesystem.config;

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
        @Header("## Dream-SystemUpdateDiscord (Message-Config) ##"),
        @Header("Dostepne type: (CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE)")
})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessageConfig extends OkaeriConfig {

    public BukkitNotice inventoryClickMessage = new BukkitNotice(NoticeType.CHAT, "&7Link do naszego discorda to: &9https://discord.gg/dreamcode");
    public BukkitNotice newMessage = new BukkitNotice(NoticeType.CHAT, "&b&lDISCORD &8> &fWlasnie wlecialo nowe ogloszenie sprawdz pod: &7/zmiany");

    public BukkitNotice reload = new BukkitNotice(NoticeType.CHAT, "&aPliki konfiguracyjne zostaly przeladowane!");

}
