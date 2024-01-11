package cc.dreamcode.anvil.config;

import cc.dreamcode.notice.NoticeType;
import cc.dreamcode.notice.bukkit.BukkitNotice;
import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
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
        @Header("## Dream-Kowadlo (Message-Config) ##"),
        @Header("Dostepne type: (CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE)")
})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessageConfig extends OkaeriConfig {


    public BukkitNotice noPermission = new BukkitNotice(NoticeType.CHAT, "&4Nie posiadasz uprawnien.");
    public BukkitNotice noPlayer = new BukkitNotice(NoticeType.CHAT, "&4Podanego gracza &cnie znaleziono.");
    public BukkitNotice itemCantBeRepaired = new BukkitNotice(NoticeType.CHAT, "&7Tego przedmiotu nie da sie naprawic!");
    public BukkitNotice mustHoldTool = new BukkitNotice(NoticeType.CHAT, "&7Musisz trzymac przedmiot ktory chcesz naprawic!");
    public BukkitNotice successfullyRepaired = new BukkitNotice(NoticeType.CHAT, "&7Pomyslnie naprawiles przedmiot!");
    public BukkitNotice dontHaveEnoughItem = new BukkitNotice(NoticeType.CHAT, "&7Nie masz wystarczajaco bloku potrzebnego do naprawienia tego przedmiotu ! {block}");



}
