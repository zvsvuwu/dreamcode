package cc.dreamcode.zonebox;

import cc.dreamcode.zonebox.command.BlockCommand;
import cc.dreamcode.command.bukkit.BukkitCommandProvider;
import cc.dreamcode.notice.minecraft.bukkit.serdes.BukkitNoticeSerdes;
import cc.dreamcode.platform.DreamVersion;
import cc.dreamcode.platform.bukkit.DreamBukkitConfig;
import cc.dreamcode.platform.bukkit.DreamBukkitPlatform;
import cc.dreamcode.platform.bukkit.component.CommandComponentResolver;
import cc.dreamcode.platform.bukkit.component.ConfigurationComponentResolver;
import cc.dreamcode.platform.bukkit.component.ListenerComponentResolver;
import cc.dreamcode.platform.bukkit.component.RunnableComponentResolver;
import cc.dreamcode.platform.component.ComponentManager;
import cc.dreamcode.zonebox.config.MessageConfig;
import cc.dreamcode.zonebox.config.PluginConfig;
import cc.dreamcode.zonebox.controller.BlockRestrictionController;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import lombok.Getter;
import lombok.NonNull;

public final class ZoneBoxPlugin extends DreamBukkitPlatform implements DreamBukkitConfig {

    @Getter private static ZoneBoxPlugin blockRestrictionPlugin;

    @Override
    public void load(@NonNull ComponentManager componentManager) {
        blockRestrictionPlugin = this;
    }

    @Override
    public void enable(@NonNull ComponentManager componentManager) {
        this.registerInjectable(BukkitCommandProvider.create(this, this.getInjector()));

        componentManager.registerResolver(CommandComponentResolver.class);
        componentManager.registerResolver(ListenerComponentResolver.class);
        componentManager.registerResolver(RunnableComponentResolver.class);

        componentManager.registerResolver(ConfigurationComponentResolver.class);
        componentManager.registerComponent(MessageConfig.class, messageConfig ->
                this.getInject(BukkitCommandProvider.class).ifPresent(bukkitCommandProvider -> {
                    bukkitCommandProvider.setRequiredPermissionMessage(messageConfig.noPermission.getText());
                    bukkitCommandProvider.setRequiredPlayerMessage(messageConfig.noPlayer.getText());
                }));
        componentManager.registerComponent(PluginConfig.class);

        componentManager.registerComponent(BlockRestrictionController.class);
        componentManager.registerComponent(BlockCommand.class);
    }

    @Override
    public void disable() {
        // features need to be call when server is stopping
    }

    @Override
    public @NonNull DreamVersion getDreamVersion() {
        return DreamVersion.create("Dream-ZoneBox", "1.0.1", "Nightusio");
    }

    @Override
    public @NonNull OkaeriSerdesPack getConfigSerdesPack() {
        return registry -> registry.register(new BukkitNoticeSerdes());
    }

}
