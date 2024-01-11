package pl.virtual.strefa.main;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import lombok.Getter;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import pl.virtual.strefa.command.CommandMap;
import pl.virtual.strefa.config.ConfigPlugin;
import pl.virtual.strefa.listener.ListenerMap;
import pl.virtual.strefa.manager.UserManager;
import pl.virtual.strefa.task.TaskMap;
import pl.virtual.strefa.util.DataUtil;
import pl.virtual.strefa.util.Message;

import java.io.File;

public class Main extends JavaPlugin {

    @Getter private static Main plugin;
    @Getter private ConfigPlugin configPlugin;
    @Getter private UserManager userManager;
    @Getter private DataUtil dataUtil;
    @Getter private Message message;

    public void onEnable() {
        plugin = this;

        PluginDescriptionFile p = this.getDescription();
        if (!p.getAuthors().contains("Virtual343")) {
            this.getPluginLoader().disablePlugin(this);
            for (int i = 1; i <= 10; i++) {
                System.out.println("[" + this.getName() + "] Nie zmieniaj nazwy autora");
            }
            return;
        }
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
        System.out.println("[" + this.getName() + "] Ladowanie utilow");
        message = new Message();
        dataUtil = new DataUtil();
        System.out.println("[" + this.getName() + "] Ladowanie uzytkowniko");
        userManager = new UserManager();
        System.out.println("[" + this.getName() + "] Ladowanie komend");
        new CommandMap(this);
        System.out.println("[" + this.getName() + "] Ladowanie listenerow");
        new ListenerMap(this);
        System.out.println("[" + this.getName() + "] Ladowanie taskow");
        new TaskMap(this);
        System.out.println(" ");
        System.out.println("[" + this.getName() + "] Plugin zostal uruchomiony poprawnie");
        System.out.println(" ");
    }

    public void onDisable() {
        plugin = null;
    }
}