package xyz.ravis96.dreamkit.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import xyz.ravis96.dreamkit.nms.notice.Notice;

@Header({"## Dream-Kit (Message-Config) ##",
        "Dostepne type: (CHAT, ACTION_BAR, SUBTITLE, TITLE, SIDEBAR)"})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessageConfig extends OkaeriConfig {

    public Notice noPermission = new Notice(Notice.Type.CHAT, "&4Nie posiadasz uprawnien.");

    public Notice kitsIsDisabled = new Notice(Notice.Type.CHAT, "&cZestawy sa wylaczone!");
    public Notice kitIsDisabled = new Notice(Notice.Type.CHAT, "&cTen zestaw jest wylaczony!");
    public Notice noKitPermission = new Notice(Notice.Type.CHAT, "&cNie posiadasz uprawnien do tego zestawu.");
    public Notice kitCoolDown = new Notice(Notice.Type.CHAT, "&cZestaw mozesz odebrac za: &7%TIME%");
    public Notice kitPickedUp = new Notice(Notice.Type.CHAT, "&aZestaw odebrany!");

    public Notice kitEnabled = new Notice(Notice.Type.CHAT, "&aKit %NAME% zostal aktywowany!");
    public Notice kitDisabled = new Notice(Notice.Type.CHAT, "&cKit %NAME% zostal dezaktywowany!");
    public Notice kitsEnabled = new Notice(Notice.Type.CHAT, "&aKity zostaly aktywowane!");
    public Notice kitsDisabled = new Notice(Notice.Type.CHAT, "&cKity zostaly dezaktywowane!");

    public Notice kitNotFound = new Notice(Notice.Type.CHAT, "&cPodanego kita nie znaleziono.");

}
