package cc.dreamcode.fortune.config;

import cc.dreamcode.fortune.fortune.Fortune;
import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import cc.dreamcode.platform.persistence.StorageConfig;
import cc.dreamcode.utilities.builder.ListBuilder;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration(
        child = "config.yml"
)
@Header("## Dream-Fortune (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {
    @Comment("Debug pokazuje dodatkowe informacje do konsoli. Lepiej wylaczyc. :P")
    public boolean debug = true;

    @Comment("Ile ma dropic maksymalnie dodatkowo itemow przy wybranych poziomach")
    public Map<Integer, Integer> levelMaxDrops = new HashMap<Integer, Integer>() {{
        put(1, 3);
        put(2, 5);
        put(4, 7);
        put(5, 9);
        put(6, 12);
        put(7, 15);
        put(8, 17);
        put(9, 20);
    }};

    @Comment("Lista fortun:")
    public List<Fortune> fortuneList = new ListBuilder<Fortune>().build();
}
