package cc.dreamcode.guisuffix.controller;

import cc.dreamcode.guisuffix.user.UserFactory;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class UserController implements Listener {

    @Inject
    private UserFactory userFactory;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        this.userFactory.createUserIfNotExist(player);
    }
}
