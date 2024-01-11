package cc.dreamcode.ccl.config;

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
        @Header("## Dream-CommandsCooldown (Message-Config) ##"),
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

    public BukkitNotice commandHelp = new BukkitNotice(MinecraftNoticeType.CHAT, "&bDream-CommandsCooldown",
            "&6/commandcooldown reload &8- &7przladowanie configu.",
            "&6/commandcooldown all <time> &8- &7ustawia cooldown na wszystkie komendy.",
            "&6/commandcooldown add <command> <time> &8- &7ustawia cooldown na dana komende.",
            "&6/commandcooldown remove <command> &8- &7usuwa cooldown na podana komende.",
            "&6/commandcooldown list &8- &7lista colldownow."
    );

    public BukkitNotice badTime = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Podany czas jest nieprawidlowy.");
    public BukkitNotice allCommandsCool_downSet = new BukkitNotice(MinecraftNoticeType.CHAT, "&aCooldown na wszystkie komendy zostal ustawiony na &6{time}.");
    public BukkitNotice cool_downAdd = new BukkitNotice(MinecraftNoticeType.CHAT, "&aCooldown na komende: &6{command} &azostal ustawiony na: &6{time}!");
    public BukkitNotice cool_downRemove = new BukkitNotice(MinecraftNoticeType.CHAT, "&aCooldown na komende: &6{command} &azostal usuniety!");
    public BukkitNotice cool_downDoesNotExists = new BukkitNotice(MinecraftNoticeType.CHAT, "&cCooldown na taka komende nie istnieje!");
    public BukkitNotice cool_downList = new BukkitNotice(MinecraftNoticeType.CHAT, "&a/{command} &8- &6{time}");
    public BukkitNotice youHaveCool_down = new BukkitNotice(MinecraftNoticeType.CHAT, "&cNastepny raz bedziesz mogl uzyc tej komendy za: &7{time}");
    public BukkitNotice globalCool_down = new BukkitNotice(MinecraftNoticeType.CHAT, "&cNastepny raz bedziesz mogl uzywac komend za: &7{time}");
    public BukkitNotice reload = new BukkitNotice(MinecraftNoticeType.CHAT, "&aPlugin zostal przeladowany!");


}
