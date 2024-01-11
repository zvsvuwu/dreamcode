package cc.dreamcode.shulker.cooldown;

import cc.dreamcode.utilities.CountUtil;
import lombok.NonNull;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CooldownManager {

    private final List<Cooldown> cooldownList = new ArrayList<>();

    public void addCooldown(@NonNull UUID uuid, @NonNull CooldownType cooldownType, @NonNull Duration duration) {
        final Cooldown cooldown = new Cooldown(uuid, cooldownType, System.currentTimeMillis(), duration);
        this.cooldownList.add(cooldown);
    }

    public void removeCooldown(@NonNull UUID uuid, @NonNull CooldownType cooldownType) {
        this.cooldownList.removeIf(cooldown -> cooldown.getUuid().equals(uuid) &&
                cooldown.getCooldownType().equals(cooldownType));
    }

    /**
     * @return empty, when player has no cooldown.
     */
    public Optional<Cooldown> getCooldown(@NonNull UUID uuid, @NonNull CooldownType cooldownType) {
        return this.cooldownList.stream()
                .filter(cooldown -> cooldown.getCooldownType().equals(cooldownType))
                .filter(cooldown -> cooldown.getUuid().equals(uuid))
                .filter(cooldown -> CountUtil.getCountDown(cooldown.getStart(), cooldown.getDuration()).toMillis() > 0L)
                .findAny();
    }

    public void removeAllWhenComplete() {
        this.cooldownList.removeIf(cooldown -> CountUtil.getCountDown(cooldown.getStart(), cooldown.getDuration()).toMillis() <= 0);
    }
}
