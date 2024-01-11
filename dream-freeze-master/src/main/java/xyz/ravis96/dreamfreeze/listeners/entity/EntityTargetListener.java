package xyz.ravis96.dreamfreeze.listeners.entity;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityTargetEvent;
import xyz.ravis96.dreamfreeze.listeners.ListenerUse;

public class EntityTargetListener extends ListenerUse {

    @EventHandler(ignoreCancelled = true)
    public void onEntityTarget(EntityTargetEvent e) {
        if(this.pluginStorage.isFreeze()) {
            e.setCancelled(true);
        }
    }
}
