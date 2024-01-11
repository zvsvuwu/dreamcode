package cc.dreamcode.ccl.user;

import cc.dreamcode.ccl.CommandsCooldownPlugin;
import cc.dreamcode.ccl.config.PluginConfig;
import cc.dreamcode.platform.DreamLogger;
import cc.dreamcode.platform.bukkit.exception.BukkitPluginException;
import cc.dreamcode.utilities.ParseUtil;
import cc.dreamcode.utilities.optional.CustomOptional;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public class UserManager {

    private @Inject UserRepository userRepository;
    private @Inject Tasker tasker;
    private @Inject DreamLogger dreamLogger;
    private @Inject CommandsCooldownPlugin commandsCooldownPlugin;
    private @Inject PluginConfig pluginConfig;

    private @Getter final Map<UUID, User> users = new HashMap<>();

    public Optional<User> getUser(UUID uuid) {
        return Optional.ofNullable(this.users.get(uuid));
    }

    public void sync(Consumer<Exception> exceptionConsumer) {
        final long time = System.currentTimeMillis();
        this.tasker.newSharedChain("syncUsers")
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
                    this.users.clear();
                    users.forEach(user -> {
                        this.users.put(user.getUniqueId(), user);
                    });

                    this.dreamLogger.info(new DreamLogger.Builder()
                            .type("Synchronized database")
                            .name("users")
                            .meta("size", this.users.size())
                            .meta("time", System.currentTimeMillis() - time + "ms")
                            .build());
                })
                .execute();
    }

    public void createUser(Player player) {
        if (this.users.containsKey(player.getUniqueId())) {
            return;
        }

        this.tasker.newSharedChain("usercreate:" + player.getUniqueId().toString())
                .async(() -> {
                    final User user = this.userRepository.findOrCreateByPath(player.getUniqueId());
                    user.setCool_down(new HashMap<>());
                    return user;
                })
                .acceptAsync(user -> {
                    user.save();
                })
                .abortIfException(e -> this.dreamLogger.error("Cannot save user data to database.", e))
                .acceptAsync(user -> {
                    this.users.put(user.getUniqueId(), user);

                    if (this.pluginConfig.debug) {
                        this.dreamLogger.debug(new DreamLogger.Builder()
                                .type("Created new user")
                                .name("")
                                .meta("uuid", user.getUniqueId().toString())
                                .build());
                    }
                })
                .execute();
    }

    public void save(User user) {
        this.tasker.newSharedChain("save:" + user.getUniqueId())
                .async(() -> {
                    user.save();
                })
                .execute();
    }

}
