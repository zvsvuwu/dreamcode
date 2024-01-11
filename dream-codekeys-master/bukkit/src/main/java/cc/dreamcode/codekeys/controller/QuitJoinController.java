package cc.dreamcode.codekeys.controller;

import cc.dreamcode.codekeys.user.UserManager;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class QuitJoinController implements Listener {

    private @Inject UserManager userManager;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        this.userManager.createUser(player);
    }

}
