package com.eripe14.prestige.config;

import com.eripe14.prestige.config.subconfig.PrestigeConfig;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import lombok.Getter;

@Getter
@Header("## DreamCode-Prestiże (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {

    private final PrestigeConfig prestigeConfig = new PrestigeConfig( );

}
