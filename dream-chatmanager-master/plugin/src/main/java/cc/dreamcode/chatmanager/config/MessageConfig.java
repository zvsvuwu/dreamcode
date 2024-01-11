package cc.dreamcode.chatmanager.config;

import cc.dreamcode.chatmanager.config.annotations.Configuration;
import cc.dreamcode.chatmanager.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.Headers;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

@Configuration(child = "message.yml")
@Headers({
        @Header("## Dream-ChatManager (Message-Config) ##"),
        @Header("Dostepne type: (CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE, SIDEBAR)")
})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessageConfig extends OkaeriConfig {

    public Notice usage = new Notice(Notice.Type.CHAT, "&7Poprawne uzycie: &c{usage}");
    public Notice noPermission = new Notice(Notice.Type.CHAT, "&4Nie posiadasz uprawnien.");
    public Notice notPlayer = new Notice(Notice.Type.CHAT, "&4Nie jestes graczem.");
    public Notice configReloaded = new Notice(Notice.Type.CHAT, "&aPomyslnie przeladowano config");
    public Notice playerIsOnCooldown = new Notice(Notice.Type.CHAT, "&aNie mozesz pisac na chacie przez {time}");

    public Notice forbiddenWordDetected = new Notice(Notice.Type.CHAT, "&cNie mozesz uzyc tego slowa na chacie");
    public Notice linkDetected = new Notice(Notice.Type.CHAT, "&aNie wysylaj linkow na chacie");
    public Notice messageOfTheDay = new Notice(Notice.Type.CHAT, "&aWitaj &e{gracz} &ana serwerze &9TwojSerwer.pl" +
            "%NEWLINE%&6Znajdujesz sie na trybie &f&lSurvival + Gildie&a!");
    public Notice adminNotice = new Notice(Notice.Type.CHAT, "&aWykryto link/zabronione slowo! " +
            "%NEWLINE%&e{gracz}&f:&c {tresc}");
    public Notice lockedChat = new Notice(Notice.Type.CHAT, "&cChat jest zablokowany");
    public Notice chatCleared = new Notice(Notice.Type.CHAT, "&aChat zostal wyczyszczony przez {gracz}");
    public Notice chatIsAlreadyLocked = new Notice(Notice.Type.CHAT, "&cChat jest juz zablokowany");
    public Notice chatIsAlreadyUnlocked = new Notice(Notice.Type.CHAT, "&cChat jest juz odblokowany");
    public Notice chatLockSuccess = new Notice(Notice.Type.CHAT, "&aChat zostal zablokowany");
    public Notice chatUnlockSuccess = new Notice(Notice.Type.CHAT, "&aChat zostal odblokowany");
    public Notice chatLockAnnouncement = new Notice(Notice.Type.CHAT, "&aChat zostal zablokowany przez {gracz}");
    public Notice chatUnlockAnnouncement = new Notice(Notice.Type.CHAT, "&aChat zostal odblokowany przez {gracz}");
}
