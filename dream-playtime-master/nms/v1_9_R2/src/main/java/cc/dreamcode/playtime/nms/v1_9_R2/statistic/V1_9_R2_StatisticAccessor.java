package cc.dreamcode.playtime.nms.v1_9_R2.statistic;

import cc.dreamcode.playtime.nms.api.statistic.StatisticAccessor;
import lombok.NonNull;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public class V1_9_R2_StatisticAccessor implements StatisticAccessor {
    @Override
    public long getPlayOneTick(@NonNull Player player) {
        return player.getStatistic(Statistic.PLAY_ONE_TICK);
    }
}
