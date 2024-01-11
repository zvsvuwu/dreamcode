package cc.dreamcode.playtime.nms.v1_8_R3;

import cc.dreamcode.playtime.nms.api.NmsAccessor;
import cc.dreamcode.playtime.nms.api.statistic.StatisticAccessor;
import cc.dreamcode.playtime.nms.v1_8_R3.statistic.V1_8_R3_StatisticAccessor;

public class V1_8_R3_NmsAccessor implements NmsAccessor {
    @Override
    public StatisticAccessor getStatisticAccessor() {
        return new V1_8_R3_StatisticAccessor();
    }
}
