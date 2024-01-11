package cc.dreamcode.codekeys.config;

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
        @Header("## Dream-CodeKeys (Message-Config) ##"),
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

    public BukkitNotice help = new BukkitNotice(MinecraftNoticeType.CHAT, "&c/akod create [kod] [action] &7- &ftworzy kod.",
            "&c/akod delete [kod] &7- &fusuwa kod.",
            "&c/akod list &7- &fwyswietla wszystkie kody.",
            "&c/akod reset all &7- &fresetuje przypisane kody wszystkim graczom.",
            "&c/akod reset [player] &7- &fresetuje przypisany kod podanemu graczowi."
            );
    public BukkitNotice codeUsing = new BukkitNotice(MinecraftNoticeType.CHAT, "&cUzyles juz kod!");
    public BukkitNotice codeExists = new BukkitNotice(MinecraftNoticeType.CHAT, "&cTaki kod juz istnieje!");
    public BukkitNotice codeDoesNotExists = new BukkitNotice(MinecraftNoticeType.CHAT, "&CTaki kod nie istnieje!");
    public BukkitNotice codeUseSuccess = new BukkitNotice(MinecraftNoticeType.CHAT, "&aUzyles kodu: &6{code}");
    public BukkitNotice codeCreation = new BukkitNotice(MinecraftNoticeType.CHAT, "&aKod &6{code} &azostal stworzony");
    public BukkitNotice codeDelete = new BukkitNotice(MinecraftNoticeType.CHAT, "&aKod &6{code} &azostal usuniety!");
    public BukkitNotice codeList = new BukkitNotice(MinecraftNoticeType.CHAT, "&bLista kodów:");
    public BukkitNotice resetAllPlayers = new BukkitNotice(MinecraftNoticeType.CHAT, "&aKody dla wszystkich graczy zostaly zrestartowane!");
    public BukkitNotice playerHasNoCode = new BukkitNotice(MinecraftNoticeType.CHAT, "&cGracz nie ma ustawionego kodu!");
    public BukkitNotice resetPlayer = new BukkitNotice(MinecraftNoticeType.CHAT, "&aKod gracza zostal zrestartowany!");
    public BukkitNotice reload = new BukkitNotice(MinecraftNoticeType.CHAT, "&aPliki konfiguracyjne zostaly przeladowane!");



}
