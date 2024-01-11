package pl.virtual.death.main;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import pl.virtual.death.config.ConfigPlugin;
import pl.virtual.death.listener.ListenerMap;
import pl.virtual.death.manager.UserManager;

import java.io.File;

public class Main extends JavaPlugin {

    @Getter public static Main plugin;

    @Getter public ConfigPlugin configPlugin;
    @Getter public UserManager userManager;

    public void onEnable() {
        Main.plugin = this;
        System.out.println("[" + this.getName() + "] Ladowanie configu");
        try {
            this.configPlugin = ConfigManager.create(ConfigPlugin.class, (it) -> {
                it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
                it.withBindFile(new File(this.getDataFolder(), "config.yml"));
                it.saveDefaults();
                it.load(true);
            });
        } catch (Exception exception) {
            System.out.println("[" + this.getName() + "] Blad podczas ladowania config'ow.");
            exception.printStackTrace();
            this.getPluginLoader().disablePlugin(this);
            return;
        }
        System.out.println("[" + this.getName() + "] Ladowanie uzytkownikow");
        userManager = new UserManager();
        System.out.println("[" + this.getName() + "] Ladowanie listenerow");
        new ListenerMap(this);
        System.out.println(" ");
        System.out.println("[" + this.getName() + "] Plugin zostal uruchomiony poprawnie");
        System.out.println(" ");
    }

    public void onDisable() {
        plugin = null;
    }
}