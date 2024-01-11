package cc.dreamcode.shulker;

import cc.dreamcode.menu.bukkit.BukkitMenuProvider;
import cc.dreamcode.notice.minecraft.bukkit.serdes.BukkitNoticeSerdes;
import cc.dreamcode.platform.DreamVersion;
import cc.dreamcode.platform.bukkit.DreamBukkitConfig;
import cc.dreamcode.platform.bukkit.DreamBukkitPlatform;
import cc.dreamcode.platform.bukkit.component.ConfigurationComponentResolver;
import cc.dreamcode.platform.bukkit.component.ListenerComponentResolver;
import cc.dreamcode.platform.bukkit.component.RunnableComponentResolver;
import cc.dreamcode.platform.component.ComponentManager;
import cc.dreamcode.shulker.config.MessageConfig;
import cc.dreamcode.shulker.config.PluginConfig;
import cc.dreamcode.shulker.cooldown.CooldownManager;
import cc.dreamcode.shulker.cooldown.CooldownRunnable;
import cc.dreamcode.utilities.bukkit.BukkitReflectionUtil;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import lombok.Getter;
import lombok.NonNull;

public final class ShulkerPlugin extends DreamBukkitPlatform implements DreamBukkitConfig {

    @Getter private static ShulkerPlugin shulkerPlugin;

    @Override
    public void load(@NonNull ComponentManager componentManager) {
        shulkerPlugin = this;
    }

    @Override
    public void enable(@NonNull ComponentManager componentManager) {
        if (!BukkitReflectionUtil.isSupported(11)) {
            throw new RuntimeException("Plugin doesn't support this server version, update to: 1.11+");
        }

        this.registerInjectable(BukkitMenuProvider.create(this));

        componentManager.registerResolver(ConfigurationComponentResolver.class);
        componentManager.registerComponent(MessageConfig.class);
        componentManager.registerComponent(PluginConfig.class);

        componentManager.registerResolver(ListenerComponentResolver.class);
        componentManager.registerResolver(RunnableComponentResolver.class);

        componentManager.registerComponent(CooldownManager.class);
        componentManager.registerComponent(CooldownRunnable.class);

        componentManager.registerComponent(ShulkerManager.class);
        componentManager.registerComponent(ShulkerController.class);
    }

    @Override
    public void disable() {
        // features need to be call when server is stopping
    }

    @Override
    public @NonNull DreamVersion getDreamVersion() {
        return DreamVersion.create("Dream-Shulker", "2.0.10", "SnoxMox, Ravis96");
    }

    @Override
    public @NonNull OkaeriSerdesPack getConfigSerdesPack() {
        return registry -> {
            registry.register(new BukkitNoticeSerdes());
        };
    }

}
