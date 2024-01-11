package cc.dreamcode.shaman;

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
import cc.dreamcode.platform.persistence.DreamPersistence;
import cc.dreamcode.platform.persistence.component.DocumentPersistenceComponentResolver;
import cc.dreamcode.platform.persistence.component.DocumentRepositoryComponentResolver;
import cc.dreamcode.shaman.commands.ShamanCommand;
import cc.dreamcode.shaman.config.MessageConfig;
import cc.dreamcode.shaman.config.PluginConfig;
import cc.dreamcode.shaman.menu.ShamanMenu;
import cc.dreamcode.shaman.perk.PerkController;
import cc.dreamcode.shaman.perk.PerkManager;
import cc.dreamcode.shaman.perk.PerkSerdes;
import cc.dreamcode.shaman.perk.PerkUpgradeSerdes;
import cc.dreamcode.shaman.user.UserController;
import cc.dreamcode.shaman.user.UserManager;
import cc.dreamcode.shaman.user.UserRepository;
import cc.dreamcode.shaman.user.UserSaveTask;
import cc.dreamcode.shaman.vault.VaultHook;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.persistence.document.DocumentPersistence;
import eu.okaeri.tasker.bukkit.BukkitTasker;
import lombok.Getter;
import lombok.NonNull;

public final class SzamanPlugin extends DreamBukkitPlatform implements DreamPersistence, DreamBukkitConfig {

    @Getter private static SzamanPlugin szamanPlugin;

    @Override
    public void load(@NonNull ComponentManager componentManager) {
        szamanPlugin = this;
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
                    bukkitCommandProvider.setRequiredPlayerMessage(messageConfig.noPlayer.getText());
                }));
        componentManager.registerComponent(PluginConfig.class, pluginConfig -> {
            this.registerInjectable(pluginConfig.storageConfig);

            componentManager.registerResolver(DocumentPersistenceComponentResolver.class);
            componentManager.registerResolver(DocumentRepositoryComponentResolver.class);

            componentManager.registerComponent(DocumentPersistence.class);
            componentManager.registerComponent(UserRepository.class);
            componentManager.registerComponent(UserManager.class, userManager -> {
                userManager.sync(e ->
                        this.getDreamLogger().error("Wystapil problem podczas ladowania danych do user-database. (" + e.getCause() + ")"));
                userManager.reloadPerks();
            });
            componentManager.registerComponent(UserController.class);
            componentManager.registerComponent(UserSaveTask.class);

            componentManager.registerComponent(PerkManager.class, perkManager -> {
                if (!perkManager.validatePerks(pluginConfig.perks.healthPerk,
                        pluginConfig.perks.speedPerk,
                        pluginConfig.perks.damagePerk,
                        pluginConfig.perks.lifeStealPerk,
                        pluginConfig.perks.fallPerk)) {
                    getServer().getPluginManager().disablePlugin(getSzamanPlugin());
                }
            });
        });

        componentManager.registerComponent(ShamanMenu.class);
        componentManager.registerComponent(ShamanCommand.class);
        componentManager.registerComponent(VaultHook.class, vaultHook -> {
            if (!vaultHook.setup()) {
                this.getDreamLogger().error("Nie wykryto plugin Vault, funkcje ekonmi nie zostaja aktywowane!");
            }
        });
        componentManager.registerComponent(PerkController.class);

    }

    @Override
    public void disable() {
        this.getInject(UserManager.class).ifPresent(UserManager::saveAll);
    }

    @Override
    public @NonNull DreamVersion getDreamVersion() {
        return DreamVersion.create("Dream-Szaman", "1.1.2", "eastcause");
    }


    @Override
    public @NonNull OkaeriSerdesPack getConfigSerdesPack() {
        return registry -> {
            registry.register(new BukkitNoticeSerdes());
            registry.register(new MenuBuilderSerdes());
            registry.register(new PerkUpgradeSerdes());
            registry.register(new PerkSerdes());
        };
    }

    @Override
    public @NonNull OkaeriSerdesPack getPersistenceSerdesPack() {
        return registry -> {
        };
    }

}
