package cc.dreamcode.tops.task;

import cc.dreamcode.platform.bukkit.component.scheduler.Scheduler;
import cc.dreamcode.tops.user.top.TopManager;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import org.bukkit.Bukkit;

import java.time.Duration;

@Scheduler(delay = 287, interval = 287)
public class TopTask implements Runnable {

    private @Inject TopManager topManager;
    private @Inject Tasker tasker;

    @Override
    public void run() {
        this.topManager.update();

        Bukkit.getOnlinePlayers().forEach(player -> {
            this.tasker.newDelayer(Duration.ofSeconds(3))
                    .delayed(() -> this.topManager.updateMenu(player))
                    .executeSync();
        });

    }
}