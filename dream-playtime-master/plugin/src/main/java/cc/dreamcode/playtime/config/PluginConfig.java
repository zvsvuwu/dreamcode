 package cc.dreamcode.playtime.config;

import cc.dreamcode.platform.bukkit.component.annotation.Configuration;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;

@Configuration(child = "config.yml")
@Header("## Dream-PlayTime (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {

    @Comment("Czy wyswietlac graczowi gui po wpisaniu komendy /czas")
    @Comment("Jezeli false gracz dostanie wiadomosc z dana informacja")
    public boolean useGui = true;

    @Comment("Konfiguracja menu czasu")
    public PlayTimeMenuConfig playTimeMenuConfig = new PlayTimeMenuConfig();
}
