package pl.virtual.death.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;

import java.util.Arrays;
import java.util.List;

@Header({"## dcDeath (config.yml) ##"})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class ConfigPlugin extends OkaeriConfig {
    public boolean wolrdguardIntegration = false;
    public List<String> onlyWorldGuardRegion = Arrays.asList("afk");
}

