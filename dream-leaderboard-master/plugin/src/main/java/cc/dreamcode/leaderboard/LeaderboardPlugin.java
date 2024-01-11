package cc.dreamcode.leaderboard;

import cc.dreamcode.leaderboard.boot.PluginBootLoader;
import cc.dreamcode.leaderboard.command.top.TopCommand;
import cc.dreamcode.leaderboard.component.ComponentHandler;
import cc.dreamcode.leaderboard.config.MessageConfig;
import cc.dreamcode.leaderboard.config.PluginConfig;
import cc.dreamcode.leaderboard.controllers.LeaderboardController;
import cc.dreamcode.leaderboard.model.user.UserRepository;
import cc.dreamcode.leaderboard.scheduler.SchedulerService;
import cc.dreamcode.leaderboard.scheduler.user.PlayingTimeRunnable;
import cc.dreamcode.menu.bukkit.BukkitMenuProvider;
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

@Plugin(name = "Dream-Leaderboard", version = "1.1.1-SNAPSHOT")
@Author("Ravis96")
@Description("Leaderboard plugin by DreamCode.")
@Website("DreamCode - https://discord.gg/dreamcode")
@ApiVersion(ApiVersion.Target.v1_13)
public final class LeaderboardPlugin extends PluginBootLoader {

    @Getter private static LeaderboardPlugin leaderboardPlugin;
    @Getter private static LeaderboardLogger leaderboardLogger;

    @Override
    public void load() {
        // Static content for api.
        leaderboardPlugin = this;
        leaderboardLogger = new LeaderboardLogger(leaderboardPlugin.getLogger());
    }

    @Override
    public void start(@NonNull ComponentHandler componentHandler) {
        // Injectable object registering. (library etc.)
        this.registerInjectable(BukkitMenuProvider.create(this));

        // Component system inspired by okaeri-platform
        // These simple structure can register all content of this plugin. (A-Z)
        componentHandler.registerComponent(SchedulerService.class, schedulerService -> {
            schedulerService.setScheduler(new ScheduledThreadPoolExecutor(1, r -> {
                Thread thread = Executors.defaultThreadFactory().newThread(r);
                thread.setName("dream-leaderboard-scheduler");
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
        componentHandler.registerComponent(LeaderboardService.class);
        componentHandler.registerComponent(LeaderboardController.class);
        componentHandler.registerComponent(LeaderboardRunnable.class);
        componentHandler.registerComponent(PlayingTimeRunnable.class);
        componentHandler.registerComponent(TopCommand.class);
    }

    @Override
    public void stop() {
        // features need to be call by stop server
    }
}
