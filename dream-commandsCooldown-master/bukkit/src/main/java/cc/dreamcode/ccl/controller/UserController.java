package cc.dreamcode.ccl.controller;

import cc.dreamcode.ccl.CommandsCooldownPlugin;
import cc.dreamcode.ccl.user.UserManager;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Example usage of user repository.
 * Register controller in plugin component system.
 */
public class UserController implements Listener {

    private @Inject CommandsCooldownPlugin commandsCooldownPlugin;
    private @Inject UserManager userManager;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        final Player player = e.getPlayer();
        this.userManager.createUser(player);
    }

}
