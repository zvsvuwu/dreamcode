package pl.virtual.gamma.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import pl.virtual.gamma.config.other.Type;

@Header("## config.yml ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class ConfigPlugin extends OkaeriConfig {
    @Comment({"","gamma command"})
    public Type messageCommandOn = new Type("CHAT", "&aGamma zostala wlaczona");
    public Type messageCommandOff = new Type("CHAT", "&cGamma zostala wylaczona");
    @Comment({"","gammareload command"})
    public Type messageReload = new Type("CHAT", "&aConfig zostal przeladowany");
}


