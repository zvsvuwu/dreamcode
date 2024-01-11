package cc.dreamcode.afkreward;

import cc.dreamcode.afkreward.config.PluginConfig;
import cc.dreamcode.afkreward.controller.AfkRewardController;
import cc.dreamcode.afkreward.manager.AfkRewardManager;
import cc.dreamcode.afkreward.runnable.AfkRewardRunnable;
import cc.dreamcode.notice.minecraft.bukkit.serdes.BukkitNoticeSerdes;
import cc.dreamcode.platform.DreamVersion;
import cc.dreamcode.platform.bukkit.DreamBukkitConfig;
import cc.dreamcode.platform.bukkit.DreamBukkitPlatform;
import cc.dreamcode.platform.bukkit.component.ConfigurationComponentResolver;
import cc.dreamcode.platform.bukkit.component.ListenerComponentResolver;
import cc.dreamcode.platform.bukkit.component.RunnableComponentResolver;
import cc.dreamcode.platform.component.ComponentManager;
import cc.dreamcode.afkreward.config.MessageConfig;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import lombok.Getter;
import lombok.NonNull;

public final class AfkRewardPlugin extends DreamBukkitPlatform implements DreamBukkitConfig {

    @Getter private static AfkRewardPlugin afkRewardPlugin;

    @Override
    public void load(@NonNull ComponentManager componentManager) {
        afkRewardPlugin = this;
    }

    @Override
    public void enable(@NonNull ComponentManager componentManager) {
        componentManager.registerResolver(ListenerComponentResolver.class);
        componentManager.registerResolver(RunnableComponentResolver.class);

        componentManager.registerResolver(ConfigurationComponentResolver.class);
        componentManager.registerComponent(MessageConfig.class);
        componentManager.registerComponent(PluginConfig.class);

        componentManager.registerComponent(AfkRewardManager.class);
        componentManager.registerComponent(AfkRewardController.class);
        componentManager.registerComponent(AfkRewardRunnable.class);
    }

    @Override
    public void disable() {
        // features need to be call by stop server
    }

    @Override
    public @NonNull DreamVersion getDreamVersion() {
        return DreamVersion.create("Dream-AfkReward", "1.1", "torobolin");
    }

    @Override
    public OkaeriSerdesPack getConfigSerdesPack() {
        return registry -> {
            registry.register(new BukkitNoticeSerdes());
        };
    }
}
