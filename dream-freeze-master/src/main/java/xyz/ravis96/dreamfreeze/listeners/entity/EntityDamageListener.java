package xyz.ravis96.dreamfreeze.listeners.entity;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import xyz.ravis96.dreamfreeze.listeners.ListenerUse;

public class EntityDamageListener extends ListenerUse {

    @EventHandler(ignoreCancelled = true)
    public void onDamage(EntityDamageEvent e) {
        if(this.pluginStorage.isFreeze()) {
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent e) {
        if(this.pluginStorage.isFreeze()) {
            e.setCancelled(true);
        }
    }
}
