package xyz.ravis96.dreamfreeze.listeners.entity;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import xyz.ravis96.dreamfreeze.listeners.ListenerUse;

public class EntityRegainHealthListener extends ListenerUse {

    @EventHandler(ignoreCancelled = true)
    public void onRegainHealth(EntityRegainHealthEvent e) {
        if(this.pluginStorage.isFreeze() && !e.getEntity().hasPermission("dc.freeze.bypass")) {
            e.setCancelled(true);
        }
    }
}
