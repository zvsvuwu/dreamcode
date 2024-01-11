package cc.dreamcode.wallet.user;

import cc.dreamcode.platform.DreamLogger;
import cc.dreamcode.platform.bukkit.exception.BukkitPluginException;
import cc.dreamcode.utilities.TimeUtil;
import cc.dreamcode.wallet.config.PluginConfig;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.HumanEntity;

import java.util.*;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class UserManager {

    private final DreamLogger dreamLogger;
    private final PluginConfig pluginConfig;
    private final UserRepository userRepository;
    private final Tasker tasker;

    private final Map<UUID, User> userMap = new HashMap<>();

    /**
     * Import from database all users into cached user-map.
     */
    public void readUsers() {
        final long startTime = System.currentTimeMillis();

        this.tasker.newChain()
                .async(this.userRepository::findAll)
                .acceptAsync(users -> {
                    this.userMap.clear();
                    users.forEach(user -> this.userMap.put(user.getUniqueId(), user));

                    final long endTime = System.currentTimeMillis();
                    this.dreamLogger.debug(new DreamLogger.Builder()
                            .type("Read database")
                            .name("users")
                            .meta("size", this.userMap.size())
                            .meta("time", TimeUtil.convertMills(endTime - startTime))
                            .build());
                })
                .execute();
    }

    public List<User> getUserList() {
        return new ArrayList<>(this.userMap.values());
    }

    public Optional<User> getUserByUuid(@NonNull UUID uuid) {
        return Optional.ofNullable(this.userMap.get(uuid));
    }

    public Optional<User> getUserByName(@NonNull String name, boolean ignoreCase) {
        return this.userMap.values()
                .stream()
                .filter(user -> ignoreCase
                        ? user.getName().equalsIgnoreCase(name)
                        : user.getName().equals(name))
                .findAny();
    }

    public Optional<User> getUserByName(@NonNull String name) {
        return this.getUserByName(name, false);
    }

    public User getUserByPlayer(@NonNull HumanEntity entity) {
        final Optional<User> optionalUser = this.getUserByUuid(entity.getUniqueId());
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }

        throw new BukkitPluginException("User not cached (" + entity.getName() + ")");
    }

    public void createUser(@NonNull HumanEntity entity) {
        this.createUser(entity.getUniqueId(), entity.getName());
    }

    public void createUser(@NonNull UUID uuid, @NonNull String name) {
        if (this.userMap.containsKey(uuid)) {
            return;
        }

        // shared-chain with dbops key is queue for reading and writing document into database (async)
        this.tasker.newSharedChain("dbops:" + uuid)
                .async(() -> this.userRepository.findOrCreate(uuid, name))
                .acceptSync(user -> {
                    user.setName(name);
                    this.userMap.put(user.getUniqueId(), user);

                    if (this.pluginConfig.debug) {
                        this.dreamLogger.debug(new DreamLogger.Builder()
                                .type("Create document")
                                .name("User")
                                .meta("uuid", user.getUniqueId().toString())
                                .meta("name", user.getName())
                                .build());
                    }
                })
                .execute();
    }

}
