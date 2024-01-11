package cc.dreamcode.pickaxe.user.controller;

import cc.dreamcode.pickaxe.user.UserRepository;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class UserController implements Listener {

    private final Tasker tasker;
    private final UserRepository userRepository;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        this.tasker.newChain()
                .async(() -> this.userRepository.findOrCreateByHumanEntity(player))
                .acceptSync(user -> {
                    user.setName(player.getName());
                })
                .acceptAsync(user -> {
                    user.save();
                })
                .execute();
    }

}
