package pl.virtual.death.listener.use;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;
import pl.virtual.death.listener.ListenerUse;
import pl.virtual.death.manager.User;

public class onRespawnEvent extends ListenerUse {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        Player p = e.getPlayer();
        User u = userManager.getOrCreateUser(p);
        if (u.getInventory() != null) {
            p.getInventory().setContents(u.getInventory());
        }
        u.setInventory(null);
    }
}
