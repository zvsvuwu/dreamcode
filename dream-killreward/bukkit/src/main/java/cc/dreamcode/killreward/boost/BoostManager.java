package cc.dreamcode.killreward.boost;

import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class BoostManager {

    private final Map<UUID, Boost> boostMap = new HashMap<>();
    private final Map<UUID, BossBar> bossBarMap = new HashMap<>();

    public void addBoost(Player player, Boost boost) {
        this.boostMap.put(player.getUniqueId(), boost);
    }

    public void addBoostToAll(Boost boost) {
        Bukkit.getOnlinePlayers().forEach(player -> this.boostMap.put(player.getUniqueId(), boost));
    }

    public void removeBoost(Player player) {
        this.boostMap.remove(player.getUniqueId());
    }

    public Optional<Boost> getBoost(Player player) {
        return Optional.ofNullable(this.boostMap.get(player.getUniqueId()));
    }

    public void addBossBar(Player player, BossBar bossBar) {
        this.bossBarMap.put(player.getUniqueId(), bossBar);
        bossBar.addPlayer(player);
    }

    public boolean hasBossBar(Player player) {
        return this.bossBarMap.containsKey(player.getUniqueId());
    }

    public void removeBossBar(Player player) {
        this.getBossBar(player).removePlayer(player);
        this.bossBarMap.remove(player.getUniqueId());
    }

    public BossBar getBossBar(Player player) {
        return this.bossBarMap.get(player.getUniqueId());
    }
}
