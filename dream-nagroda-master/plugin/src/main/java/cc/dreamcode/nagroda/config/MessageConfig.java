package cc.dreamcode.nagroda.config;

import cc.dreamcode.nagroda.config.annotations.Configuration;
import cc.dreamcode.nagroda.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;

@Configuration(child = "message.yml")
@Headers({
        @Header("## Dream-Nagroda (Message-Config) ##"),
        @Header("Dostepne type: (CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE, SIDEBAR)")
})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessageConfig extends OkaeriConfig {

    public Notice noPermission = new Notice(Notice.Type.CHAT, "&4Nie posiadasz uprawnien.");
    public Notice notPlayer = new Notice(Notice.Type.CHAT, "&4Nie jestes graczem.");

    public Notice rewardFail = new Notice(Notice.Type.CHAT, "&cOdebrales juz swoja nagrode");
    public Notice rewardSuccess = new Notice(Notice.Type.CHAT, "&7Twój losowo wygenerowany kod to &f{kod}" +
            "%NEWLINE%&7Wpisz go na kanale &f#odbierz-nagrode&7, aby odebrac nagrode");
    public Notice broadcastReward = new Notice(Notice.Type.CHAT, "&aGracz &e{gracz} &aodebral nagrode!");
    @Comment("Wiadomosci wysylane przez bota discord")
    public String codeContainsLetters = "Ten kod zawiera litery!";
    public String codeDoesntExist = "Ten kod nie istnieje!";
    public String playerOffline = "Nie jestes na serwerze!";
    public String receivedReward = "Otrzymales nagrode!";
    @Comment("Log wysylany do konsoli, gdy id kanalu jest nieprawidlowe")
    public String incorrectChannelId = "Niepoprawne id kanalu!";
}
