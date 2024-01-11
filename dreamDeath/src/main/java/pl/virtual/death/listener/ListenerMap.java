package pl.virtual.death.listener;

import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import pl.virtual.death.listener.use.onDeathEvent;
import pl.virtual.death.listener.use.onRespawnEvent;
import pl.virtual.death.main.Main;

import java.util.stream.Stream;

public class ListenerMap {
    private final Main plugin;

    public ListenerMap(Main plugin) {
        this.plugin = plugin;
        Stream.of(
                new onDeathEvent(),
                new onRespawnEvent()
                ).forEach(this::registerListener);
    }

    public void registerListener(Listener listener) {
        PluginManager pm = this.plugin.getServer().getPluginManager();
        pm.registerEvents(listener, plugin);
    }
}
