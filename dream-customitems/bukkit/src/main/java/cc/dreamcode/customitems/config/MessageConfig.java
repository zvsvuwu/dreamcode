package cc.dreamcode.customitems.config;

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
        @Header("## Dream-CustomItems (Message-Config) ##"),
        @Header("Dostepne type: (DO_NOT_SEND, CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE)")
})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessageConfig extends OkaeriConfig {

    public BukkitNotice noPermission = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Nie posiadasz uprawnien.");
    public BukkitNotice notPlayer = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Nie jestes graczem.");
    public BukkitNotice offlinePlayer = new BukkitNotice(MinecraftNoticeType.CHAT, "&cTen gracz jest offline!");
    public BukkitNotice usage = new BukkitNotice(MinecraftNoticeType.CHAT, "&aPoprawne użycie: &6{usage}");
    public BukkitNotice reload = new BukkitNotice(MinecraftNoticeType.CHAT, "&aPrzeładowano pliki konfiguracyjne!");
    public BukkitNotice cooldown = new BukkitNotice(MinecraftNoticeType.CHAT, "&6Ten przedmiot możesz dopiero użyć za &e{time}&6!");
    public BukkitNotice boneStun = new BukkitNotice(MinecraftNoticeType.CHAT, "&aOgłuszono gracza {player}!");
}
