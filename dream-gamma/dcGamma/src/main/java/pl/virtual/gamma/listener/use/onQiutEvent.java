package pl.virtual.gamma.listener.use;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;
import pl.virtual.gamma.listener.ListenerUse;
import pl.virtual.gamma.manager.User;

public class onQiutEvent extends ListenerUse {

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        User u = userManager.getOrCreateUser(p);
        if (u.isGamma())p.removePotionEffect(PotionEffectType.NIGHT_VISION);
    }
}
