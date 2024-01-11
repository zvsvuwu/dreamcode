package cc.dreamcode.wallet;

import cc.dreamcode.command.bukkit.BukkitCommandProvider;
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
import cc.dreamcode.platform.hook.DreamHookManager;
import cc.dreamcode.platform.persistence.DreamPersistence;
import cc.dreamcode.platform.persistence.component.DocumentPersistenceComponentResolver;
import cc.dreamcode.platform.persistence.component.DocumentRepositoryComponentResolver;
import cc.dreamcode.wallet.citizens.CitizensHook;
import cc.dreamcode.wallet.citizens.NpcTopSerdes;
import cc.dreamcode.wallet.command.OdbierznagrodeCommand;
import cc.dreamcode.wallet.command.PortfelCommand;
import cc.dreamcode.wallet.config.MessageConfig;
import cc.dreamcode.wallet.config.PluginConfig;
import cc.dreamcode.wallet.menu.BuyHistoryMenu;
import cc.dreamcode.wallet.placeholder.PlaceholderApiExpansion;
import cc.dreamcode.wallet.placeholder.PlaceholderApiHook;
import cc.dreamcode.wallet.menu.WalletMenu;
import cc.dreamcode.wallet.product.ProductBuySerdes;
import cc.dreamcode.wallet.product.ProductSerdes;
import cc.dreamcode.wallet.top.TopManager;
import cc.dreamcode.wallet.user.UserController;
import cc.dreamcode.wallet.user.UserManager;
import cc.dreamcode.wallet.user.UserRepository;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import eu.okaeri.persistence.document.DocumentPersistence;
import eu.okaeri.tasker.bukkit.BukkitTasker;
import lombok.Getter;
import lombok.NonNull;

public final class WalletPlugin extends DreamBukkitPlatform implements DreamBukkitConfig, DreamPersistence {

    @Getter private static WalletPlugin walletPlugin;

    @Override
    public void load(@NonNull ComponentManager componentManager) {
        walletPlugin = this;
    }

    @Override
    public void enable(@NonNull ComponentManager componentManager) {
        componentManager.setDebug(false);

        this.getInjector().registerInjectable(BukkitTasker.newPool(this));
        this.getInjector().registerInjectable(BukkitMenuProvider.create(this));
        this.getInjector().registerInjectable(BukkitCommandProvider.create(this, this.getInjector()));

        componentManager.registerResolver(CommandComponentResolver.class);
        componentManager.registerResolver(ListenerComponentResolver.class);
        componentManager.registerResolver(RunnableComponentResolver.class);

        componentManager.registerResolver(ConfigurationComponentResolver.class);
        componentManager.registerComponent(MessageConfig.class);
        componentManager.registerComponent(PluginConfig.class, pluginConfig -> {
            // register persistence + repositories
            this.registerInjectable(pluginConfig.storageConfig);

            componentManager.registerResolver(DocumentPersistenceComponentResolver.class);
            componentManager.registerResolver(DocumentRepositoryComponentResolver.class);
            componentManager.registerComponent(DocumentPersistence.class);

            // enable additional logs and debug messages
            componentManager.setDebug(pluginConfig.debug);
        });

        componentManager.registerComponent(UserRepository.class);
        componentManager.registerComponent(UserManager.class, UserManager::readUsers);
        componentManager.registerComponent(UserController.class);

        componentManager.registerComponent(TopManager.class);

        componentManager.registerComponent(DreamHookManager.class, dreamHookManager ->
                dreamHookManager.registerHook(PlaceholderApiHook.class));

        componentManager.registerComponent(WalletMenu.class);
        componentManager.registerComponent(BuyHistoryMenu.class);

        componentManager.registerComponent(PortfelCommand.class);
        componentManager.registerComponent(OdbierznagrodeCommand.class);

        this.getInject(DreamHookManager.class)
                .flatMap(pluginHookManager -> pluginHookManager.get(PlaceholderApiHook.class))
                .ifPresent(placeholderApiHook -> placeholderApiHook.register(this.createInstance(PlaceholderApiExpansion.class)));

        if(this.getServer().getPluginManager().getPlugin("Citizens") != null) {
            componentManager.registerComponent(CitizensHook.class);
        }
    }
    @Override
    public void disable() {
        // features need to be call when server is stopping
    }

    @Override
    public @NonNull DreamVersion getDreamVersion() {
        return DreamVersion.create("Dream-Wallet", "1.0.4", "Ravis96");
    }

    @Override
    public @NonNull OkaeriSerdesPack getConfigSerdesPack() {
        return registry -> {
            registry.register(new BukkitNoticeSerdes());
            registry.register(new MenuBuilderSerdes());
            registry.register(new ProductSerdes());
            registry.register(new NpcTopSerdes());
        };
    }

    @Override
    public @NonNull OkaeriSerdesPack getPersistenceSerdesPack() {
        return registry -> {
            registry.register(new SerdesBukkit());
            registry.register(new ProductBuySerdes());
        };
    }

}
