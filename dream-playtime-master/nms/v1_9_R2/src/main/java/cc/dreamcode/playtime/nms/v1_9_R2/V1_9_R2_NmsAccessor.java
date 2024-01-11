package cc.dreamcode.playtime.nms.v1_9_R2;

import cc.dreamcode.playtime.nms.api.NmsAccessor;
import cc.dreamcode.playtime.nms.api.statistic.StatisticAccessor;
import cc.dreamcode.playtime.nms.v1_9_R2.statistic.V1_9_R2_StatisticAccessor;

public class V1_9_R2_NmsAccessor implements NmsAccessor {
    @Override
    public StatisticAccessor getStatisticAccessor() {
        return new V1_9_R2_StatisticAccessor();
    }
}
