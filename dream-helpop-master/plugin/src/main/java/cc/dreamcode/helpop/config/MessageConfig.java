package cc.dreamcode.helpop.config;

import cc.dreamcode.helpop.config.annotations.Configuration;
import cc.dreamcode.helpop.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;

@Configuration(child = "message.yml")
@Headers({
        @Header("## Dream-Helpop (Message-Config) ##"),
        @Header("Dostepne type: (CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE, SIDEBAR)")
})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessageConfig extends OkaeriConfig {

    public Notice usage = new Notice(Notice.Type.CHAT, "&7Poprawne uzycie: &c{usage}");
    public Notice noPermission = new Notice(Notice.Type.CHAT, "&4Nie posiadasz uprawnien.");
    public Notice notPlayer = new Notice(Notice.Type.CHAT, "&4Nie jestes graczem.");

    public Notice alreadyOn = new Notice(Notice.Type.CHAT, "&aHelpop jest juz wlaczone");
    public Notice alreadyOff = new Notice(Notice.Type.CHAT, "&cHelpop jest juz wylaczony");
    public Notice helpopOn = new Notice(Notice.Type.CHAT, "&aWlaczono powiadomienia helpop");
    public Notice helpopOff = new Notice(Notice.Type.CHAT, "&cWylaczono powiadomienia helpop");
    public Notice playerIsOnCooldown = new Notice(Notice.Type.CHAT, "&cNie mozesz jeszcze uzyc tej komendy przez {time}");
    public Notice helpopSuccess = new Notice(Notice.Type.CHAT, "&aPomyslnie wyslano zgloszenie");
    public Notice helpopNotice = new Notice(Notice.Type.CHAT, "&cHelpop &8> &7[&e{gracz}&7] &a{tresc}");
    @Comment("Log wysylany do konsoli, gdy id kanalu jest nieprawidlowe")
    public String incorrectChannelId = "Niepoprawne id kanalu!";
}
