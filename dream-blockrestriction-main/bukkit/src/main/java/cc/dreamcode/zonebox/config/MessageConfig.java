package cc.dreamcode.zonebox.config;

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
        @Header("## Dream-ZoneBox (Message-Config) ##"),
        @Header("Dostepne type: (CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE)")
})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessageConfig extends OkaeriConfig {

    public BukkitNotice usage = new BukkitNotice(MinecraftNoticeType.CHAT, "&7Poprawne uzycie: &c{usage}");

    public BukkitNotice noPermission = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Nie posiadasz uprawnien.");
    public BukkitNotice noPlayer = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Podanego gracza &cnie znaleziono.");

    public BukkitNotice cantPlaceBlock = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Nie mozesz postawic tego bloku!");
    public BukkitNotice cantBreakBlock = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Nie mozesz zniszczyc tego bloku!");

    public BukkitNotice blockDoesNotExist = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Taki blok nie istnieje!");
    public BukkitNotice blockAlreadyAdded = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Ten blok juz znajduje sie na liscie!");
    public BukkitNotice blockNotAdded = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Ten blok nie znajduje sie na liscie!");
    public BukkitNotice blockSuccessfullyAdded = new BukkitNotice(MinecraftNoticeType.CHAT, "&aPomyslnie dodales blok: &e{block}!");
    public BukkitNotice blockSuccessfullyRemoved = new BukkitNotice(MinecraftNoticeType.CHAT, "&aPomyslnie usunales blok: &e{block}!");
    public BukkitNotice addedBlocksList = new BukkitNotice(MinecraftNoticeType.CHAT, "&aLista dodanych blokow: &e{list}!");


    public BukkitNotice reloaded = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Konfiguracja pluginu zostala pomyslnie zaladowana!");
    public BukkitNotice reloadError = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Wystapil blad podczas wczytywania konfiguracji pluginu!");

}
