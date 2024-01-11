package xyz.ravis96.dreamfreeze.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import lombok.Getter;
import xyz.ravis96.dreamfreeze.config.subconfig.*;

@Getter
@Header("## Dream-Freeze (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {

    @Comment("Debug pokazuje dodatkowe informacje do konsoli. Lepiej wylaczyc. :P")
    public boolean debug = true;

    @Comment("Ustaw funkcje pluginu:")
    @Getter private CommandsConfig commandsConfig = new CommandsConfig();
    @Getter private FreezeConfig freezeConfig = new FreezeConfig();

}
