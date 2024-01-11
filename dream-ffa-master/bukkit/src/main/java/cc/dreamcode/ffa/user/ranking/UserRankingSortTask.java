package cc.dreamcode.ffa.user.ranking;

import cc.dreamcode.platform.bukkit.component.scheduler.Scheduler;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;

/**
 * UserRankingSortTask.java
 * Purpose: The UserRankingSortTask is a class that re-sorts user-ranking.
 * * @author trueman96
 * @version 1.0-beta.1
 * @since 2023-11-25
 */
@RequiredArgsConstructor(onConstructor_= @Inject)
@Scheduler(delay = 160L, interval = 160L)
public class UserRankingSortTask implements Runnable {

    private final UserRanking userRanking;

    @Override
    public void run() {
        this.userRanking.refreshRankings();
    }
}
