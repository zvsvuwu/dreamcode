package xyz.ravis96.dreamfreeze.listeners;

import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import xyz.ravis96.dreamfreeze.PluginMain;
import xyz.ravis96.dreamfreeze.listeners.block.BlockBreakListener;
import xyz.ravis96.dreamfreeze.listeners.block.BlockPlaceListener;
import xyz.ravis96.dreamfreeze.listeners.entity.*;
import xyz.ravis96.dreamfreeze.listeners.inventory.InventoryClickListener;
import xyz.ravis96.dreamfreeze.listeners.player.*;

import java.util.stream.Stream;

public class ListenerMap {
    private final PluginMain plugin;

    public ListenerMap(PluginMain plugin) {
        this.plugin = plugin;

        Stream.of(
                new PlayerMoveListener(),
                new PlayerInteractListener(),
                new PlayerChatListener(),
                new PlayerCommandListener(),
                new EntityDamageListener(),
                new PlayerInteractEntityListener(),
                new PlayerPickupItemListener(),
                new PlayerDropItemListener(),
                new InventoryClickListener(),
                new BlockPlaceListener(),
                new BlockBreakListener(),
                new EntityRegainHealthListener(),
                new EntityTargetListener(),
                new FoodLevelChangeListener(),
                new ProjectileLaunchListener()
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
