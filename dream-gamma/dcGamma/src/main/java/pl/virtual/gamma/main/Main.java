package pl.virtual.gamma.main;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import pl.virtual.gamma.command.CommandMap;
import pl.virtual.gamma.config.ConfigPlugin;
import pl.virtual.gamma.listener.ListenerMap;
import pl.virtual.gamma.manager.UserManager;
import pl.virtual.gamma.util.Message;

import java.io.File;

public class Main extends JavaPlugin {

    @Getter public static Main plugin;
    @Getter public ConfigPlugin configPlugin;
    @Getter public UserManager userManager;
    @Getter public Message message;

    public void onEnable() {
        Main.plugin = this;
        System.out.println("[" + this.getName() + "] Ladowanie configu");
        try {
            this.configPlugin = ConfigManager.create(ConfigPlugin.class, (it) -> {
                it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
                it.withBindFile(new File(this.getDataFolder(), "message.yml"));
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
        System.out.println("[" + this.getName() + "] Ladowanie uzytkownikow");
        userManager = new UserManager();
        System.out.println("[" + this.getName() + "] Ladowanie listenerow");
        new ListenerMap(this);
        System.out.println("[" + this.getName() + "] Ladowanie komend");
        new CommandMap(this);
        System.out.println(" ");
        System.out.println("[" + this.getName() + "] Plugin zostal uruchomiony poprawnie");
        System.out.println(" ");
    }

    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(all -> all.removePotionEffect(PotionEffectType.NIGHT_VISION));
        plugin = null;
    }
}
