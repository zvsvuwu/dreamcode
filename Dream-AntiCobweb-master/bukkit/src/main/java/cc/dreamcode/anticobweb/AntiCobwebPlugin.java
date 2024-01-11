package cc.dreamcode.anticobweb;

import cc.dreamcode.anticobweb.command.AntiCobwebCommand;
import cc.dreamcode.anticobweb.config.MessagesConfig;
import cc.dreamcode.anticobweb.config.PluginConfig;
import cc.dreamcode.anticobweb.controller.AntiCobwebController;
import cc.dreamcode.anticobweb.manager.CooldownManager;
import cc.dreamcode.command.bukkit.BukkitCommandProvider;
import cc.dreamcode.menu.serdes.bukkit.okaeri.MenuBuilderSerdes;
import cc.dreamcode.notice.bukkit.BukkitNoticeProvider;
import cc.dreamcode.notice.bukkit.okaeri_serdes.BukkitNoticeSerdes;
import cc.dreamcode.platform.DreamVersion;
import cc.dreamcode.platform.bukkit.DreamBukkitPlatform;
import cc.dreamcode.platform.bukkit.component.CommandComponentResolver;
import cc.dreamcode.platform.bukkit.component.ConfigurationComponentResolver;
import cc.dreamcode.platform.bukkit.component.ListenerComponentResolver;
import cc.dreamcode.platform.bukkit.component.RunnableComponentResolver;
import cc.dreamcode.platform.component.ComponentManager;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import lombok.Getter;
import lombok.NonNull;

public final class AntiCobwebPlugin extends DreamBukkitPlatform {

    @Getter
    private static AntiCobwebPlugin antiCobwebPlugin;

    @Override
    public void load(@NonNull ComponentManager componentManager) {
        antiCobwebPlugin = this;
    }

    @Override
    public void enable(@NonNull ComponentManager componentManager) {
        this.registerInjectable(BukkitNoticeProvider.create(this));
        this.registerInjectable(BukkitCommandProvider.create(this, this.getInjector()));

        componentManager.registerResolver(CommandComponentResolver.class);
        componentManager.registerResolver(ListenerComponentResolver.class);
        componentManager.registerResolver(RunnableComponentResolver.class);

        componentManager.registerResolver(ConfigurationComponentResolver.class);

        componentManager.registerComponent(PluginConfig.class);
        componentManager.registerComponent(MessagesConfig.class, messageConfig -> {
            this.getInject(BukkitCommandProvider.class).ifPresent(bukkitCommandProvider -> {
                bukkitCommandProvider.setNoPermissionMessage(messageConfig.noPermission);
                bukkitCommandProvider.setNoPlayerMessage(messageConfig.notPlayer);
            });
        });
        componentManager.registerComponent(CooldownManager.class);
        componentManager.registerComponent(AntiCobwebController.class);

        componentManager.registerComponent(AntiCobwebCommand.class);
    }

    @Override
    public void disable() {}

    @Override
    public @NonNull DreamVersion getDreamVersion() {
        return DreamVersion.create("Dream-AntiCobweb", "1.0", "Codestech");
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
        return registry -> {};
    }

}
