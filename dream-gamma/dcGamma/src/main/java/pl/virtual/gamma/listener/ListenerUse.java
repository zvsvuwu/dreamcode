package pl.virtual.gamma.listener;

import org.bukkit.event.Listener;
import pl.virtual.gamma.main.Main;
import pl.virtual.gamma.manager.UserManager;

public class ListenerUse implements Listener {
    public Main plugin = Main.getPlugin();
    public UserManager userManager = plugin.getUserManager();
}
