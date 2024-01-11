package cc.dreamcode.killreward.boost;

import cc.dreamcode.killreward.config.PluginConfig;
import cc.dreamcode.killreward.util.PercentUtil;
import cc.dreamcode.killreward.util.TimeUtil;
import cc.dreamcode.platform.bukkit.component.scheduler.Scheduler;
import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;

import java.util.Map;

@Scheduler(delay = 0L, interval = 20L)
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class BoostTask implements Runnable {

    private final PluginConfig pluginConfig;
    private final BoostManager boostManager;

    @Override
    public void run() {
        long currentTimeMillis = System.currentTimeMillis();
        Bukkit.getOnlinePlayers().forEach(player -> this.boostManager.getBoost(player).ifPresent(boost -> {
            long remainingTime = boost.getDuration() - currentTimeMillis;

            if (remainingTime <= 0) {
                this.boostManager.removeBoost(player);
                this.boostManager.removeBossBar(player);
                return;
            }

            double progress = (double) remainingTime / boost.getTotalTime();

            Map<String, Object> replacementMap = new MapBuilder<String, Object>()
                    .put("chance", PercentUtil.formatPercent(boost.getChance()))
                    .put("time", TimeUtil.formatMilliseconds(remainingTime))
                    .build();

            if (!this.boostManager.hasBossBar(player)) {
                this.boostManager.addBossBar(player, this.pluginConfig.buildBossBar(replacementMap));
                return;
            }

            this.boostManager.getBossBar(player).setTitle(this.pluginConfig.getBossbarTitle(replacementMap));
            this.boostManager.getBossBar(player).setProgress(progress);
        }));
    }
}

