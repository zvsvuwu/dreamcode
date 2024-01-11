package cc.dreamcode.tops.user;

import cc.dreamcode.platform.DreamLogger;
import cc.dreamcode.platform.bukkit.exception.BukkitPluginException;
import cc.dreamcode.tops.config.PluginConfig;
import cc.dreamcode.tops.user.top.UserData;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@NoArgsConstructor
public class UserManager {

    private @Inject DreamLogger dreamLogger;
    private @Inject PluginConfig pluginConfig;
    private @Inject UserRepository userRepository;
    private @Inject Tasker tasker;

    private final Map<UUID, User> userMap = new HashMap<>();

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

    public Collection<User> getUsers() {
        return this.userMap.values();
    }

    public User getUserByPlayer(@NonNull Player player) {
        if (!this.userMap.containsKey(player.getUniqueId())) {
            this.createUser(player);

            throw new BukkitPluginException("User not cached");
        }

        return this.userMap.get(player.getUniqueId());
    }

    public Optional<User> getUserByUuid(@NonNull UUID uuid) {
        return Optional.ofNullable(this.userMap.get(uuid));
    }

    public Optional<User> getUserByName(@NonNull String name) {
        return this.userMap.values()
                .stream()
                .filter(user -> user.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public void createUser(@NonNull Player player) {
        if (this.userMap.containsKey(player.getUniqueId())) {
            return;
        }

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

                    if (this.pluginConfig.debug) {
                        this.dreamLogger.debug(new DreamLogger.Builder()
                                .type("Created new user")
                                .name(user.getName())
                                .meta("uuid", user.getUniqueId().toString())
                                .build());
                    }
                })
                .execute();
    }

    public void saveAll() {
        long time = System.currentTimeMillis();
        this.userMap.forEach((uuid, user) -> user.save());

        if (this.pluginConfig.debug) {
            this.dreamLogger.debug(new DreamLogger.Builder()
                    .type("Save database")
                    .name("users")
                    .meta("size", this.userMap.size())
                    .took(System.currentTimeMillis() - time)
                    .build());
        }
    }

    public List<User> getUsersByTop(@NonNull UserData data, int limit) {
        return this.userMap.values()
                .stream()
                .sorted(Comparator.comparingDouble(data.getFunction()).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }


}