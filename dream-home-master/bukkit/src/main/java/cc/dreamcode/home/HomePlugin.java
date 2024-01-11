package cc.dreamcode.home;

import cc.dreamcode.command.bukkit.BukkitCommandProvider;
import cc.dreamcode.home.command.HomeCommand;
import cc.dreamcode.home.controller.HomeController;
import cc.dreamcode.home.controller.UserController;
import cc.dreamcode.home.manager.HomeManager;
import cc.dreamcode.home.manager.UserManager;
import cc.dreamcode.home.rawlocation.RawLocationSerdes;
import cc.dreamcode.home.runnable.HomeRunnable;
import cc.dreamcode.menu.bukkit.BukkitMenuProvider;
import cc.dreamcode.menu.serdes.bukkit.okaeri.MenuBuilderSerdes;
import cc.dreamcode.notice.bukkit.BukkitNoticeProvider;
import cc.dreamcode.notice.bukkit.okaeri_serdes.BukkitNoticeSerdes;
import cc.dreamcode.platform.DreamVersion;
import cc.dreamcode.platform.bukkit.DreamBukkitPlatform;
import cc.dreamcode.platform.bukkit.component.CommandComponentResolver;
import cc.dreamcode.platform.bukkit.component.ConfigurationComponentResolver;
import cc.dreamcode.platform.bukkit.component.DocumentPersistenceComponentResolver;
import cc.dreamcode.platform.bukkit.component.DocumentRepositoryComponentResolver;
import cc.dreamcode.platform.bukkit.component.ListenerComponentResolver;
import cc.dreamcode.platform.bukkit.component.RunnableComponentResolver;
import cc.dreamcode.platform.component.ComponentManager;
import cc.dreamcode.home.config.MessageConfig;
import cc.dreamcode.home.config.PluginConfig;
import cc.dreamcode.home.user.UserRepository;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.persistence.document.DocumentPersistence;
import eu.okaeri.tasker.bukkit.BukkitTasker;
import lombok.Getter;
import lombok.NonNull;

public final class HomePlugin extends DreamBukkitPlatform {

    @Getter private static HomePlugin homePlugin;

    @Override
    public void load(@NonNull ComponentManager componentManager) {
        homePlugin = this;
    }

    @Override
    public void enable(@NonNull ComponentManager componentManager) {
        this.registerInjectable(BukkitTasker.newPool(this));
        this.registerInjectable(BukkitMenuProvider.create(this));
        this.registerInjectable(BukkitNoticeProvider.create(this));
        this.registerInjectable(BukkitCommandProvider.create(this, this.getInjector()));

        componentManager.registerResolver(CommandComponentResolver.class);
        componentManager.registerResolver(ListenerComponentResolver.class);
        componentManager.registerResolver(RunnableComponentResolver.class);

        componentManager.registerResolver(ConfigurationComponentResolver.class);
        componentManager.registerComponent(MessageConfig.class, messageConfig -> {
            this.getInject(BukkitCommandProvider.class).ifPresent(bukkitCommandProvider -> {
                bukkitCommandProvider.setNoPermissionMessage(messageConfig.noPermission);
                bukkitCommandProvider.setNoPlayerMessage(messageConfig.noPlayer);
            });
        });
        componentManager.registerComponent(PluginConfig.class, pluginConfig -> {
            // register persistence + repositories
            this.registerInjectable(pluginConfig.storageConfig);

            componentManager.registerResolver(DocumentPersistenceComponentResolver.class);
            componentManager.registerResolver(DocumentRepositoryComponentResolver.class);

            componentManager.registerComponent(DocumentPersistence.class);
            componentManager.registerComponent(UserRepository.class);
        });
        componentManager.registerComponent(UserManager.class, userManager -> userManager.syncUserInMemoryDatabase(e ->
                this.getDreamLogger().error("Wystapil problem podczas ladowania danych do user-database. (" + e.getCause() + ")")));
        componentManager.registerComponent(UserController.class);

        componentManager.registerComponent(HomeManager.class);
        componentManager.registerComponent(HomeRunnable.class);
        componentManager.registerComponent(HomeController.class);
        componentManager.registerComponent(HomeCommand.class);
    }

    @Override
    public void disable() {
        // features need to be call when server is stopping
    }

    @Override
    public DreamVersion getDreamVersion() {
        return DreamVersion.create("Dream-Home", "1.0", "torobolin");
    }

    @Override
    public OkaeriSerdesPack getPluginSerdesPack() {
        return registry -> {
            registry.register(new BukkitNoticeSerdes());
            registry.register(new MenuBuilderSerdes());
            registry.register(new RawLocationSerdes());
        };
    }
}
