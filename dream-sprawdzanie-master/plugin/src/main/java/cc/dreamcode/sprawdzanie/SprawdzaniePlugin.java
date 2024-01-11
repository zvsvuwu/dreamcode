package cc.dreamcode.sprawdzanie;

import cc.dreamcode.sprawdzanie.boot.PluginBootLoader;
import cc.dreamcode.sprawdzanie.component.ComponentHandler;
import cc.dreamcode.sprawdzanie.config.MessageConfig;
import cc.dreamcode.sprawdzanie.config.PluginConfig;
import cc.dreamcode.sprawdzanie.content.*;
import cc.dreamcode.sprawdzanie.scheduler.SchedulerService;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.Website;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Plugin(name = "Dream-Sprawdzanie", version = "1.0")
@Author("torobolin")
@Description("Sprawdzanie plugin by DreamCode.")
@Website("DreamCode - https://discord.gg/dreamcode")
@ApiVersion(ApiVersion.Target.v1_13)

public final class SprawdzaniePlugin extends PluginBootLoader {

    @Getter private static SprawdzaniePlugin sprawdzaniePlugin;
    @Getter private static SprawdzanieLogger sprawdzanieLogger;

    @Override
    public void load() {
        sprawdzaniePlugin = this;
        sprawdzanieLogger = new SprawdzanieLogger(sprawdzaniePlugin.getLogger());
    }

    @Override
    public void start(@NonNull ComponentHandler componentHandler) {
        componentHandler.registerComponent(SchedulerService.class, schedulerService -> {
            schedulerService.setScheduler(new ScheduledThreadPoolExecutor(1, r -> {
                Thread thread = Executors.defaultThreadFactory().newThread(r);
                thread.setName("dream-sprawdzanie-scheduler");
                return thread;
            }));

            schedulerService.getScheduler().setRemoveOnCancelPolicy(true);
            schedulerService.getScheduler().setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
            schedulerService.setAsync(new ForkJoinPool(
                    16,
                    new SchedulerService.WorkerThreadFactory(),
                    new SchedulerService.ExceptionHandler(),
                    false
            ));
        });
        componentHandler.registerComponent(PluginConfig.class);
        componentHandler.registerComponent(MessageConfig.class);
        componentHandler.registerComponent(SprawdzanieManager.class);
        componentHandler.registerComponent(SprawdzCommand.class);
        componentHandler.registerComponent(CzystyCommand.class);
        componentHandler.registerComponent(PlayerEventsListener.class);
        componentHandler.registerComponent(PrzyznajeSieCommand.class);
        componentHandler.registerComponent(SprawdzanieTask.class);
    }

    @Override
    public void stop() {
    }
}
