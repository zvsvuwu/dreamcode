package cc.dreamcode.leaderboard.storage.document;

import cc.dreamcode.leaderboard.LeaderboardPlugin;
import cc.dreamcode.leaderboard.scheduler.SchedulerService;
import eu.okaeri.persistence.document.Document;

import java.util.concurrent.TimeUnit;

/**
 * Abstract class for asynchronous save method in document.
 */
public abstract class AbstractDocument extends Document {
    public void saveAsync() {
        LeaderboardPlugin.getLeaderboardPlugin().getInject(SchedulerService.class).ifPresent(schedulerService ->
                schedulerService.asyncLater(this::save, 0L, TimeUnit.MILLISECONDS));
    }
}
