package cc.dreamcode.generator.config;

import cc.dreamcode.generator.PluginLogger;
import cc.dreamcode.generator.PluginMain;
import cc.dreamcode.generator.features.generator.template.GeneratorTemplateSerdes;
import cc.dreamcode.generator.features.notice.NoticeSerdes;
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
                registry.register(new GeneratorTemplateSerdes());
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
