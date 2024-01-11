package cc.dreamcode.anticobweb.manager;

import cc.dreamcode.anticobweb.config.PluginConfig;
import cc.dreamcode.utilities.CountUtil;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class CooldownManager {

    private @Inject PluginConfig pluginConfig;
    private final Map<UUID, Instant> cooldown = new HashMap<>();

    public boolean isOnCooldown(@NonNull UUID uuid) {
        return getCooldown(uuid).toNanos() > 0;
    }

    public @NonNull Duration getCooldown(@NonNull UUID uuid) {
        Duration configCooldown = this.pluginConfig.cooldown;
        final Instant instant = this.cooldown.computeIfAbsent(uuid, u -> Instant.now().minusNanos(configCooldown.toNanos()));
        return CountUtil.getCountDown(instant, configCooldown.toMillis());
    }

    public void resetCooldown(@NonNull UUID uuid) {
        if (!this.cooldown.containsKey(uuid)) {
            return;
        }
        this.cooldown.put(uuid, Instant.now());
    }
}
