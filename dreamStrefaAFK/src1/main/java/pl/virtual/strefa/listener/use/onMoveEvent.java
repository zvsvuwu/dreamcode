package pl.virtual.strefa.listener.use;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.virtual.strefa.listener.ListenerUse;

public class onMoveEvent extends ListenerUse {

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        Location to = e.getTo();
        Location from = e.getFrom();
        boolean fromAfk = dataUtil.isInAntyAfk(from);
        boolean toAfk = dataUtil.isInAntyAfk(to);
        if (configPlugin.ignoreEventInRegion)if (dataUtil.isInAntyAfk(p.getLocation()))return;
        if ((to.getBlockX() != from.getBlockX() || to.getBlockY() != from.getBlockY() || to.getBlockZ() != from.getBlockZ()) && !fromAfk && toAfk) {
            if (configPlugin.messageToStatus)message.message(p, configPlugin.messageTo.type, configPlugin.messageTo.message);
        }else if ((from.getBlockX() != to.getBlockX() || from.getBlockY() != to.getBlockY() || from.getBlockZ() != to.getBlockZ()) && fromAfk && !toAfk) {
            if (configPlugin.messageFromStatus)message.message(p, configPlugin.messageFrom.type, configPlugin.messageFrom.message);
        }
    }
}
