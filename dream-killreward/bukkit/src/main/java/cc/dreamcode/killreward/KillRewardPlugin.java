package cc.dreamcode.killreward;

import cc.dreamcode.command.bukkit.BukkitCommandProvider;
import cc.dreamcode.killreward.boost.BoostManager;
import cc.dreamcode.killreward.boost.BoostTask;
import cc.dreamcode.killreward.command.KillRewardCommand;
import cc.dreamcode.killreward.config.PluginConfig;
import cc.dreamcode.killreward.controller.KillRewardController;
import cc.dreamcode.killreward.manager.CooldownManager;
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
import cc.dreamcode.killreward.config.MessageConfig;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.tasker.bukkit.BukkitTasker;
import lombok.Getter;
import lombok.NonNull;

public final class KillRewardPlugin extends DreamBukkitPlatform implements DreamBukkitConfig, DreamPersistence {

    @Getter private static KillRewardPlugin killRewardPlugin;

    @Override
    public void load(@NonNull ComponentManager componentManager) {
        killRewardPlugin = this;
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

        componentManager.registerComponent(PluginConfig.class, pluginConfig -> componentManager.setDebug(pluginConfig.debug));

        componentManager.registerComponent(BoostManager.class);
        componentManager.registerComponent(CooldownManager.class);

        componentManager.registerComponent(KillRewardCommand.class);
        componentManager.registerComponent(KillRewardController.class);
        componentManager.registerComponent(BoostTask.class);
    }

    @Override
    public void disable() {
    }

    @Override
    public @NonNull DreamVersion getDreamVersion() {
        return DreamVersion.create("Dream-KillReward", "1.0.2", "Kajteh_");
    }

    @Override
    public @NonNull OkaeriSerdesPack getConfigSerdesPack() {
        return registry -> {
            registry.register(new BukkitNoticeSerdes());
            registry.register(new MenuBuilderSerdes());
        };
    }
}
