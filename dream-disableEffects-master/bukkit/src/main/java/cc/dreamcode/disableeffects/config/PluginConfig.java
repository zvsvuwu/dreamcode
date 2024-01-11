package cc.dreamcode.disableeffects.config;

import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import cc.dreamcode.utilities.builder.ListBuilder;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

import java.util.List;

@Configuration(
        child = "config.yml"
)
@Header("## Dream-DisableEffects (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {
    @Comment("Debug pokazuje dodatkowe informacje do konsoli. Lepiej wylaczyc. :P")
    public boolean debug = true;

    @Comment("W jakich swiatach efekty maja byc wylaczone.")
    public List<String> disabledWorlds = new ListBuilder<String>().add("world").build();

    @Comment("Na jakich regionach efekty maja byc wylaczone.")
    public List<String> disabledRegions = new ListBuilder<String>().add("spawn").build();



}
