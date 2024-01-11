package cc.dreamcode.ccl.config;

import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import cc.dreamcode.platform.persistence.StorageConfig;
import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration(
        child = "config.yml"
)
@Header("## Dream-CommandsCooldown (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {
    @Comment("Debug pokazuje dodatkowe informacje do konsoli. Lepiej wylaczyc. :P")
    public boolean debug = true;

    @Comment("Uzupelnij ponizsze menu danymi.")
    public StorageConfig storageConfig = new StorageConfig("dreamccl");

    public String allCommandsCool_down = "3s";

    public Map<String, String> cool_downs = new MapBuilder<String, String>()
            .put("spawnpoint", "3m")
            .build();

    public void fixCommands(boolean save) {
        cool_downs = cool_downs.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().toLowerCase().replace("/", ""),
                        Map.Entry::getValue
                ));
        if (save) {
            save();
        }
    }
}
