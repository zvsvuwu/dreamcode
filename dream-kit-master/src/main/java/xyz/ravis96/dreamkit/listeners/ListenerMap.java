package xyz.ravis96.dreamkit.listeners;

import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import xyz.ravis96.dreamkit.PluginMain;
import xyz.ravis96.dreamkit.features.menu.MenuActionHandler;

import java.util.stream.Stream;

public class ListenerMap {
    private final PluginMain plugin;

    public ListenerMap(PluginMain plugin) {
        this.plugin = plugin;

        Stream.of(
                new MenuActionHandler()
        ).forEach(this::registerListener);
    }

    public void registerListener(Listener listener) {
        PluginManager pm = this.plugin.getServer().getPluginManager();
        try {
            pm.registerEvents(this.plugin.getInjector().newInstanceWithFields(listener.getClass()), plugin);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
