package cc.dreamcode.sprawdzanie.config;

import cc.dreamcode.sprawdzanie.config.annotations.Configuration;
import cc.dreamcode.sprawdzanie.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.Headers;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

@Configuration(child = "message.yml")
@Headers({
        @Header("## Dream-Sprawdzanie (Message-Config) ##"),
        @Header("Dostepne type: (CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE, SIDEBAR)")
})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessageConfig extends OkaeriConfig {

    public Notice usage = new Notice(Notice.Type.CHAT, "&7Poprawne uzycie: &c{usage}");
    public Notice noPermission = new Notice(Notice.Type.CHAT, "&4Nie posiadasz uprawnien.");
    public Notice noPlayer = new Notice(Notice.Type.CHAT, "&4Podanego gracza &cnie znaleziono.");
    public Notice notPlayer = new Notice(Notice.Type.CHAT, "&4Nie jestes graczem.");
    public Notice playerIsMe = new Notice(Notice.Type.CHAT, "&4Nie rob tego &cna sobie.");
    public Notice playerIsAdmin = new Notice(Notice.Type.CHAT, "&cPodany gracz jest administratorem");

    public Notice playerLogoutMessage = new Notice(Notice.Type.CHAT, "&cGracz {gracz} wylogowal sie podczas sprawdzania");
    public Notice adminCheckMessage = new Notice(Notice.Type.CHAT, "&cSprawdzasz gracza: &a{gracz}!");
    public Notice playerNotBeingChecked = new Notice(Notice.Type.CHAT, "&cTen gracz nie jest sprawdzany");
    public Notice senderNotBeingChecked = new Notice(Notice.Type.CHAT, "&cNie jestes sprawdzany");
    public Notice clearedPlayer = new Notice(Notice.Type.CHAT, "&aOczysciles gracza {gracz}");
    public Notice clearNotice = new Notice(Notice.Type.CHAT, "&aGracz {gracz} jest czysty!");
    public Notice admitNotice = new Notice(Notice.Type.CHAT, "&cGracz {gracz} przyznal sie do cheatowania!");
    public Notice checkNotice = new Notice(Notice.Type.CHAT, "&4Gracz {gracz} jest sprawdzany!");
    public Notice checkTitle = new Notice(Notice.Type.TITLE_SUBTITLE,
            "&4Jestes sprawdzany w celu wykrycia cheatow%NEWLINE%&cPostepuj zgodnie z poleceniami administracji", 1);
    public Notice checkMessage = new Notice(Notice.Type.CHAT,
            "&cJestes sprawdzany! Wejdz na kanal &a#sprawdzanie &cna naszym discordzie discord.gg/dreamcode! &4Masz 3 minuty");
    public Notice commandsBlocked = new Notice(Notice.Type.CHAT, "&cNie mozesz uzywac komend, gdy jestes sprawdzany");

}
