package cc.dreamcode.dropsmp.config;

import cc.dreamcode.dropsmp.features.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.Headers;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

@Headers({
        @Header("## Dream-DropSMP (Message-Config) ##"),
        @Header("Dostepne type: (CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE, SIDEBAR)")
})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessageConfig extends OkaeriConfig {

    public Notice usage = new Notice(Notice.Type.CHAT, "&7Poprawne uzycie: &c{usage}");
    public Notice noPermission = new Notice(Notice.Type.CHAT, "&4Nie posiadasz uprawnien.");
    public Notice noPlayer = new Notice(Notice.Type.CHAT, "&4Podanego gracza &cnie znaleziono.");
    public Notice notNumber = new Notice(Notice.Type.CHAT, "&4Podana liczba &cnie jest cyfra.");
    public Notice userDeath = new Notice(Notice.Type.CHAT, "&c{player} umarl");
    public Notice userKilledByOtherUser = new Notice(Notice.Type.CHAT, "&c{player} zostal zabity przez {killer}");
    //🗡 🛡
    public Notice actionbarMessage = new Notice(Notice.Type.ACTION_BAR,
            "&c{strength}% &7Strenght &3{resistance}% &7Protection &f{speed}% &7Speed");
    public Notice maxNumberOfStats = new Notice(Notice.Type.CHAT, "&7Osiagneles maksylana wartosc &5{stats}");
    public Notice correctlyUseEmblem = new Notice(Notice.Type.CHAT, "&aEmblemant zostal poprawnie uzyty");

    public Notice susscusfulGiveStat = new Notice(Notice.Type.CHAT, "&7Poprawnie dodano statystyke}");
}
