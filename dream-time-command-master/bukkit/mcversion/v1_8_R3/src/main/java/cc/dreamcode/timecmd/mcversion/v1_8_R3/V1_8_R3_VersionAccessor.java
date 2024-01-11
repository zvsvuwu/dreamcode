package cc.dreamcode.timecmd.mcversion.v1_8_R3;

import cc.dreamcode.timecmd.mcversion.api.VersionAccessor;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public class V1_8_R3_VersionAccessor implements VersionAccessor {

    @Override
    public int getTime(Player player) {
        return player.getStatistic(Statistic.PLAY_ONE_TICK);
    }
}
