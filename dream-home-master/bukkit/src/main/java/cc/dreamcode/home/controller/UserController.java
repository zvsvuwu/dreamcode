package cc.dreamcode.home.controller;

import cc.dreamcode.home.manager.UserManager;
import cc.dreamcode.home.user.UserRepository;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.bukkit.BukkitTasker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class UserController implements Listener {
    private @Inject UserManager userManager;
    private @Inject BukkitTasker tasker;
    private @Inject UserRepository userRepository;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        this.tasker.newChain()
                .async(() -> this.userRepository.findOrCreateByHumanEntity(event.getPlayer()))
                .acceptAsync(user -> {
                    this.userManager.createUser(event.getPlayer());
                })
                .execute();
    }

}
