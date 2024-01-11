package pl.virutal.mobs.gui;

import org.bukkit.plugin.java.JavaPlugin;

public final class GuiRegisterListener {

    public void register(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(new InventoryListener(), plugin);
    }
}
