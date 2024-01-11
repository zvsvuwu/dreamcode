package cc.dreamcode.dropsmp.config;

import cc.dreamcode.dropsmp.PluginLogger;
import cc.dreamcode.dropsmp.PluginMain;
import cc.dreamcode.dropsmp.features.items.ItemSerdes;
import cc.dreamcode.dropsmp.features.menu.MenuSerdes;
import cc.dreamcode.dropsmp.features.notice.NoticeSerdes;
import cc.dreamcode.dropsmp.features.emblem.EmblemSerdes;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import lombok.RequiredArgsConstructor;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ConfigLoader {

    private final File dataFile;

    public PluginConfig loadPluginConfig() {
        long start = System.currentTimeMillis();
        PluginConfig pluginConfig = ConfigManager.create(PluginConfig.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
            it.withBindFile(this.dataFile);
            it.withSerdesPack(registry -> {
                registry.register(new NoticeSerdes());
                registry.register(new MenuSerdes());
                registry.register(new EmblemSerdes());
                registry.register(new ItemSerdes());
            });
            it.saveDefaults();
            it.load(true);
        });

        long took = System.currentTimeMillis() - start;
        PluginMain.getPluginLogger().info(
                new PluginLogger.Loader()
                        .type("Poprawnie zaladowano config")
                        .name(pluginConfig.getClass().getSimpleName())
                        .took(took)
                        .meta("path", pluginConfig.getBindFile())
                        .meta("sub-configs", Arrays.stream(PluginConfig.class.getDeclaredFields())
                                .map(Field::getName)
                                .filter(name -> name.contains("Config"))
                                .collect(Collectors.toList()))
                        .build()
        );

        return pluginConfig;
    }

    public MessageConfig loadMessageConfig() {
        long start = System.currentTimeMillis();
        MessageConfig messageConfig = ConfigManager.create(MessageConfig.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
            it.withBindFile(this.dataFile);
            it.withSerdesPack(registry -> registry.register(new NoticeSerdes()));
            it.saveDefaults();
            it.load(true);
        });

        long took = System.currentTimeMillis() - start;
        PluginMain.getPluginLogger().info(
                new PluginLogger.Loader()
                        .type("Poprawnie zaladowano messages")
                        .name(messageConfig.getClass().getSimpleName())
                        .took(took)
                        .meta("path", messageConfig.getBindFile())
                        .build()
        );

        return messageConfig;
    }
}
