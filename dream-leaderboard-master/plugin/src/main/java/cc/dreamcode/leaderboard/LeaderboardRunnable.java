package cc.dreamcode.leaderboard;

import cc.dreamcode.leaderboard.scheduler.annotations.Scheduler;
import eu.okaeri.injector.annotation.Inject;

import java.util.concurrent.TimeUnit;

@Scheduler(start = 1, interval = 15, unit = TimeUnit.SECONDS)
public class LeaderboardRunnable implements Runnable {

    private @Inject LeaderboardPlugin leaderboardPlugin;
    private @Inject LeaderboardService leaderboardService;

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        this.leaderboardService.updateMap();
    }
}
