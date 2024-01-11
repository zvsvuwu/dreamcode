package cc.dreamcode.timecmd.config;

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
        @Header("## Dream-TimeCommand (Message-Config) ##"),
        @Header("Dostepne type: (DO_NOT_SEND, CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE)")
})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessageConfig extends OkaeriConfig {

    public BukkitNotice usage = new BukkitNotice(MinecraftNoticeType.CHAT, "&7Poprawne uzycie: &c{usage}");
    public BukkitNotice noPermission = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Nie posiadasz uprawnien.");
    public BukkitNotice noPlayer = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Podanego gracza &cnie znaleziono.");
    public BukkitNotice playerIsOffline = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Podany gracz &cjest offline.");
    public BukkitNotice notPlayer = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Nie jestes graczem.");
    public BukkitNotice notNumber = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Podana liczba &cnie jest cyfra.");
    public BukkitNotice playerIsMe = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Nie rob tego &cna sobie.");
    public BukkitNotice cantUseCommand = new BukkitNotice(MinecraftNoticeType.CHAT, "&cAby uzyc tej komendy musisz przegrać: &6{time}!",
            "&cPrzegrales: &6{player-time}, &cmusisz przegrac jeszcze: &6{missing-time}");

    public BukkitNotice commandHelp = new BukkitNotice(MinecraftNoticeType.CHAT, "&c/timecommand create <command> <time> &8- &7tworzy komende.",
            "&c/timecommand delete <command> &8- &7usuwa komende.",
            "&c/timecommand list &8- &7wyswietla liste komend.");
    public BukkitNotice listFormat = new BukkitNotice(MinecraftNoticeType.CHAT, "&c{command} &8- &b{time}");
    public BukkitNotice commandDoesNotExists = new BukkitNotice(MinecraftNoticeType.CHAT, "&cTaka komenda nie istnieje.");
    public BukkitNotice commandDeleted = new BukkitNotice(MinecraftNoticeType.CHAT, "&cKomenda &6{command} &czostala usunieta.");
    public BukkitNotice commandCreated = new BukkitNotice(MinecraftNoticeType.CHAT, "&aKomenda &6{command} &azostala stworzona z wymaganym czasem: &6{time}.");
    public BukkitNotice reload = new BukkitNotice(MinecraftNoticeType.CHAT, "&aPlugin zostal przeladowany.");
    public BukkitNotice badTime = new BukkitNotice(MinecraftNoticeType.CHAT, "&cPodany czas jest nieprawidlowy.");


}
