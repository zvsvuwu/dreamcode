package cc.dreamcode.generator;

import cc.dreamcode.generator.component.ComponentHandler;
import cc.dreamcode.generator.config.ConfigLoader;
import cc.dreamcode.generator.config.MessageConfig;
import cc.dreamcode.generator.config.PluginConfig;
import cc.dreamcode.generator.exception.PluginRuntimeException;
import cc.dreamcode.generator.features.generator.GeneratorActionHandler;
import cc.dreamcode.generator.features.generator.persistence.GeneratorRepository;
import cc.dreamcode.generator.features.generator.persistence.GeneratorRepositoryFactory;
import cc.dreamcode.generator.nms.api.NmsAccessor;
import cc.dreamcode.generator.nms.v1_10_R1.V1_10_R1_NmsAccessor;
import cc.dreamcode.generator.nms.v1_11_R1.V1_11_R1_NmsAccessor;
import cc.dreamcode.generator.nms.v1_12_R1.V1_12_R1_NmsAccessor;
import cc.dreamcode.generator.nms.v1_13_R2.V1_13_R2_NmsAccessor;
import cc.dreamcode.generator.nms.v1_14_R1.V1_14_R1_NmsAccessor;
import cc.dreamcode.generator.nms.v1_15_R1.V1_15_R1_NmsAccessor;
import cc.dreamcode.generator.nms.v1_16_R3.V1_16_R3_NmsAccessor;
import cc.dreamcode.generator.nms.v1_17_R1.V1_17_R1_NmsAccessor;
import cc.dreamcode.generator.nms.v1_18_R2.V1_18_R2_NmsAccessor;
import cc.dreamcode.generator.nms.v1_19_R1.V1_19_R1_NmsAccessor;
import cc.dreamcode.generator.nms.v1_8_R3.V1_8_R3_NmsAccessor;
import cc.dreamcode.generator.nms.v1_9_R2.V1_9_R2_NmsAccessor;
import cc.dreamcode.generator.persistence.PersistenceHandler;
import cc.dreamcode.generator.persistence.PersistenceService;
import cc.dreamcode.generator.persistence.RepositoryLoader;
import com.cryptomorin.xseries.ReflectionUtils;
import eu.okaeri.injector.Injector;
import eu.okaeri.injector.OkaeriInjector;
import eu.okaeri.tasker.bukkit.BukkitTasker;
import eu.okaeri.tasker.core.Tasker;
import lombok.Getter;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.Website;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

import java.io.File;
import java.util.stream.Stream;

@Plugin(name = "Dream-Generator", version = "1.0-SNAPSHOT")
@Author("SnoxMox & Ravis96")
@Description("Generator plugin for DreamCode.")
@Website("DreamCode - https://discord.gg/dreamcode")
@ApiVersion(ApiVersion.Target.v1_13)

public final class PluginMain extends PluginBootLoader {

    @Getter private static PluginMain pluginMain;
    @Getter private static PluginLogger pluginLogger;

    @Getter private PluginConfig pluginConfig;
    @Getter private MessageConfig messageConfig;

    @Getter private PersistenceService persistenceService;
    @Getter private Injector injector;
    @Getter private Tasker tasker;
    @Getter private NmsAccessor nmsAccessor;
    @Getter private ComponentHandler componentHandler;
    @Getter private GeneratorRepository generatorRepository;

    @Override
    public void load() {
        pluginMain = this;
        pluginLogger = new PluginLogger(pluginMain.getLogger());

        this.injector = OkaeriInjector.create();
        this.injector.registerInjectable(this);

        this.tasker = BukkitTasker.newPool(this);
        this.injector.registerInjectable(this.tasker);

        this.nmsAccessor = this.hookNmsAccessor();
        this.injector.registerInjectable(this.nmsAccessor);

        this.componentHandler = new ComponentHandler(this);
        this.injector.registerInjectable(this.componentHandler);

        try {
            this.messageConfig = new ConfigLoader(new File(this.getDataFolder(), "message.yml")).loadMessageConfig();
            this.injector.registerInjectable(this.messageConfig);

            this.pluginConfig = new ConfigLoader(new File(this.getDataFolder(), "config.yml")).loadPluginConfig();
            this.injector.registerInjectable(this.pluginConfig);
        }
        catch (Exception e) {
            this.getPluginDisabled().set(true);
            throw new PluginRuntimeException("An error was caught when config files are loading...", e, this);
        }
    }

    @Override
    public void start() {
        if (!PluginFactory.checkPlugin(this.getPluginDisabled(), this.getDescription())) {
            return;
        }

        try {
            // connect database
            this.persistenceService = new PersistenceService(
                    this,
                    new PersistenceHandler()
            );
            this.persistenceService.initializePersistence();

            this.generatorRepository = new GeneratorRepositoryFactory(
                    this,
                    this.persistenceService.getPersistenceHandler()
            ).getRepositoryService();
            this.injector.registerInjectable(this.generatorRepository);

            // load database to cache
            this.persistenceService.getPersistenceHandler().getRepositoryLoaderList()
                    .forEach(RepositoryLoader::load);


            // register other services

        }
        catch (Exception e) {
            this.getPluginDisabled().set(true);
            throw new PluginRuntimeException("An error was caught when services are loading...", e, this);
        }

        // register components (commands, listener, task or else (need implement))
        Stream.of(
                new GeneratorActionHandler()
        ).forEach(ob -> this.componentHandler.registerComponent(ob));

        PluginMain.getPluginLogger().info(String.format("Aktywna wersja: v%s - Autor: %s",
                getDescription().getVersion(),
                getDescription().getAuthors()));
    }

    @Override
    public void stop() {
        // save cache to database
        this.persistenceService.savePersistence(true,
                this.persistenceService.getPersistenceHandler().getRepositoryLoaderList());

        // features need to be call by stop server

        PluginMain.getPluginLogger().info(String.format("Aktywna wersja: v%s - Autor: %s",
                getDescription().getVersion(),
                getDescription().getAuthors()));
    }

    public NmsAccessor hookNmsAccessor() {
        PluginMain.getPluginLogger().info(
                new PluginLogger.Loader()
                        .type("Podlaczam wersje minecraft")
                        .name(ReflectionUtils.VERSION)
                        .build()
        );

        switch (ReflectionUtils.VER) {
            case 8: {
                return new V1_8_R3_NmsAccessor();
            }
            case 9: {
                return new V1_9_R2_NmsAccessor();
            }
            case 10: {
                return new V1_10_R1_NmsAccessor();
            }
            case 11: {
                return new V1_11_R1_NmsAccessor();
            }
            case 12: {
                return new V1_12_R1_NmsAccessor();
            }
            case 13: {
                return new V1_13_R2_NmsAccessor();
            }
            case 14: {
                return new V1_14_R1_NmsAccessor();
            }
            case 15: {
                return new V1_15_R1_NmsAccessor();
            }
            case 16: {
                return new V1_16_R3_NmsAccessor();
            }
            case 17: {
                return new V1_17_R1_NmsAccessor();
            }
            case 18: {
                return new V1_18_R2_NmsAccessor();
            }
            case 19: {
                return new V1_19_R1_NmsAccessor();
            }
            default: {
                throw new PluginRuntimeException("Plugin doesn't support this server version, change to 1.8 - 1.19 (latest subversion).");
            }
        }
    }
}
