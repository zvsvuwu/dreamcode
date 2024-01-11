package cc.dreamcode.killreward.manager;

import cc.dreamcode.killreward.KillRewardCooldown;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CooldownManager {

    private final List<KillRewardCooldown> killRewardCooldowns = new ArrayList<>();

    public void setCooldown(Player killer, Player killedPlayer, long cooldown) {
        UUID killerUUID = killer.getUniqueId();
        UUID killedPlayerUUID = killedPlayer.getUniqueId();
        this.killRewardCooldowns.add(new KillRewardCooldown(killerUUID, killedPlayerUUID, System.currentTimeMillis() + cooldown));
    }

    public boolean hasCooldown(Player killer, Player killedPlayer) {
        UUID killerUUID = killer.getUniqueId();
        UUID killedPlayerUUID = killedPlayer.getUniqueId();

        for (KillRewardCooldown cooldown : killRewardCooldowns) {
            if (cooldown.getKillerUuid().equals(killerUUID) && cooldown.getKilledPlayerUuid().equals(killedPlayerUUID)) {
                long currentTime = System.currentTimeMillis();
                long cooldownTime = cooldown.getCooldown();

                return currentTime < cooldownTime;
            }
        }
        return false;
    }

    public long getRemainingTime(Player killer, Player killedPlayer) {
        UUID killerUUID = killer.getUniqueId();
        UUID killedPlayerUUID = killedPlayer.getUniqueId();

        for (KillRewardCooldown cooldown : killRewardCooldowns) {
            if (cooldown.getKillerUuid().equals(killerUUID) && cooldown.getKilledPlayerUuid().equals(killedPlayerUUID)) {
                long currentTime = System.currentTimeMillis();
                long cooldownEndTime = cooldown.getCooldown();

                if (cooldownEndTime > currentTime) {
                    return cooldownEndTime - currentTime;
                }
            }
        }
        return 0;
    }
}
