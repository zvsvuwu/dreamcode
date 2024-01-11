package cc.dreamcode.guisuffix;

import cc.dreamcode.command.bukkit.BukkitCommandProvider;
import cc.dreamcode.guisuffix.command.AdminSuffixCommand;
import cc.dreamcode.guisuffix.command.RemoveSuffixCommand;
import cc.dreamcode.guisuffix.command.SuffixCommand;
import cc.dreamcode.guisuffix.config.MessageConfig;
import cc.dreamcode.guisuffix.config.PluginConfig;
import cc.dreamcode.guisuffix.controller.UserController;
import cc.dreamcode.guisuffix.hook.luckperms.LuckPermsHook;
import cc.dreamcode.guisuffix.hook.vault.VaultHook;
import cc.dreamcode.guisuffix.suffix.SuffixService;
import cc.dreamcode.guisuffix.suffix.menu.SuffixMenu;
import cc.dreamcode.guisuffix.suffix.menu.item.SuffixItemSerializer;
import cc.dreamcode.guisuffix.user.UserCache;
import cc.dreamcode.guisuffix.user.UserFactory;
import cc.dreamcode.guisuffix.user.UserRepository;
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
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import eu.okaeri.persistence.document.DocumentPersistence;
import eu.okaeri.tasker.bukkit.BukkitTasker;
import lombok.NonNull;

public final class GuiSuffixPlugin extends DreamBukkitPlatform implements DreamBukkitConfig, DreamPersistence {

    @Override
    public void load(@NonNull ComponentManager componentManager) { }

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

        componentManager.registerComponent(PluginConfig.class, pluginConfig -> {
            componentManager.setDebug(pluginConfig.debug);

            this.registerInjectable(pluginConfig.storageConfig);

            componentManager.registerResolver(DocumentPersistenceComponentResolver.class);
            componentManager.registerResolver(DocumentRepositoryComponentResolver.class);
            componentManager.registerComponent(DocumentPersistence.class);

            componentManager.registerComponent(UserRepository.class);
            componentManager.registerComponent(UserCache.class);
            componentManager.registerComponent(UserFactory.class, UserFactory::createOnline);
        });
        componentManager.registerComponent(UserController.class);

        // LuckPerms
        if (getServer().getPluginManager().getPlugin("LuckPerms") == null) {
            getDreamLogger().error("Nie wykryto pluginu LuckPerms, ktory jest wymagany do poprawnego dzialania pluginu. Zainstaluj plugin i zrestartuj serwer");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        componentManager.registerComponent(LuckPermsHook.class);

        //Vault
        componentManager.registerComponent(DreamHookManager.class, dreamHookManager -> dreamHookManager.registerHook(VaultHook.class));
        componentManager.registerComponent(SuffixService.class);
        componentManager.registerComponent(SuffixMenu.class);

        componentManager.registerComponent(SuffixCommand.class);
        componentManager.registerComponent(RemoveSuffixCommand.class);
        componentManager.registerComponent(AdminSuffixCommand.class);

        this.getInject(DreamHookManager.class)
                .flatMap(dreamHookManager -> dreamHookManager.get(VaultHook.class))
                .ifPresent(VaultHook::register);
    }

    @Override
    public void disable() { }

    @Override
    public @NonNull DreamVersion getDreamVersion() {
        return DreamVersion.create("Dream-GuiSuffix", "1.0.2", "ForeX03");
    }

    @Override
    public @NonNull OkaeriSerdesPack getConfigSerdesPack() {
        return registry -> {
            registry.register(new BukkitNoticeSerdes());
            registry.register(new MenuBuilderSerdes());
            registry.register(new SuffixItemSerializer());
        };
    }

    @Override
    public @NonNull OkaeriSerdesPack getPersistenceSerdesPack() {
        return registry -> registry.register(new SerdesBukkit());
    }
}