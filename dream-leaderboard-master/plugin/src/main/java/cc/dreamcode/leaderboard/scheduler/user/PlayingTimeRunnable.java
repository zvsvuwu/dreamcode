package cc.dreamcode.leaderboard.scheduler.user;

import cc.dreamcode.leaderboard.LeaderboardPlugin;
import cc.dreamcode.leaderboard.model.user.User;
import cc.dreamcode.leaderboard.model.user.UserRepository;
import cc.dreamcode.leaderboard.scheduler.annotations.Scheduler;
import eu.okaeri.injector.annotation.Inject;

import java.util.concurrent.TimeUnit;

@Scheduler(start = 5, interval = 5, unit = TimeUnit.SECONDS)
public class PlayingTimeRunnable implements Runnable {

    private @Inject LeaderboardPlugin leaderboardPlugin;
    private @Inject UserRepository userRepository;

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
        this.leaderboardPlugin.getServer().getOnlinePlayers().forEach(player -> {
            final User user = this.userRepository.findOrCreateByPlayer(player).join();

            user.setPlayTime(user.getPlayTime() + 5);
            user.saveAsync();
        });
    }
}
