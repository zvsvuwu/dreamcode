package cc.dreamcode.antygrief;

import cc.dreamcode.antygrief.controller.AntyGriefController;
import cc.dreamcode.antygrief.hook.PluginHookManager;
import cc.dreamcode.antygrief.manager.AntyGriefManager;
import cc.dreamcode.antygrief.region.RegionSerdes;
import cc.dreamcode.command.bukkit.BukkitCommandProvider;
import cc.dreamcode.menu.bukkit.BukkitMenuProvider;
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
import cc.dreamcode.antygrief.config.MessageConfig;
import cc.dreamcode.antygrief.config.PluginConfig;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import lombok.Getter;
import lombok.NonNull;

public final class AntyGriefPlugin extends DreamBukkitPlatform {

    @Getter private static AntyGriefPlugin antyGriefPlugin;

    @Override
    public void load(@NonNull ComponentManager componentManager) {
        antyGriefPlugin = this;
    }

    @Override
    public void enable(@NonNull ComponentManager componentManager) {
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
                bukkitCommandProvider.setNoPlayerMessage(messageConfig.notPlayer);
            });
        });
        componentManager.registerComponent(PluginConfig.class);

        componentManager.registerComponent(PluginHookManager.class, PluginHookManager::registerHooks);

        componentManager.registerComponent(AntyGriefManager.class);
        componentManager.registerComponent(AntyGriefController.class);
    }

    @Override
    public void disable() {
        // features need to be call when server is stopping
    }

    @Override
    public DreamVersion getDreamVersion() {
        return DreamVersion.create("Dream-AntyGrief", "1.0", "torobolin");
    }

    @Override
    public OkaeriSerdesPack getPluginSerdesPack() {
        return registry -> {
            registry.register(new BukkitNoticeSerdes());
            registry.register(new MenuBuilderSerdes());
            registry.register(new RegionSerdes());
        };
    }
}
