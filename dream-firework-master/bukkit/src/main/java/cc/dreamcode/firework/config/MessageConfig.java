package cc.dreamcode.firework.config;

import cc.dreamcode.notice.minecraft.MinecraftNoticeType;
import cc.dreamcode.notice.minecraft.bukkit.BukkitNotice;
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
        @Header("## Dream-FireWork (Message-Config) ##"),
        @Header("Dostepne type: (DO_NOT_SEND, CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE)")
})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessageConfig extends OkaeriConfig {

    public BukkitNotice usage = new BukkitNotice(MinecraftNoticeType.CHAT, "&7Poprawne uzycie: &c{usage}");
    public BukkitNotice noPermission = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Nie posiadasz uprawnien.");
    public BukkitNotice notPlayer = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Nie jestes graczem.");
    public BukkitNotice noPlayer = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Podanego gracza &cnie znaleziono.");

    public BukkitNotice fireWorkPlayerGiven = new BukkitNotice(MinecraftNoticeType.CHAT, "&cDales magiczna fajerwerke graczowi {player}!");
    public BukkitNotice fireWorkGiven = new BukkitNotice(MinecraftNoticeType.CHAT, "&cDales calemu serwerowi magiczna fajerwerke!");
    public BukkitNotice fireWorkReceived = new BukkitNotice(MinecraftNoticeType.CHAT, "&cDostales magiczna fajerwerke od administratora {admin}!");
    public BukkitNotice fireWorkExists = new BukkitNotice(MinecraftNoticeType.CHAT, "&cPodany gracz posiada juz fajerwerke w swoim eq!");

}
