package xyz.ravis96.dreamfreeze.listeners.entity;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import xyz.ravis96.dreamfreeze.listeners.ListenerUse;

public class FoodLevelChangeListener extends ListenerUse {

    @EventHandler(ignoreCancelled = true)
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        if(this.pluginStorage.isFreeze() && !e.getEntity().hasPermission("dc.freeze.bypass")) {
            e.setCancelled(true);
        }
    }
}
