package cc.dreamcode.disableeffects;

import cc.dreamcode.command.bukkit.BukkitCommandProvider;
import cc.dreamcode.disableeffects.command.DisableEffectsCommand;
import cc.dreamcode.disableeffects.listener.EffectController;
import cc.dreamcode.disableeffects.manager.RegionManager;
import cc.dreamcode.disableeffects.packet.AdapterPacketInitialize;
import cc.dreamcode.disableeffects.worldguard.WorldGuardStorage;
import cc.dreamcode.menu.bukkit.BukkitMenuProvider;
import cc.dreamcode.menu.bukkit.okaeri.MenuBuilderSerdes;
import cc.dreamcode.notice.bukkit.BukkitNoticeProvider;
import cc.dreamcode.notice.bukkit.okaeri_serdes.BukkitNoticeSerdes;
import cc.dreamcode.platform.DreamVersion;
import cc.dreamcode.platform.bukkit.DreamBukkitPlatform;
import cc.dreamcode.platform.bukkit.component.CommandComponentResolver;
import cc.dreamcode.platform.bukkit.component.ConfigurationComponentResolver;
import cc.dreamcode.platform.bukkit.component.ListenerComponentResolver;
import cc.dreamcode.platform.bukkit.component.RunnableComponentResolver;
import cc.dreamcode.platform.component.ComponentManager;
import cc.dreamcode.disableeffects.config.MessageConfig;
import cc.dreamcode.disableeffects.config.PluginConfig;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.tasker.bukkit.BukkitTasker;
import lombok.Getter;
import lombok.NonNull;

public final class BukkitDisableEffectsPlugin extends DreamBukkitPlatform {

    @Getter private static BukkitDisableEffectsPlugin bukkitDisableEffectsPlugin;

    @Override
    public void load(@NonNull ComponentManager componentManager) {
        bukkitDisableEffectsPlugin = this;
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
        componentManager.registerComponent(MessageConfig.class, messageConfig ->
                this.getInject(BukkitCommandProvider.class).ifPresent(bukkitCommandProvider -> bukkitCommandProvider.setRequiredPermissionMessage(messageConfig.noPermission)));

        componentManager.registerComponent(PluginConfig.class);

        componentManager.registerComponent(WorldGuardStorage.class, worldGuardStorage -> {
            if (!worldGuardStorage.init()) {
                getDreamLogger().info("Nie wykryto pluginu WorldGuard, plugin bedzie blokowal tylko i wylacznie swiaty!");
            }
        });
        componentManager.registerComponent(RegionManager.class);
        componentManager.registerComponent(EffectController.class);
        componentManager.registerComponent(AdapterPacketInitialize.class, AdapterPacketInitialize::init);
        componentManager.registerComponent(DisableEffectsCommand.class);

    }

    @Override
    public void disable() {
    }

    @Override
    public @NonNull DreamVersion getDreamVersion() {
        return DreamVersion.create("Dream-DisableEffects", "1.0", "eastcause");
    }

    @Override
    public @NonNull OkaeriSerdesPack getBukkitConfigurationSerdesPack() {
        return registry -> {
            registry.register(new BukkitNoticeSerdes());
            registry.register(new MenuBuilderSerdes());
        };
    }

    @Override
    public @NonNull OkaeriSerdesPack getBukkitPersistenceSerdesPack() {
        return registry -> {

        };
    }

}
