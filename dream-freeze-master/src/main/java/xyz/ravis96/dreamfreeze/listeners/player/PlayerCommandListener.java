package xyz.ravis96.dreamfreeze.listeners.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import xyz.ravis96.dreamfreeze.config.subconfig.FreezeConfig;
import xyz.ravis96.dreamfreeze.listeners.ListenerUse;

public class PlayerCommandListener extends ListenerUse {

    @EventHandler(ignoreCancelled = true)
    public void onCommand(PlayerCommandPreprocessEvent e) {
        final String command = e.getMessage().split(" ")[0].replace("/", "");

        if(this.pluginStorage.isFreeze() && !e.getPlayer().hasPermission("dc.freeze.bypass")) {
            final FreezeConfig freezeConfig = this.pluginConfig.getFreezeConfig();

            if(!freezeConfig.legalCommandList.contains(command)) {
                e.setCancelled(true);
            }
        }
    }
}
