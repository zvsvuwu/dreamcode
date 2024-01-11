package cc.dreamcode.totem;

import cc.dreamcode.command.bukkit.BukkitCommandProvider;
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
import cc.dreamcode.totem.command.TotemCommand;
import cc.dreamcode.totem.config.MessageConfig;
import cc.dreamcode.totem.config.PluginConfig;
import cc.dreamcode.totem.controller.UserController;
import cc.dreamcode.totem.inventory.TotemMenuHolder;
import cc.dreamcode.totem.user.UserRepository;
import cc.dreamcode.totem.vault.VaultApi;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import eu.okaeri.persistence.document.DocumentPersistence;
import eu.okaeri.tasker.bukkit.BukkitTasker;
import lombok.Getter;
import lombok.NonNull;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;

public final class TotemPlugin extends DreamBukkitPlatform implements DreamBukkitConfig, DreamPersistence {

    @Getter private static TotemPlugin totemPlugin;
    @Getter private static Economy econ = null;

    @Override
    public void load(@NonNull ComponentManager componentManager) {
        totemPlugin = this;
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

        componentManager.registerComponent(MessageConfig.class, messageConfig -> this.getInject(BukkitCommandProvider.class).ifPresent(bukkitCommandProvider -> {
            bukkitCommandProvider.setRequiredPermissionMessage(messageConfig.noPermission.getText());
            bukkitCommandProvider.setRequiredPlayerMessage(messageConfig.notPlayer.getText());
        }));

        componentManager.registerComponent(PluginConfig.class, pluginConfig -> {
            componentManager.setDebug(pluginConfig.debug);
            this.registerInjectable(pluginConfig.storageConfig);
            componentManager.registerResolver(DocumentPersistenceComponentResolver.class);
            componentManager.registerResolver(DocumentRepositoryComponentResolver.class);
            componentManager.registerComponent(DocumentPersistence.class);
            componentManager.registerComponent(UserRepository.class);
            componentManager.registerComponent(TotemMenuHolder.class);
        });

        componentManager.registerComponent(VaultApi.class);
        componentManager.registerComponent(TotemCommand.class);
        componentManager.registerComponent(UserController.class);
    }

    public void disable() {
    }

    @NonNull
    public DreamVersion getDreamVersion() {
        return DreamVersion.create("Dream-Totem", "1.0.6", "Sayler");
    }

    @Override
    public @NonNull OkaeriSerdesPack getConfigSerdesPack() {
        return registry -> {
            registry.register(new BukkitNoticeSerdes());
            registry.register(new MenuBuilderSerdes());
        };
    }

    @Override
    public @NonNull OkaeriSerdesPack getPersistenceSerdesPack() {
        return registry -> {
            registry.register(new SerdesBukkit());
        };
    }
}
