package pl.virtual.death.listener;

import org.bukkit.event.Listener;
import pl.virtual.death.config.ConfigPlugin;
import pl.virtual.death.main.Main;
import pl.virtual.death.manager.UserManager;

public class ListenerUse implements Listener {
    public Main plugin = Main.getPlugin();
    public UserManager userManager = plugin.getUserManager();
    public ConfigPlugin configPlugin = plugin.getConfigPlugin();
}
