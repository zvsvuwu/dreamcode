package cc.dreamcode.playtime.nms.v1_17_R1;

import cc.dreamcode.playtime.nms.api.NmsAccessor;
import cc.dreamcode.playtime.nms.api.statistic.StatisticAccessor;
import cc.dreamcode.playtime.nms.v1_17_R1.statistic.V1_17_R1_StatisticAccessor;

public class V1_17_R1_NmsAccessor implements NmsAccessor {
    @Override
    public StatisticAccessor getStatisticAccessor() {
        return new V1_17_R1_StatisticAccessor();
    }
}
