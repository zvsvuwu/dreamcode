package cc.dreamcode.updatesystem;

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
import cc.dreamcode.platform.persistence.resolver.DocumentPersistenceComponentResolver;
import cc.dreamcode.platform.persistence.resolver.DocumentRepositoryComponentResolver;
import cc.dreamcode.updatesystem.command.ChangesCommand;
import cc.dreamcode.updatesystem.config.MessageConfig;
import cc.dreamcode.updatesystem.config.PluginConfig;
import cc.dreamcode.updatesystem.discord.Bot;
import cc.dreamcode.updatesystem.listener.InventoryClickListener;
import cc.dreamcode.updatesystem.menu.MessageMenu;
import cc.dreamcode.updatesystem.message.MessageManager;
import cc.dreamcode.updatesystem.message.MessageRepository;
import cc.dreamcode.updatesystem.update.UpdateChannelSerdes;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.persistence.document.DocumentPersistence;
import eu.okaeri.tasker.bukkit.BukkitTasker;
import lombok.Getter;
import lombok.NonNull;

public final class BukkitUpdateSystemPlugin extends DreamBukkitPlatform {

    @Getter private static BukkitUpdateSystemPlugin bukkitUpdateSystemPlugin;

    @Override
    public void load(@NonNull ComponentManager componentManager) {
        bukkitUpdateSystemPlugin = this;
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
        componentManager.registerComponent(MessageConfig.class, messageConfig -> this.getInject(BukkitCommandProvider.class));

        componentManager.registerComponent(PluginConfig.class, pluginConfig -> {
            this.registerInjectable(pluginConfig.storageConfig);

            componentManager.registerComponent(MessageMenu.class);

            componentManager.registerResolver(DocumentPersistenceComponentResolver.class);
            componentManager.registerResolver(DocumentRepositoryComponentResolver.class);

            componentManager.registerComponent(DocumentPersistence.class);
            componentManager.registerComponent(MessageRepository.class);
            componentManager.registerComponent(MessageManager.class, messageManager -> messageManager
                    .sync(e -> this.getDreamLogger().error("Wystapil problem podczas ladowania danych do message-database. (" + e.getCause() + ")")));
        });

        componentManager.registerComponent(Bot.class, Bot::init);
        componentManager.registerComponent(ChangesCommand.class);
        componentManager.registerComponent(InventoryClickListener.class);
    }

    @Override
    public void disable() {
        this.getInject(Bot.class).ifPresent(bot -> bot.getJda().shutdown());
    }

    @Override
    public @NonNull DreamVersion getDreamVersion() {
        return DreamVersion.create("Dream-SystemUpdateDiscord", "1.0", "eastcause");
    }

    @Override
    public @NonNull OkaeriSerdesPack getBukkitConfigurationSerdesPack() {
        return registry -> {
            registry.register(new BukkitNoticeSerdes());
            registry.register(new MenuBuilderSerdes());
            registry.register(new UpdateChannelSerdes());
        };
    }

    @Override
    public @NonNull OkaeriSerdesPack getBukkitPersistenceSerdesPack() {
        return registry -> {

        };
    }

}
