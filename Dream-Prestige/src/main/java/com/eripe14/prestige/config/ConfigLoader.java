package com.eripe14.prestige.config;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import lombok.RequiredArgsConstructor;
import xyz.ravis96.dreambasis.bukkit.config.SerdesDream;
import xyz.ravis96.dreambasis.bukkit.config.serdes.NoticeSerializer;

import java.io.File;

@RequiredArgsConstructor
public class ConfigLoader {

    private final File dataFile;

    public PluginConfig loadPluginConfig() {
        return ConfigManager.create(PluginConfig.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit(), new SerdesDream());
            it.withBindFile(this.dataFile);
            it.saveDefaults();
            it.load(true);
        });
    }

    public CommandsConfig loadCommandsConfig() {
        return ConfigManager.create(CommandsConfig.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit(), new SerdesDream());
            it.withBindFile(this.dataFile);
            it.saveDefaults();
            it.load(true);
        });
    }

    public MessageConfig loadMessageConfig() {
        return ConfigManager.create(MessageConfig.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
            it.withBindFile(this.dataFile);
            it.withSerdesPack(registry -> registry.register(new NoticeSerializer()));
            it.saveDefaults();
            it.load(true);
        });
    }
}
