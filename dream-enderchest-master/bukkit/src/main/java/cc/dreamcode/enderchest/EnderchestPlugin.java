package cc.dreamcode.enderchest;

import cc.dreamcode.command.bukkit.BukkitCommandProvider;
import cc.dreamcode.enderchest.command.EnderchestCommand;
import cc.dreamcode.enderchest.ec.EnderchestController;
import cc.dreamcode.enderchest.ec.EnderchestManager;
import cc.dreamcode.enderchest.ec.EnderchestTypeSerdes;
import cc.dreamcode.enderchest.item.ArrayItemStackSerdes;
import cc.dreamcode.enderchest.menu.EnderchestMainMenu;
import cc.dreamcode.enderchest.menu.EnderchestMenu;
import cc.dreamcode.enderchest.task.SaveTask;
import cc.dreamcode.enderchest.user.UserController;
import cc.dreamcode.enderchest.user.UserManager;
import cc.dreamcode.menu.bukkit.BukkitMenuProvider;
import cc.dreamcode.menu.bukkit.okaeri.MenuBuilderSerdes;
import cc.dreamcode.notice.minecraft.bukkit.serdes.BukkitNoticeSerdes;
import cc.dreamcode.platform.DreamVersion;
import cc.dreamcode.platform.bukkit.DreamBukkitConfig;
import cc.dreamcode.platform.bukkit.DreamBukkitPlatform;
import cc.dreamcode.platform.bukkit.component.CommandComponentResolver;
import cc.dreamcode.platform.bukkit.component.ConfigurationComponentResolver;
import cc.dreamcode.platform.bukkit.component.ListenerComponentResolver;
import cc.dreamcode.platform.bukkit.component.RunnableComponentResolver;
import cc.dreamcode.platform.component.ComponentManager;
import cc.dreamcode.platform.persistence.DreamPersistence;
import cc.dreamcode.platform.persistence.component.DocumentPersistenceComponentResolver;
import cc.dreamcode.platform.persistence.component.DocumentRepositoryComponentResolver;
import cc.dreamcode.enderchest.config.MessageConfig;
import cc.dreamcode.enderchest.config.PluginConfig;
import cc.dreamcode.enderchest.user.UserRepository;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.configs.yaml.bukkit.serdes.serializer.ItemStackSerializer;
import eu.okaeri.persistence.document.DocumentPersistence;
import eu.okaeri.tasker.bukkit.BukkitTasker;
import lombok.Getter;
import lombok.NonNull;

public final class EnderchestPlugin extends DreamBukkitPlatform implements DreamBukkitConfig, DreamPersistence {

    @Getter private static EnderchestPlugin enderchestPlugin;

    @Override
    public void load(@NonNull ComponentManager componentManager) {
        enderchestPlugin = this;
    }

    @Override
    public void enable(@NonNull ComponentManager componentManager) {
        this.registerInjectable(BukkitTasker.newPool(this));
        this.registerInjectable(BukkitMenuProvider.create(this));
        this.registerInjectable(BukkitCommandProvider.create(this, this.getInjector()));

        componentManager.registerResolver(CommandComponentResolver.class);
        componentManager.registerResolver(ListenerComponentResolver.class);
        componentManager.registerResolver(RunnableComponentResolver.class);

        componentManager.registerResolver(ConfigurationComponentResolver.class);
        componentManager.registerComponent(MessageConfig.class, messageConfig ->
                this.getInject(BukkitCommandProvider.class).ifPresent(bukkitCommandProvider -> {
                    bukkitCommandProvider.setRequiredPermissionMessage(messageConfig.noPermission.getText());
                    bukkitCommandProvider.setRequiredPlayerMessage(messageConfig.notPlayer.getText());
                }));

        componentManager.registerComponent(PluginConfig.class, pluginConfig -> {
            this.registerInjectable(pluginConfig.storageConfig);

            componentManager.registerResolver(DocumentPersistenceComponentResolver.class);
            componentManager.registerResolver(DocumentRepositoryComponentResolver.class);

            componentManager.registerComponent(DocumentPersistence.class);
            componentManager.registerComponent(UserRepository.class);
            componentManager.registerComponent(UserManager.class, userManager -> userManager.sync(e ->
                    this.getDreamLogger().error("Wystapil problem podczas ladowania danych do user-database. (" + e.getCause() + ")")));
            componentManager.registerComponent(UserController.class);
        });

        componentManager.registerComponent(EnderchestMenu.class);
        componentManager.registerComponent(EnderchestMainMenu.class);
        componentManager.registerComponent(EnderchestManager.class);
        componentManager.registerComponent(EnderchestCommand.class);
        componentManager.registerComponent(EnderchestController.class);
        componentManager.registerComponent(SaveTask.class, SaveTask::run);
    }

    @Override
    public void disable() {
        getInject(UserManager.class).ifPresent(UserManager::saveAll);
    }

    @Override
    public @NonNull DreamVersion getDreamVersion() {
        return DreamVersion.create("Dream-Enderchest", "1.0", "eastcause");
    }

    @Override
    public @NonNull OkaeriSerdesPack getConfigSerdesPack() {
        return registry -> {
            registry.register(new BukkitNoticeSerdes());
            registry.register(new MenuBuilderSerdes());
            registry.register(new EnderchestTypeSerdes());
        };
    }

    @Override
    public @NonNull OkaeriSerdesPack getPersistenceSerdesPack() {
        return registry -> {
            registry.register(new ItemStackSerializer());
            registry.register(new ArrayItemStackSerdes());
        };

    }

}
