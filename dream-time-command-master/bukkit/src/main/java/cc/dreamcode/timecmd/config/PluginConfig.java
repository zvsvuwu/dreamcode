package cc.dreamcode.timecmd.config;

import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

import java.util.Map;

@Configuration(
        child = "config.yml"
)
@Header("## Dream-TimeCommand (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {

    public String bypassPermission = "dc.timecmd.bypass";

    @Comment("Placeholder od czasu: %dreamtimecmd_time%")
    public Map<String, String> timeCommands = new MapBuilder<String, String>()
            .put("help", "10m")
            .put("ver", "20h")
            .put("version", "100h")
            .build();

}
