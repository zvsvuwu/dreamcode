package pl.virtual.elytra.listener.use;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.virtual.elytra.listener.ListenerUse;

public class onMoveEvent extends ListenerUse {

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        Location to = e.getTo();
        Location from = e.getFrom();
        boolean fromAfk = dataUtil.isInRegion(from);
        boolean toAfk = dataUtil.isInRegion(to);
        if ((to.getBlockX() != from.getBlockX() || to.getBlockY() != from.getBlockY() || to.getBlockZ() != from.getBlockZ()) && !fromAfk && toAfk) {
            if (configPlugin.messageToStatus)message.message(p, configPlugin.messageTo.type, configPlugin.messageTo.message);
        }else if ((from.getBlockX() != to.getBlockX() || from.getBlockY() != to.getBlockY() || from.getBlockZ() != to.getBlockZ()) && fromAfk && !toAfk) {
            if (configPlugin.messageFromStatus)message.message(p, configPlugin.messageFrom.type, configPlugin.messageFrom.message);
        }

        if (p.hasPermission(configPlugin.permissionBypass))return;
        if (dataUtil.isInRegion(p.getLocation())){
            if (p.getInventory().getChestplate() == null)return;
            Material chestplate = p.getInventory().getChestplate().getType();
            if (chestplate.equals(Material.ELYTRA)){
                if(p.isGliding()){
                    p.setGliding(false);
                    message.message(p, configPlugin.message.type, configPlugin.message.message);
                }
            }
        }
    }
}
