package xyz.ravis96.dreamfreeze.listeners.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChatEvent;
import xyz.ravis96.dreamfreeze.listeners.ListenerUse;

public class PlayerChatListener extends ListenerUse {

    @EventHandler(ignoreCancelled = true)
    public void onChat(PlayerChatEvent e) {
        if(this.pluginStorage.isFreeze() && !e.getPlayer().hasPermission("dc.freeze.bypass")) {
            e.setCancelled(true);
        }
    }
}
