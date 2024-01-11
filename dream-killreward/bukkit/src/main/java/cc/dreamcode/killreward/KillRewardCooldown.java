package cc.dreamcode.killreward;

import lombok.Getter;

import java.util.UUID;

@Getter
public class KillRewardCooldown {

    private final UUID killerUuid;
    private final UUID killedPlayerUuid;
    private final long cooldown;

    public KillRewardCooldown(final UUID killerUuid, final UUID killedPlayerUuid, final long cooldown) {
        this.killerUuid = killerUuid;
        this.killedPlayerUuid = killedPlayerUuid;
        this.cooldown = cooldown;
    }
}
