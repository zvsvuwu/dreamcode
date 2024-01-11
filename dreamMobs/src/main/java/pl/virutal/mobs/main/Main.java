package pl.virutal.mobs.main;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import lombok.Getter;
import org.bukkit.entity.Mob;
import org.bukkit.plugin.java.JavaPlugin;
import pl.virutal.mobs.command.CommandMap;
import pl.virutal.mobs.config.ConfigPlugin;
import pl.virutal.mobs.gui.GuiRegisterListener;
import pl.virutal.mobs.gui.use.MenuGui;
import pl.virutal.mobs.gui.use.MobGui;
import pl.virutal.mobs.listener.ListenerMap;
import pl.virutal.mobs.manager.UserManager;
import pl.virutal.mobs.util.Message;

import java.io.File;

public class Main extends JavaPlugin {

    @Getter public static Main plugin;
    @Getter public ConfigPlugin configPlugin;
    @Getter public Message message;
    @Getter public MenuGui menuGui;
    @Getter public MobGui mobGui;
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
        System.out.println("[" + this.getName() + "] Ladowanie utilow");
        message = new Message();
        System.out.println("[" + this.getName() + "] Ladowanie listenerow");
        new ListenerMap(this);
        new GuiRegisterListener().register(this);
        System.out.println("[" + this.getName() + "] Ladowanie gui");
        mobGui = new MobGui();
        menuGui = new MenuGui();
        System.out.println("[" + this.getName() + "] Ladowanie komend");
        new CommandMap(this);
        System.out.println(" ");
        System.out.println("[" + this.getName() + "] Plugin zostal uruchomiony poprawnie");
        System.out.println(" ");
    }

    public void onDisable() {
        configPlugin.save();
        plugin = null;
    }
}