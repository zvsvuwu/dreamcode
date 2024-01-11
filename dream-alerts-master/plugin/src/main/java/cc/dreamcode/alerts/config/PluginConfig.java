package cc.dreamcode.alerts.config;

import cc.dreamcode.alerts.config.annotations.Configuration;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;

@Configuration(child = "config.yml")
@Header("## Dream-Alerts (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {

    @Comment("Title, ktory bedzie wyswietlal sie przy uzyciu typu alertu: title_subtitle")
    public String alertTitle = "&8[&4&lALERT&8]";

    @Comment("Przez jaki czas powinien wyswietlac sie graczom alert? (nie dziala na chat) (w sekundach)")
    public int alertTime = 3;
}
