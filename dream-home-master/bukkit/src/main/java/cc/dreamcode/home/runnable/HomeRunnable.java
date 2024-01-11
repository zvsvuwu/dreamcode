package cc.dreamcode.home.runnable;

import cc.dreamcode.home.HomePlugin;
import cc.dreamcode.home.config.MessageConfig;
import cc.dreamcode.home.manager.HomeManager;
import cc.dreamcode.platform.bukkit.component.scheduler.Scheduler;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.Location;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Scheduler(interval = 20, delay = 0)
public class HomeRunnable implements Runnable {
    private @Inject HomePlugin homePlugin;
    private @Inject MessageConfig messageConfig;
    private @Inject HomeManager homeManager;

    @Override
    public void run() {
        this.homePlugin.getServer().getOnlinePlayers().forEach(player -> {

            Map<UUID, Integer> timeMap = this.homeManager.getTimeMap();
            Map<UUID, Location> locationMap = this.homeManager.getLocationMap();

            if (!timeMap.containsKey(player.getUniqueId())) return;
            if (!locationMap.containsKey(player.getUniqueId())) return;

            if (timeMap.get(player.getUniqueId()) > 0) {

                this.messageConfig.teleportationNotice.send(
                        player,
                        Collections.singletonMap("time", timeMap.get(player.getUniqueId()))
                );

                timeMap.put(player.getUniqueId(), timeMap.get(player.getUniqueId()) - 1);
                return;
            }

            Location location = locationMap.get(player.getUniqueId());

            this.homePlugin.getServer().getScheduler().runTask(
                    this.homePlugin,
                    () -> player.teleport(location)
            );

            timeMap.remove(player.getUniqueId());
            locationMap.remove(player.getUniqueId());

            this.messageConfig.teleportationSuccess.send(player);

        });
    }
}
