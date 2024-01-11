package cc.dreamcode.shaman.user;

import cc.dreamcode.platform.DreamLogger;
import cc.dreamcode.platform.bukkit.exception.BukkitPluginException;
import cc.dreamcode.shaman.SzamanPlugin;
import cc.dreamcode.shaman.config.PluginConfig;
import cc.dreamcode.shaman.perk.PerkUpgrade;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.persistence.document.Document;
import eu.okaeri.tasker.core.Tasker;
import lombok.Getter;
import org.bukkit.Bukkit;
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
    private @Inject PluginConfig pluginConfig;
    private @Inject SzamanPlugin szamanPlugin;

    private final Map<UUID, User> users = new HashMap<>();
    private final Map<String, UUID> uuids = new HashMap<>();
    @Getter private final Set<UUID> quit = new HashSet<>();

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
                .acceptSync(user -> {
                    setHealth(user, player);
                    setSpeed(user, player);
                })
                .execute();
    }

    public void saveAll() {
        long time = System.currentTimeMillis();

        Set<UUID> uuids = new HashSet<>(this.quit);
        this.quit.clear();

        this.szamanPlugin.getServer().getOnlinePlayers()
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

    public void setHealth(User user, Player player) {
        int hp = user.getHealthLvl();
        if (hp > 0) {
            PerkUpgrade perkUpgrade = this.pluginConfig.perks.healthPerk.getUpgrades().getOrDefault(hp, null);
            if (perkUpgrade != null) {
                double v = perkUpgrade.getValue() * 2;
                player.setMaxHealth(20 + v);
            }
        }
        else {
            player.setMaxHealth(20.0);
        }
    }

    public void setSpeed(User user, Player player) {
        int speed = user.getSpeedLvl();
        if (speed > 0) {
            PerkUpgrade perkUpgrade = this.pluginConfig.perks.speedPerk.getUpgrades().getOrDefault(speed, null);
            if (perkUpgrade != null) {
                float percent = (float) perkUpgrade.getValue();
                float newSpeed = 0.2f + ((0.2f * percent) / 100.0F);
                if (newSpeed > 1.0) {
                    newSpeed = 1.0f;
                }
                player.setWalkSpeed(newSpeed);
            }
        }
        else {
            player.setWalkSpeed(0.2f);
        }
    }

    public void reloadPerks() {
        if (this.szamanPlugin.getServer().getOnlinePlayers().size() > 0) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                getUser(player.getUniqueId()).ifPresent(user -> {
                    setHealth(user, player);
                    setSpeed(user, player);
                });
            }
        }
    }

}
