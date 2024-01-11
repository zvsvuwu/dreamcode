package cc.dreamcode.killreward.config;

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
        @Header("## Dream-KillReward (Message-Config) ##"),
        @Header("Dostepne type: (DO_NOT_SEND, CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE)")
})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessageConfig extends OkaeriConfig {

    public BukkitNotice usage = new BukkitNotice(MinecraftNoticeType.CHAT, "&7Poprawne uzycie: &c{usage}");
    public BukkitNotice noPermission = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Nie posiadasz uprawnien.");
    public BukkitNotice notPlayer = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Nie jestes graczem.");
    public BukkitNotice offlinePlayer = new BukkitNotice(MinecraftNoticeType.CHAT, "&cTen gracz jest offline!");
    public BukkitNotice notNumber = new BukkitNotice(MinecraftNoticeType.CHAT, "&cJakiś argument musi byc liczbą!");
    public BukkitNotice reload = new BukkitNotice(MinecraftNoticeType.CHAT, "&aPrzeładowano pliki konfiguracyjne!");
    public BukkitNotice reward = new BukkitNotice(MinecraftNoticeType.CHAT, "&6Otrzymano nagrodę za zabicie gracza &e{player}&6!");
    public BukkitNotice entityReward = new BukkitNotice(MinecraftNoticeType.CHAT, "&aOtrzymano nagrode za zabicie moba {entity}!");
    public BukkitNotice kill = new BukkitNotice(MinecraftNoticeType.TITLE, "&cZabiłeś/aś gracza &4{player}&c!");
    public BukkitNotice killBroadcast = new BukkitNotice(MinecraftNoticeType.CHAT, "&cGracz &4{killer} &czabil gracza &4{player}&c!");
    public BukkitNotice cooldown = new BukkitNotice(
            MinecraftNoticeType.CHAT,
            "&cOtrzymałeś już nagrodę za zabicie tego gracza! &cPoczekaj jeszcze &4{time}&c, aby otrzymać nagrodę za zabicie tego gracza!"
    );
    public BukkitNotice boostAdd = new BukkitNotice(MinecraftNoticeType.CHAT, "&aDodano boost graczowi &6{player}&a z szansą &6{chance} &ana czas &6{duration}&a.");
    public BukkitNotice boostAddAll = new BukkitNotice(MinecraftNoticeType.CHAT, "&aDodano każdemu graczowi boost z szansą &6{chance}&a na czas &6{duration}&a.");
}
