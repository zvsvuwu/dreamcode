package cc.dreamcode.spawn.config;

import cc.dreamcode.notice.minecraft.MinecraftNoticeType;
import cc.dreamcode.notice.minecraft.bukkit.BukkitNotice;
import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;

import java.util.Collections;
import java.util.List;

@Configuration(
        child = "message.yml"
)
@Headers({
        @Header("## Dream-Spawn (Message-Config) ##"),
        @Header("Dostepne type: (DO_NOT_SEND, CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE)")
})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessageConfig extends OkaeriConfig {

    @Comment("Wiadomość, która ma być wyświetlana podczas teleportacji.")
    public List<BukkitNotice> teleportMessage = Collections.singletonList(
            new BukkitNotice(MinecraftNoticeType.TITLE_SUBTITLE, "&aTrwa teleportacja... %NEWLINE% &f{time}")
    );

    @Comment("Wiadomość, która ma zostać wysłana gdy gracz zostanie przeteleportowany na spawna.")
    public BukkitNotice successMessage = new BukkitNotice(MinecraftNoticeType.SUBTITLE, "&aTwoja teleportacja dobiegła końca!");

    @Comment("Wiadomość, która jest wysyłana gdy gracz się ruszy podczas teleportacji.")
    public BukkitNotice moveMessage = new BukkitNotice(MinecraftNoticeType.SUBTITLE, "&cTwoja teleportacja została przerwana!");

    public BukkitNotice usage = new BukkitNotice(MinecraftNoticeType.CHAT, "&7Poprawne uzycie: &c{usage}");
    public BukkitNotice noPermission = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Nie posiadasz uprawnien.");
    public BukkitNotice notPlayer = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Nie jestes graczem.");
    public BukkitNotice noPlayer = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Ten gracz jest offline!");
    public BukkitNotice setSpawn = new BukkitNotice(MinecraftNoticeType.CHAT, "&aUstawiono lokalizacje spawna!");
    public BukkitNotice alreadyTeleporting = new BukkitNotice(MinecraftNoticeType.CHAT, "&cJesteś już podczas teleportacji!");
    public BukkitNotice configReloaded = new BukkitNotice(MinecraftNoticeType.CHAT, "&aPrzeładowano plik konfiguracyjny!");
}
