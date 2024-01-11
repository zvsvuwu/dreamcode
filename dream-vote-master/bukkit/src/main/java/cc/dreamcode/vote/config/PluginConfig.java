package cc.dreamcode.vote.config;

import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import cc.dreamcode.platform.bukkit.persistence.StorageConfig;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

@Configuration(
        child = "config.yml"
)
@Header("## Dream-Vote (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {
    @Comment("Uzupelnij ponizsze menu danymi.")
    public StorageConfig storageConfig = new StorageConfig();

    @Comment("Co ile ma byc tworzone glosowanie? (w sekundach)")
    public int voteCreateTime = 300;

    @Comment("Ile ma trwac glosowanie? (w sekundach)")
    public int voteTime = 60;

    @Comment("Komenda ktora plugin ma wykonac po skonczeniu glosowania, gdy zakonczy sie ono przewaga glosow na tak (bez \"/\"")
    public String voteEndCommand = "time set day";
}
