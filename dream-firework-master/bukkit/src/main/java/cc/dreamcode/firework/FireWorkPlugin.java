package cc.dreamcode.firework;

import cc.dreamcode.command.bukkit.BukkitCommandProvider;
import cc.dreamcode.firework.command.FireWorkCommand;
import cc.dreamcode.firework.config.MessageConfig;
import cc.dreamcode.firework.config.PluginConfig;
import cc.dreamcode.firework.controller.ElytraBoostController;
import cc.dreamcode.firework.controller.PlayerInteractController;
import cc.dreamcode.firework.recipe.RecipeComponent;
import cc.dreamcode.notice.minecraft.bukkit.serdes.BukkitNoticeSerdes;
import cc.dreamcode.platform.DreamVersion;
import cc.dreamcode.platform.bukkit.DreamBukkitConfig;
import cc.dreamcode.platform.bukkit.DreamBukkitPlatform;
import cc.dreamcode.platform.bukkit.component.CommandComponentResolver;
import cc.dreamcode.platform.bukkit.component.ConfigurationComponentResolver;
import cc.dreamcode.platform.bukkit.component.ListenerComponentResolver;
import cc.dreamcode.platform.bukkit.exception.BukkitPluginException;
import cc.dreamcode.platform.component.ComponentManager;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.tasker.bukkit.BukkitTasker;
import io.papermc.lib.PaperLib;
import io.papermc.lib.environments.Environment;
import lombok.Getter;
import lombok.NonNull;

public final class FireWorkPlugin extends DreamBukkitPlatform implements DreamBukkitConfig {

    @Getter private static FireWorkPlugin fireWorkPlugin;

    @Override
    public void load(@NonNull ComponentManager componentManager) {
        fireWorkPlugin = this;
    }

    @Override
    public void enable(@NonNull ComponentManager componentManager) {
        final Environment environment = PaperLib.getEnvironment();
        if (!environment.isPaper()) {
            throw new BukkitPluginException("Plugin needs Paper software or his fork to work.");
        }

        this.registerInjectable(BukkitTasker.newPool(this));
        this.registerInjectable(BukkitCommandProvider.create(this, this.getInjector()));

        componentManager.registerResolver(CommandComponentResolver.class);
        componentManager.registerResolver(ListenerComponentResolver.class);

        componentManager.registerResolver(ConfigurationComponentResolver.class);
        componentManager.registerComponent(MessageConfig.class, messageConfig ->
                this.getInject(BukkitCommandProvider.class).ifPresent(bukkitCommandProvider -> {
                    bukkitCommandProvider.setRequiredPermissionMessage(messageConfig.noPermission.getText());
                    bukkitCommandProvider.setRequiredPlayerMessage(messageConfig.notPlayer.getText());
                }));

        componentManager.registerComponent(PluginConfig.class);

        componentManager.registerComponent(RecipeComponent.class, RecipeComponent::registerRecipe);

        componentManager.registerComponent(FireWorkCommand.class);
        componentManager.registerComponent(ElytraBoostController.class);
        componentManager.registerComponent(PlayerInteractController.class);
    }

    @Override
    public void disable() {
        // features need to be call when server is stopping
    }

    @Override
    public @NonNull DreamVersion getDreamVersion() {
        return DreamVersion.create("Dream-FireWork", "1.4.2", "Nightusio");
    }

    @Override
    public @NonNull OkaeriSerdesPack getConfigSerdesPack() {
        return registry -> registry.register(new BukkitNoticeSerdes());
    }

}
