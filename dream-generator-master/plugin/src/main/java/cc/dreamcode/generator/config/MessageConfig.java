package cc.dreamcode.generator.config;

import cc.dreamcode.generator.features.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.Headers;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

@Headers({
        @Header("## Dream-Generator (Message-Config) ##"),
        @Header("Dostepne type: (CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE, SIDEBAR)")
})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessageConfig extends OkaeriConfig {

    public Notice usage = new Notice(Notice.Type.CHAT, "&7Poprawne uzycie: &c{usage}");
    public Notice noPermission = new Notice(Notice.Type.CHAT, "&4Nie posiadasz uprawnien.");
    public Notice dataLoaded = new Notice(Notice.Type.CHAT, "&cData zostalo zaladowane z database!");
    public Notice generatorIsAlreadyPlaceHere = new Notice(Notice.Type.CHAT, "&cW tym miejscu jest juz stoniarka");
    public Notice successfulCreateGenerator = new Notice(Notice.Type.CHAT, "&aPoprawnie postawiono stoniarke");
    public Notice successfulDeleteGenerator = new Notice(Notice.Type.CHAT, "&cPoprawnie usunieto stoniarke");

}
