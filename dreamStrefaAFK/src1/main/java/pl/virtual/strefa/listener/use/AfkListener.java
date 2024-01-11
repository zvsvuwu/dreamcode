package pl.virtual.strefa.listener.use;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.virtual.strefa.listener.ListenerUse;
import pl.virtual.strefa.manager.User;

public class AfkListener extends ListenerUse {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        User u = userManager.getOrCreateUser(p);
        userManager.resetTimer(u);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        if (!configPlugin.moveEvent)return;
        Player p = e.getPlayer();
        if (configPlugin.ignoreEventInRegion)if (dataUtil.isInAntyAfk(p.getLocation()))return;
        User u = userManager.getOrCreateUser(p);
        userManager.resetTimer(u);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        if (!configPlugin.breakEvent)return;
        Player p = e.getPlayer();
        if (configPlugin.ignoreEventInRegion)if (dataUtil.isInAntyAfk(p.getLocation()))return;
        User u = userManager.getOrCreateUser(p);
        userManager.resetTimer(u);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        if (!configPlugin.placeEvent)return;
        Player p = e.getPlayer();
        if (configPlugin.ignoreEventInRegion)if (dataUtil.isInAntyAfk(p.getLocation()))return;
        User u = userManager.getOrCreateUser(p);
        userManager.resetTimer(u);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if (!configPlugin.damageEvent)return;
        if (e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            if (configPlugin.ignoreEventInRegion)if (dataUtil.isInAntyAfk(p.getLocation()))return;
            User u = userManager.getOrCreateUser(p);
            userManager.resetTimer(u);
        }
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (configPlugin.ignoreEventInRegion)if (dataUtil.isInAntyAfk(p.getLocation()))return;
            User u = userManager.getOrCreateUser(p);
            userManager.resetTimer(u);
        }
    }
}
