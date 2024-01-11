package cc.dreamcode.itemchange;

import cc.dreamcode.command.bukkit.BukkitCommandProvider;
import cc.dreamcode.itemchange.commands.ChangeCommand;
import cc.dreamcode.itemchange.hook.PluginHookManager;
import cc.dreamcode.notice.minecraft.bukkit.serdes.BukkitNoticeSerdes;
import cc.dreamcode.platform.DreamVersion;
import cc.dreamcode.platform.bukkit.DreamBukkitConfig;
import cc.dreamcode.platform.bukkit.DreamBukkitPlatform;
import cc.dreamcode.platform.bukkit.component.CommandComponentResolver;
import cc.dreamcode.platform.bukkit.component.ConfigurationComponentResolver;
import cc.dreamcode.platform.bukkit.component.ListenerComponentResolver;
import cc.dreamcode.platform.bukkit.component.RunnableComponentResolver;
import cc.dreamcode.platform.component.ComponentManager;
import cc.dreamcode.itemchange.config.MessageConfig;
import cc.dreamcode.itemchange.config.PluginConfig;
import cc.dreamcode.utilities.bukkit.BukkitReflectionUtil;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import lombok.Getter;
import lombok.NonNull;

public final class ItemChangePlugin extends DreamBukkitPlatform implements DreamBukkitConfig {

    @Getter private static ItemChangePlugin itemChangePlugin;

    @Override
    public void load(@NonNull ComponentManager componentManager) {
        itemChangePlugin = this;
    }

    @Override
    public void enable(@NonNull ComponentManager componentManager) {
        if (!BukkitReflectionUtil.isSupported(11)) {
            throw new RuntimeException("Plugin doesn't support this server version, update to: 1.11+");
        }

        this.registerInjectable(BukkitCommandProvider.create(this, this.getInjector()));

        componentManager.registerResolver(CommandComponentResolver.class);
        componentManager.registerResolver(ListenerComponentResolver.class);
        componentManager.registerResolver(RunnableComponentResolver.class);

        componentManager.registerResolver(ConfigurationComponentResolver.class);
        componentManager.registerComponent(MessageConfig.class, messageConfig -> {
            this.getInject(BukkitCommandProvider.class).ifPresent(bukkitCommandProvider -> {
                bukkitCommandProvider.setRequiredPermissionMessage(messageConfig.noPermission.getText());
                bukkitCommandProvider.setRequiredPlayerMessage(messageConfig.noPlayer.getText());
            });
        });
        componentManager.registerComponent(PluginConfig.class);

        componentManager.registerComponent(PluginHookManager.class, PluginHookManager::registerHooks);

        componentManager.registerComponent(ChangeCommand.class);
    }

    @Override
    public void disable() {
        // features need to be call when server is stopping
    }

    @Override
    public @NonNull DreamVersion getDreamVersion() {
        return DreamVersion.create("Dream-ItemChange", "1.0.2", "torobolin");
    }

    @Override
    public @NonNull OkaeriSerdesPack getConfigSerdesPack() {
        return registry -> registry.register(new BukkitNoticeSerdes());
    }

}
