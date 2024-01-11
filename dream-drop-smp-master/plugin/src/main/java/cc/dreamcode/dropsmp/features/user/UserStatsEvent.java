package cc.dreamcode.dropsmp.features.user;

import cc.dreamcode.dropsmp.features.user.persistence.User;
import cc.dreamcode.dropsmp.features.user.persistence.UserRepository;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class UserStatsEvent implements Listener {

    private @Inject UserRepository repository;

    @EventHandler
    public void damageByMob(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            final Player player = (Player) e.getDamager();
            final User user = repository.get(player,false);
            e.setDamage(e.getFinalDamage() * (user.getResistance() / 100));
        }
    }

    @EventHandler
    public void resistance(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            final Player player = (Player) e.getEntity();
            final User user = repository.get(player,false);
            final double damage = (e.getFinalDamage() * ((user.getStrength() / 100) / (user.getResistance() / 1000)));
            e.setDamage(damage);
        }
    }
    @EventHandler
    public void mobDamae(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            final Player player = (Player) e.getDamager();
            final User user = repository.get(player,false);
            e.setDamage(e.getFinalDamage() * (user.getStrength() / 100));
        }
    }

    @EventHandler
    public void userJump(PlayerMoveEvent e) {
        final Location from = e.getFrom();
        final Location to = e.getTo();
        if (from.getY() < to.getY()) {
            final Player player = e.getPlayer();
            final User u = repository.get(player,false);

        }
    }
}
