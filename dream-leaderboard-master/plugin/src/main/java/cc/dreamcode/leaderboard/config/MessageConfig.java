package cc.dreamcode.leaderboard.config;

import cc.dreamcode.leaderboard.config.annotations.Configuration;
import cc.dreamcode.leaderboard.notice.Notice;
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
        @Header("## Dream-Leaderboard (Message-Config) ##"),
        @Header("Dostepne type: (CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE, SIDEBAR)")
})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessageConfig extends OkaeriConfig {

    public Notice noPermission = new Notice(Notice.Type.CHAT, "&4Nie posiadasz uprawnien.");
    public Notice notPlayer = new Notice(Notice.Type.CHAT, "&4Nie jestes graczem.");

    public Notice firstPageReach = new Notice(Notice.Type.CHAT, "&cOsiagnieto pierwsza strone, dalej juz nic nie ma!");
    public Notice lastPageReach = new Notice(Notice.Type.CHAT, "&cOsiagnieto ostatnia strone, dalej juz nic nie ma!");

}
