package xyz.ravis96.dreamfreeze.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import xyz.ravis96.dreamfreeze.helpers.TitleBuilder;
import xyz.ravis96.dreamfreeze.nms.notice.Notice;

@Header({"## Dream-Freeze (Message-Config) ##",
        "Dostepne type: (CHAT, ACTION_BAR, SUBTITLE, TITLE, SIDEBAR, BOSSBAR)"})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessageConfig extends OkaeriConfig {

    public Notice noPermission = new Notice(Notice.Type.CHAT, "&4Nie posiadasz uprawnien.");

    public Notice freezeEnable = new Notice(Notice.Type.CHAT, "&cCaly serwer zostal zamrozony!");
    public Notice freezeDisable = new Notice(Notice.Type.CHAT, "&aCaly serwer zostal odmrozony!");

}
