package cc.dreamcode.tops.controller;


import cc.dreamcode.tops.user.UserManager;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class UserController implements Listener {
    private @Inject UserManager userManager;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        final Player player = e.getPlayer();
        this.userManager.createUser(player);
    }
}
