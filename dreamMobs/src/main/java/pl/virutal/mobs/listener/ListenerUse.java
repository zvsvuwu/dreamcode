package pl.virutal.mobs.listener;

import org.bukkit.event.Listener;
import pl.virutal.mobs.config.ConfigPlugin;
import pl.virutal.mobs.main.Main;

public class ListenerUse implements Listener {
    public Main plugin = Main.getPlugin();
    public ConfigPlugin configPlugin = plugin.getConfigPlugin();
}
