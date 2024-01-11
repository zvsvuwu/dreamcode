package cc.dreamcode.anticobweb.config;

import cc.dreamcode.notice.NoticeType;
import cc.dreamcode.notice.bukkit.BukkitNotice;
import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

@Configuration(
        child = "messages.yml"
)
@Header("## AntiCobweb Messages Config ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessagesConfig extends OkaeriConfig {
    @Comment("Wiadomość używana podczas używania narzędzia")
    public BukkitNotice useMessage = new BukkitNotice(NoticeType.CHAT, "&aUżyłeś narzędzia");

    @Comment("Wiadomość używana podczas gdy gracz posiada cooldown")
    public BukkitNotice cooldownMessage = new BukkitNotice(NoticeType.ACTION_BAR, "&cPoczekaj {cooldown} sekund(-y) przed następnym użyciem tego narzędzia");

    @Comment("Wiadomość używana podczas gdy gracz używa komendy aby dostać różdżke")
    public BukkitNotice giveMessage = new BukkitNotice(NoticeType.CHAT, "&6Dostałeś narzędzie, aby je użyc kliknij nim na blok lub powietrze");

    @Comment("Wiadomość używana podczas gdy gracz nie posiada permisji")
    public BukkitNotice noPermission = new BukkitNotice(NoticeType.CHAT, "&cNie posiadasz uprawnień.");

    @Comment("Wiadomość używana podczas gdy osoba która ją wysłała nie jest graczem")
    public BukkitNotice notPlayer = new BukkitNotice(NoticeType.CHAT, "&cNie jesteś graczem.");
}
