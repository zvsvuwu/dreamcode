package cc.dreamcode.report.config;

import cc.dreamcode.report.config.annotations.Configuration;
import cc.dreamcode.report.notice.Notice;
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
        @Header("## Dream-Report (Message-Config) ##"),
        @Header("Dostepne type: (CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE, SIDEBAR)")
})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessageConfig extends OkaeriConfig {

    public Notice usage = new Notice(Notice.Type.CHAT, "&7Poprawne uzycie: &c{usage}");
    public Notice noPermission = new Notice(Notice.Type.CHAT, "&4Nie posiadasz uprawnien.");
    public Notice noPlayer = new Notice(Notice.Type.CHAT, "&4Podanego gracza &cnie znaleziono.");
    public Notice noPlayerOrCannotFindReports = new Notice(Notice.Type.CHAT, "&4Podanego gracza &cnie znaleziono lub gracz nie posiada zadnych zgloszen do wyswietlenia.");
    public Notice notPlayer = new Notice(Notice.Type.CHAT, "&4Musisz byc graczem, aby to zrobic.");
    public Notice actionPlayedOnSelf = new Notice(Notice.Type.CHAT, "&cNie mozesz tego zrobic na sobie.");
    public Notice coolDown = new Notice(Notice.Type.CHAT, "&cAby to zrobic, poczekaj: &7{time}");
    public Notice reloaded = new Notice(Notice.Type.CHAT, "&aPrzeladowano! &7({time})");

    public Notice reportRemoved = new Notice(Notice.Type.CHAT, "&cUsunieto zgloszenie gracza {nick}. &8(&5&l{id}&8)");
    public Notice reportSend = new Notice(Notice.Type.CHAT, "&7Poprawnie wyslano zgloszenie gracza &c{nick}");
    public Notice reportAdmin = new Notice(Notice.Type.CHAT,
            "&7Nowe zgloszenie &8(&5&l{id}&8)",
            "",
            "&7Zglosil: &e{reporter}",
            "&7Zgloszono: &e{target}",
            "&7Powod: &a{reason}",
            "",
            "&7(&cuzyj /report panel {target}, aby sprawdzic zgloszenie&7)"
    );

    public Notice firstPageReach = new Notice(Notice.Type.CHAT, "&cOsiagnieto pierwsza strone, dalej juz nic nie ma!");
    public Notice lastPageReach = new Notice(Notice.Type.CHAT, "&cOsiagnieto ostatnia strone, dalej juz nic nie ma!");

    public Notice reportToggleOn = new Notice(Notice.Type.CHAT, "&aAktywowano powiadomienia zgloszen!");
    public Notice reportToggleOff = new Notice(Notice.Type.CHAT, "&cDezaktywowano powiadomienia zgloszen!");
    public Notice reportCleared = new Notice(Notice.Type.CHAT, "&cUsunieto wszystkie zgloszenia gracza {nick}.");
    public Notice reportEmpty = new Notice(Notice.Type.CHAT, "&cTen gracz nie posiada zadnych zgloszen do wyswietlenia.");

}
