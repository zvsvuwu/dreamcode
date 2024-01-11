package cc.dreamcode.spawn;

import cc.dreamcode.spawn.config.PluginConfig;
import eu.okaeri.configs.exception.OkaeriException;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class SpawnManager {

    private final PluginConfig config;
    private final Map<UUID, Long> teleport = new HashMap<>();

    public void addTeleport(Player player, long time) {
        teleport.put(player.getUniqueId(), time);
    }

    public void removeTeleport(Player player) {
        teleport.remove(player.getUniqueId());
    }

    public Optional<Long> getTeleport(Player player) {
        return Optional.ofNullable(teleport.get(player.getUniqueId()));
    }

    public Location getSpawnLocation() {
        return config.spawnLocation;
    }

    public void setSpawnLocation(Location location) {
        this.config.spawnLocation = location;
        try {
            this.config.save();
        } catch (OkaeriException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public boolean isPlayerTeleporting(Player player) {
        return getTeleport(player).map(teleport -> teleport > System.currentTimeMillis()).orElse(false);
    }
}
