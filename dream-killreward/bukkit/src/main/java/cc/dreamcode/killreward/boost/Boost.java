package cc.dreamcode.killreward.boost;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Boost {
    private long totalTime;
    private long duration;
    private double chance;

    public Boost(long totalTime, long duration, double chance) {
        this.totalTime = totalTime;
        this.duration = duration;
        this.chance = chance;
    }
}
