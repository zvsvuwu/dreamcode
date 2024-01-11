package cc.dreamcode.codekeys.user;

import cc.dreamcode.codekeys.CodeKeysPlugin;
import cc.dreamcode.codekeys.config.PluginConfig;
import cc.dreamcode.platform.DreamLogger;
import cc.dreamcode.platform.bukkit.exception.BukkitPluginException;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.persistence.document.Document;
import eu.okaeri.tasker.core.Tasker;
import lombok.Getter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

public class UserManager {

    private @Inject UserRepository userRepository;
    private @Inject Tasker tasker;
    private @Inject DreamLogger dreamLogger;
    private @Inject CodeKeysPlugin codeKeysPlugin;
    private @Inject PluginConfig pluginConfig;

    private @Getter final Map<UUID, User> users = new HashMap<>();
    private @Getter final Map<String, UUID> uuids = new HashMap<>();
    private @Getter final Set<UUID> quit = new HashSet<>();


    public Optional<User> getUser(UUID uuid) {
        return Optional.ofNullable(this.users.get(uuid));
    }

    public Optional<User> getByName(String name) {
        Optional<UUID> optional = Optional.ofNullable(this.uuids.get(name.toLowerCase()));
        if (optional.isPresent()) {
            return getUser(optional.get());
        }
        return Optional.empty();
    }

    public void sync(Consumer<Exception> exceptionConsumer) {
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
                    this.users.clear();
                    users.forEach(user -> {
                        this.users.put(user.getUniqueId(), user);
                        this.uuids.put(user.getName().toLowerCase(), user.getUniqueId());
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
                    this.users.put(user.getUniqueId(), user);
                    this.uuids.put(user.getName().toLowerCase(), user.getUniqueId());

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

        Set<UUID> uuids = new HashSet<>(this.quit);
        this.quit.clear();

        this.codeKeysPlugin.getServer().getOnlinePlayers()
                .stream()
                .map(Entity::getUniqueId)
                .forEach(uuids::add);

        uuids.stream()
                .map(this::getUser)
                .filter(Objects::nonNull)
                .forEach(user -> user.ifPresent(Document::save));

        if (this.pluginConfig.debug) {
            this.dreamLogger.debug(new DreamLogger.Builder()
                    .type("Save database")
                    .name("users")
                    .meta("size", uuids.size())
                    .took(System.currentTimeMillis() - time)
                    .build());
        }
    }

}
