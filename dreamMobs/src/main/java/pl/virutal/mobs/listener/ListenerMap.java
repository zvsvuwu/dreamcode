package pl.virutal.mobs.listener;

import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import pl.virutal.mobs.listener.use.onMobSpawnEvent;
import pl.virutal.mobs.main.Main;

import java.util.stream.Stream;

public class ListenerMap {
    private final Main plugin;

    public ListenerMap(Main plugin) {
        this.plugin = plugin;
        Stream.of(
                new onMobSpawnEvent()
                ).forEach(this::registerListener);
    }

    public void registerListener(Listener listener) {
        PluginManager pm = this.plugin.getServer().getPluginManager();
        pm.registerEvents(listener, plugin);
    }
}
