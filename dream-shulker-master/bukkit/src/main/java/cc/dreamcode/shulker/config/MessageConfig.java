package cc.dreamcode.shulker.config;

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
        @Header("## Dream-Shulker (Message-Config) ##"),
        @Header("Dostepne type: (DO_NOT_SEND, CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE)")
})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessageConfig extends OkaeriConfig {

    public BukkitNotice hasOpenedShulker = new BukkitNotice(MinecraftNoticeType.CHAT, "&cJestes w trakcie otwierania shulkera!");
    public BukkitNotice openShulker = new BukkitNotice(MinecraftNoticeType.CHAT, "&aOtworzono shulkera!");
    public BukkitNotice closeShulker = new BukkitNotice(MinecraftNoticeType.CHAT, "&cZamknieto shulkera!");
    public BukkitNotice shulkerInOffHand = new BukkitNotice(MinecraftNoticeType.CHAT, "&cAby otworzyc shulkera, nalezy trzymac go w glownej rece!");
    public BukkitNotice shulkerCooldown = new BukkitNotice(MinecraftNoticeType.CHAT, "&cPrzed nastepnym uzyciem, poczekaj: &7{time}");

}
