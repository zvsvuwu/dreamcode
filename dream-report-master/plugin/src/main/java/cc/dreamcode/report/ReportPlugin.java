package cc.dreamcode.report;

import cc.dreamcode.menu.bukkit.BukkitMenuProvider;
import cc.dreamcode.report.boot.PluginBootLoader;
import cc.dreamcode.report.command.report.ReportCommand;
import cc.dreamcode.report.component.ComponentHandler;
import cc.dreamcode.report.config.MessageConfig;
import cc.dreamcode.report.config.PluginConfig;
import cc.dreamcode.report.model.user.UserRepository;
import cc.dreamcode.report.scheduler.SchedulerService;
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

@Plugin(name = "Dream-Report", version = "1.0.2-SNAPSHOT")
@Author("Ravis96")
@Description("Report plugin by DreamCode.")
@Website("DreamCode - https://discord.gg/dreamcode")
@ApiVersion(ApiVersion.Target.v1_13)
public final class ReportPlugin extends PluginBootLoader {

    @Getter private static ReportPlugin reportPlugin;
    @Getter private static ReportLogger reportLogger;

    @Override
    public void load() {
        // Static content for api.
        reportPlugin = this;
        reportLogger = new ReportLogger(reportPlugin.getLogger());
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
                thread.setName("dream-report-scheduler");
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
        componentHandler.registerComponent(ReportCommand.class);
    }

    @Override
    public void stop() {
        // features need to be call by stop server
    }
}
