package cc.dreamcode.automessage.config;

import cc.dreamcode.notice.NoticeType;
import cc.dreamcode.notice.bukkit.BukkitNotice;
import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration(
        child = "config.yml"
)
@Header("## Dream-AutoMessage (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {
    @Comment("Debug pokazuje dodatkowe informacje do konsoli. Lepiej wylaczyc. :P")
    public boolean debug = true;

    @Comment("Czy ma wysylac wiadomosci")
    public boolean shouldSendMessages = true;

    @Comment("Co ile ma byc wysylana wiadomosc")
    public Duration msgInterval = Duration.ofSeconds(10);

    @Comment("Lista wiadomosci ktore wysyla plugin")
    public List<BukkitNotice> messages = new ArrayList<>(Arrays.asList(
            new BukkitNotice(NoticeType.CHAT, "&1Testowa Wiadomosc"),
            new BukkitNotice(NoticeType.CHAT, "&22 Druga testowa wiadomosc"),
            new BukkitNotice(NoticeType.CHAT, "&3Trzecie testowa Wiadomosc")));
}
