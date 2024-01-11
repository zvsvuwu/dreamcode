package cc.dreamcode.home.manager;

import cc.dreamcode.home.user.User;
import cc.dreamcode.home.user.UserRepository;
import cc.dreamcode.platform.DreamLogger;
import cc.dreamcode.platform.bukkit.exception.BukkitPluginException;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class UserManager {
    private @Inject DreamLogger dreamLogger;
    private @Inject UserRepository userRepository;
    private @Inject Tasker tasker;

    private @Getter final Map<UUID, User> userMap = new HashMap<>();

    public void syncUserInMemoryDatabase(Consumer<Exception> exceptionConsumer) {
        final long time = System.currentTimeMillis();
        this.tasker.newChain()
                .async(() -> {
                    try {
                        return this.userRepository.findAll();
                    }
                    catch (Exception e) {
                        exceptionConsumer.accept(e);
                        throw new BukkitPluginException("Cannot load user-repository data.", e);
                    }
                })
                .acceptAsync(users -> {
                    this.userMap.clear();
                    users.forEach(user -> this.userMap.put(user.getUniqueId(), user));

                    this.dreamLogger.info(new DreamLogger.Builder()
                            .type("Synchronized database")
                            .name("users")
                            .meta("size", this.userMap.size())
                            .meta("time", System.currentTimeMillis() - time + "ms")
                            .build());
                })
                .execute();
    }

    public User getUserByPlayer(Player player) {
        if (!this.userMap.containsKey(player.getUniqueId())) {
            this.createUser(player);

            throw new BukkitPluginException("User not cached");
        }

        return this.userMap.get(player.getUniqueId());
    }

    public void createUser(Player player) {
        this.tasker.newChain()
                .async(() -> {
                    final User user = this.userRepository.findOrCreateByPath(player.getUniqueId());
                    user.setName(player.getName());

                    return user;
                })
                .acceptAsync(user -> {
                    user.save();
                })
                .abortIfException(e -> this.dreamLogger.error("Cannot save user data to database.", e))
                .acceptAsync(user -> {
                    this.userMap.put(user.getUniqueId(), user);
                })
                .execute();
    }
}
