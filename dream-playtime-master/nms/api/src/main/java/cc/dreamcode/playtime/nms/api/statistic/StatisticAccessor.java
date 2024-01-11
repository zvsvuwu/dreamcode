package cc.dreamcode.playtime.nms.api.statistic;

import lombok.NonNull;
import org.bukkit.entity.Player;

public interface StatisticAccessor {

    long getPlayOneTick(@NonNull Player player);
}
