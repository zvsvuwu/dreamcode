package cc.dreamcode.guisuffix.user;

import cc.dreamcode.platform.DreamLogger;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class UserFactory {

    private final UserCache userCache;

    private final Tasker tasker;

    private final DreamLogger dreamLogger;

    private final UserRepository userRepository;

    public void createUserIfNotExist(@NonNull Player player) {
        if (this.userCache.values().stream().anyMatch(user -> user.getUniqueId().equals(player.getUniqueId()))) {
            return;
        }

        this.tasker.newChain()
                .async(() -> {
                    User user = this.userRepository.findOrCreateByPath(player.getUniqueId());

                    user.setName(player.getName());
                    user.setUniqueId(player.getUniqueId());

                    if (user.getSuffixes() == null) {
                        user.setSuffixes(new ArrayList<>());
                    }

                    return user;
                })
                .acceptAsync(user -> {
                    user.save();
                })
                .abortIfException(e -> this.dreamLogger.error("Cannot save user data to database.", e))
                .acceptAsync(this.userCache::add)
                .execute();
    }

    public void createOnline() {
        Bukkit.getOnlinePlayers().forEach(this::createUserIfNotExist);
    }
}
