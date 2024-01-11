package cc.dreamcode.itemchange.config;

import cc.dreamcode.notice.minecraft.MinecraftNoticeType;
import cc.dreamcode.notice.minecraft.bukkit.BukkitNotice;
import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.Headers;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

@Configuration(child = "message.yml")
@Headers({
        @Header("## Dream-ItemChange (Message-Config) ##"),
        @Header("Dostepne type: (CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE)")
})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessageConfig extends OkaeriConfig {

    public BukkitNotice noPermission = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Nie posiadasz uprawnien.");
    public BukkitNotice noPlayer = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Podanego gracza &cnie znaleziono.");

    public BukkitNotice changeSuccess = new BukkitNotice(MinecraftNoticeType.CHAT, "&aZmieniono twoj przedmiot na diamentowy");
    public BukkitNotice changeFailure = new BukkitNotice(MinecraftNoticeType.CHAT, "&cNie trzymasz w rece odpowiedniego przedmiotu");
    public BukkitNotice insufficientFunds = new BukkitNotice(MinecraftNoticeType.CHAT, "&cNie stac cie na to. &a($500)");
}
