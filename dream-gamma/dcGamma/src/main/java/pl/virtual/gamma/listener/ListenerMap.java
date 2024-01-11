package pl.virtual.gamma.listener;

import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import pl.virtual.gamma.listener.use.onJoinEvent;
import pl.virtual.gamma.main.Main;

import java.util.stream.Stream;

public class ListenerMap {
    private final Main plugin;

    public ListenerMap(Main plugin) {
        this.plugin = plugin;
        Stream.of(
                new onJoinEvent()
        ).forEach(this::registerListener);
    }

    public void registerListener(Listener listener) {
        PluginManager pm = this.plugin.getServer().getPluginManager();
        pm.registerEvents(listener, plugin);
    }
}
