package cc.dreamcode.ffa;

import cc.dreamcode.command.bukkit.BukkitCommandProvider;
import cc.dreamcode.ffa.config.MessageConfig;
import cc.dreamcode.ffa.config.PluginConfig;
import cc.dreamcode.ffa.user.UserCache;
import cc.dreamcode.ffa.user.ranking.UserRanking;
import cc.dreamcode.ffa.user.UserRepository;
import cc.dreamcode.ffa.config.ConfigReloadCommand;
import cc.dreamcode.ffa.user.command.KillStreakCommand;
import cc.dreamcode.ffa.user.command.SetSpawnCommand;
import cc.dreamcode.ffa.user.UserController;
import cc.dreamcode.ffa.deposit.DepositItemTask;
import cc.dreamcode.ffa.user.UserPlaceholder;
import cc.dreamcode.ffa.user.ranking.UserRankingCommand;
import cc.dreamcode.ffa.user.ranking.UserRankingPlaceholder;
import cc.dreamcode.ffa.user.ranking.UserRankingSortTask;
import cc.dreamcode.ffa.user.command.SaveInventoryCommand;
import cc.dreamcode.ffa.user.combat.UserCombatInfoUpdateTask;
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
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import eu.okaeri.persistence.document.DocumentPersistence;
import eu.okaeri.tasker.bukkit.BukkitTasker;
import lombok.Getter;
import lombok.NonNull;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

import java.util.Optional;

public final class BukkitFFAPlugin extends DreamBukkitPlatform implements DreamBukkitConfig, DreamPersistence {

    @Getter private static BukkitFFAPlugin bukkitFFAPlugin;

    @Override
    public void load(@NonNull ComponentManager componentManager) {
        bukkitFFAPlugin = this;
    }

    @Override
    public void enable(@NonNull ComponentManager componentManager) {
        this.registerInjectable(BukkitTasker.newPool(this));
        this.registerInjectable(BukkitMenuProvider.create(this));
        this.registerInjectable(BukkitCommandProvider.create(this, this.getInjector()));

        componentManager.registerResolver(ConfigurationComponentResolver.class);
        componentManager.registerComponent(MessageConfig.class);

        componentManager.registerResolver(CommandComponentResolver.class);
        componentManager.registerResolver(ListenerComponentResolver.class);
        componentManager.registerResolver(RunnableComponentResolver.class);

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
            componentManager.registerComponent(UserCache.class);
            componentManager.registerComponent(UserController.class);
            componentManager.registerComponent(UserRanking.class);

            componentManager.registerComponent(UserCombatInfoUpdateTask.class);
            componentManager.registerComponent(DepositItemTask.class);
            componentManager.registerComponent(UserRankingSortTask.class);

            componentManager.registerComponent(KillStreakCommand.class);
            componentManager.registerComponent(SaveInventoryCommand.class);
            componentManager.registerComponent(SetSpawnCommand.class);
            componentManager.registerComponent(ConfigReloadCommand.class);
            componentManager.registerComponent(UserRankingCommand.class);
        });

        Optional.ofNullable(this.getServer().getPluginManager().getPlugin("PlaceholderAPI"))
                .ifPresent(plugin -> {
                    componentManager.registerComponent(UserPlaceholder.class, PlaceholderExpansion::register);
                    componentManager.registerComponent(UserRankingPlaceholder.class, PlaceholderExpansion::register);
                    this.getDreamLogger().debug("Registered PlaceholderAPI expansions.");
                });
    }

    @Override
    public void disable() {
        // features need to be call when server is stopping
    }

    @Override
    public @NonNull DreamVersion getDreamVersion() {
        return DreamVersion.create("dream-ffa", "1.0-beta.1", "vkie");
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
