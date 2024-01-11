package xyz.ravis96.dreamfreeze.listeners.entity;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import xyz.ravis96.dreamfreeze.listeners.ListenerUse;

public class ProjectileLaunchListener extends ListenerUse {

    @EventHandler(ignoreCancelled = true)
    public void onProjectileLaunch(ProjectileLaunchEvent e) {
        if(this.pluginStorage.isFreeze() && !e.getEntity().hasPermission("dc.freeze.bypass")) {
            e.setCancelled(true);
        }
    }
}
