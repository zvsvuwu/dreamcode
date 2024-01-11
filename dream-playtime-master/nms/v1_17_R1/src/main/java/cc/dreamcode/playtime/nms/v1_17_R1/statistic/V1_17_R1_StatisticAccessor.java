package cc.dreamcode.playtime.nms.v1_17_R1.statistic;

import cc.dreamcode.playtime.nms.api.statistic.StatisticAccessor;
import lombok.NonNull;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public class V1_17_R1_StatisticAccessor implements StatisticAccessor {
    @Override
    public long getPlayOneTick(@NonNull Player player) {
        return player.getStatistic(Statistic.PLAY_ONE_MINUTE);
    }
}
