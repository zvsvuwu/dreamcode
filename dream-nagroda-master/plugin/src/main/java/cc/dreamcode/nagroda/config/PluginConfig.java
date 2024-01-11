package cc.dreamcode.nagroda.config;

import cc.dreamcode.nagroda.config.annotations.Configuration;
import cc.dreamcode.nagroda.config.storage.StorageConfig;
import cc.dreamcode.nagroda.content.NagrodaConfig;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

@Configuration(child = "config.yml")
@Header("## Dream-Nagroda (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {

    @Comment("Uzupelnij ponizsze menu danymi.")
    public StorageConfig storageConfig = new StorageConfig();

    public NagrodaConfig nagrodaConfig = new NagrodaConfig();
}
