package cc.dreamcode.shulker.cooldown;

import cc.dreamcode.utilities.CountUtil;
import cc.dreamcode.utilities.TimeUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class Cooldown {

    private final UUID uuid;
    private final CooldownType cooldownType;
    private final long start;
    private final Duration duration;

    public String getFormattedTime() {
        return TimeUtil.convertDurationMills(CountUtil.getCountDown(this.start, this.duration));
    }

}
