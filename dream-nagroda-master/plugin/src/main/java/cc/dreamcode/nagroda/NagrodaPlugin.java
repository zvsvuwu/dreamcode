package cc.dreamcode.nagroda;

import cc.dreamcode.nagroda.boot.PluginBootLoader;
import cc.dreamcode.nagroda.component.ComponentHandler;
import cc.dreamcode.nagroda.config.MessageConfig;
import cc.dreamcode.nagroda.config.PluginConfig;
import cc.dreamcode.nagroda.content.NagrodaCommand;
import cc.dreamcode.nagroda.content.NagrodaListener;
import cc.dreamcode.nagroda.content.NagrodaManager;
import cc.dreamcode.nagroda.model.user.UserRepository;
import cc.dreamcode.nagroda.scheduler.SchedulerService;
import eu.okaeri.persistence.document.DocumentPersistence;
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

@Plugin(name = "Dream-Nagroda", version = "1.0")
@Author("torobolin")
@Description("Nagroda plugin by DreamCode.")
@Website("DreamCode - https://discord.gg/dreamcode")
@ApiVersion(ApiVersion.Target.v1_13)

public final class NagrodaPlugin extends PluginBootLoader {

    @Getter private static NagrodaPlugin nagrodaPlugin;
    @Getter private static NagrodaLogger nagrodaLogger;

    @Override
    public void load() {
        nagrodaPlugin = this;
        nagrodaLogger = new NagrodaLogger(nagrodaPlugin.getLogger());
    }

    @Override
    public void start(@NonNull ComponentHandler componentHandler) {
        componentHandler.registerComponent(SchedulerService.class, schedulerService -> {
            schedulerService.setScheduler(new ScheduledThreadPoolExecutor(1, r -> {
                Thread thread = Executors.defaultThreadFactory().newThread(r);
                thread.setName("dream-nagroda-scheduler");
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
        componentHandler.registerComponent(DocumentPersistence.class);
        componentHandler.registerComponent(UserRepository.class);
        componentHandler.registerComponent(NagrodaManager.class, NagrodaManager::initializeBot);
        componentHandler.registerComponent(NagrodaListener.class);
        componentHandler.registerComponent(NagrodaCommand.class);
    }

    @Override
    public void stop() {
        this.getInject(NagrodaManager.class).ifPresent(nagrodaManager ->
                nagrodaManager.getJda().shutdown());
    }
}
