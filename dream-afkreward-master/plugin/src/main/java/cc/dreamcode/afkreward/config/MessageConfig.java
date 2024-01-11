package cc.dreamcode.afkreward.config;

import cc.dreamcode.notice.minecraft.MinecraftNoticeType;
import cc.dreamcode.notice.minecraft.bukkit.BukkitNotice;
import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.Headers;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

import java.util.Map;

@Configuration(child = "message.yml")
@Headers({
        @Header("## Dream-AfkReward (Message-Config) ##"),
        @Header("Dostepne type: (CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE, SIDEBAR)")
})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessageConfig extends OkaeriConfig {

    @Comment("Lista wiadomosci wysylanych graczowi, gdy osiagnie dany czas afk (sekunda, wiadomosc)")
    public Map<Integer, BukkitNotice> announcements = new MapBuilder<Integer, BukkitNotice>()
            .put(60, new BukkitNotice(MinecraftNoticeType.CHAT, "&aOtrzymales nagrode za bycie afk przez &eminute"))
            .put(120, new BukkitNotice(MinecraftNoticeType.CHAT, "Otrzymales nagrode za bycia afk przez &e2 minuty"))
            .build();
}
