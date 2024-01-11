package pl.virtual.gamma.listener.use;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.virtual.gamma.listener.ListenerUse;
import pl.virtual.gamma.manager.User;

public class onJoinEvent extends ListenerUse {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        User u = userManager.getOrCreateUser(p);
        if (u.isGamma())p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999999, 1));
    }
}
