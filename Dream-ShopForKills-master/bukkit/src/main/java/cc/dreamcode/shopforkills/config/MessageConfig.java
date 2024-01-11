package cc.dreamcode.shopforkills.config;

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
        @Header("## Dream-ShopForKills (Message-Config) ##"),
        @Header("Dostepne type: (DO_NOT_SEND, CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE)")
})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessageConfig extends OkaeriConfig {

    public BukkitNotice usage = new BukkitNotice(MinecraftNoticeType.CHAT, "&7Poprawne uzycie: &c{usage}");
    public BukkitNotice noPermission = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Nie posiadasz uprawnien.");
    public BukkitNotice notPlayer = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Nie jestes graczem.");
    public BukkitNotice notNumber = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Podana liczba &cnie jest cyfra.");
    public BukkitNotice notHaveKills = new BukkitNotice(MinecraftNoticeType.CHAT, "&cNie posiadasz tyle killi do wydania!");
    public BukkitNotice buyItem = new BukkitNotice(MinecraftNoticeType.CHAT, "&aPrzedmiot zostal zakupiony!");
    public BukkitNotice notItem = new BukkitNotice(MinecraftNoticeType.CHAT, "&cNie posiadasz przedmiotu w rece!");
    public BukkitNotice itemAdd = new BukkitNotice(MinecraftNoticeType.CHAT, "&aPrzedmiot zostal dodany do sklepu!");
    public BukkitNotice offerNotNull = new BukkitNotice(MinecraftNoticeType.CHAT, "&cIstnieje juz oferta w tym slocie!");
    public BukkitNotice offerNull = new BukkitNotice(MinecraftNoticeType.CHAT, "&cOferta w tym slocie nie istnieje!");
    public BukkitNotice offerDelete = new BukkitNotice(MinecraftNoticeType.CHAT, "&cOferta zostala usunieta");
    public BukkitNotice reloaded = new BukkitNotice(MinecraftNoticeType.CHAT, "&aPrzeladowano! &7({time})");
    public BukkitNotice reloadError = new BukkitNotice(MinecraftNoticeType.CHAT, "&cZnaleziono problem w konfiguracji: &6{error}");


}
