package cc.dreamcode.tops;

import cc.dreamcode.command.bukkit.BukkitCommandProvider;
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
import cc.dreamcode.tops.command.TopCommand;
import cc.dreamcode.tops.config.MessageConfig;
import cc.dreamcode.tops.config.PluginConfig;
import cc.dreamcode.tops.controller.TopController;
import cc.dreamcode.tops.controller.UserController;
import cc.dreamcode.tops.task.TopTask;
import cc.dreamcode.tops.task.UserSaveTask;
import cc.dreamcode.tops.user.UserManager;
import cc.dreamcode.tops.user.UserRepository;
import cc.dreamcode.tops.user.top.TopCalculator;
import cc.dreamcode.tops.user.top.TopManager;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.persistence.document.DocumentPersistence;
import eu.okaeri.tasker.bukkit.BukkitTasker;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.entity.HumanEntity;

public final class BukkitTopsPlugin extends DreamBukkitPlatform {

    @Getter private static BukkitTopsPlugin bukkitTopsPlugin;

    @Override
    public void load(@NonNull ComponentManager componentManager) {
        bukkitTopsPlugin = this;
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
        componentManager.registerComponent(MessageConfig.class, messageConfig -> this.getInject(BukkitCommandProvider.class).ifPresent(bukkitCommandProvider -> {
            bukkitCommandProvider.setNoPermissionMessage(messageConfig.noPermission);
            bukkitCommandProvider.setNoPlayerMessage(messageConfig.noPlayer);
        }));
        componentManager.registerComponent(PluginConfig.class, pluginConfig -> {
            this.registerInjectable(pluginConfig.storageConfig);

            componentManager.registerResolver(DocumentPersistenceComponentResolver.class);
            componentManager.registerResolver(DocumentRepositoryComponentResolver.class);

            componentManager.registerComponent(DocumentPersistence.class);
            componentManager.registerComponent(UserRepository.class);
        });


        componentManager.registerComponent(DocumentPersistence.class);
        componentManager.registerComponent(UserRepository.class);
        componentManager.registerComponent(UserManager.class, userManager -> userManager.syncUserInMemoryDatabase(e ->
                this.getDreamLogger().error("Wystapil problem podczas ladowania danych do user-database. (" + e.getCause() + ")")));
        componentManager.registerComponent(UserController.class);
        componentManager.registerComponent(UserSaveTask.class);
        componentManager.registerComponent(TopCalculator.class);
        componentManager.registerComponent(TopManager.class);
        componentManager.registerComponent(TopTask.class);
        componentManager.registerComponent(TopCommand.class);
        componentManager.registerComponent(TopController.class);
    }

    @Override
    public void disable() {
        this.getInject(UserManager.class).ifPresent(UserManager::saveAll);
    }

    @Override
    public @NonNull DreamVersion getDreamVersion() {
        return DreamVersion.create("Dream-Tops", "1.0", "Nightusio");
    }

    @Override
    public @NonNull OkaeriSerdesPack getConfigurationSerdesPack() {
        return registry -> {
            registry.register(new BukkitNoticeSerdes());
            registry.register(new MenuBuilderSerdes());
        };
    }

    @Override
    public @NonNull OkaeriSerdesPack getPersistenceSerdesPack() {
        return registry -> {

        };
    }

}
