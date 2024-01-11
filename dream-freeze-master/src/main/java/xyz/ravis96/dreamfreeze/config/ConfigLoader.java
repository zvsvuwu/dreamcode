package xyz.ravis96.dreamfreeze.config;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import lombok.RequiredArgsConstructor;
import xyz.ravis96.dreamfreeze.config.serdes.*;

import java.io.File;

@RequiredArgsConstructor
public class ConfigLoader {
    private final File dataFile;

    public PluginConfig loadPluginConfig() {
        return ConfigManager.create(PluginConfig.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
            it.withBindFile(this.dataFile);
            it.withSerdesPack(registry -> {
                // Class
                registry.register(new FunnyCommandSerializer());
                registry.register(new NoticeSerializer());
                registry.register(new PairSerializer());
                registry.register(new TitleSerializer());
                // Objects
            });
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
