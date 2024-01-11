package cc.dreamcode.tops.task;

import cc.dreamcode.platform.bukkit.component.scheduler.Scheduler;
import cc.dreamcode.tops.user.UserManager;
import eu.okaeri.injector.annotation.Inject;

@Scheduler(delay = 300, interval = 300)
public class UserSaveTask implements Runnable {

    private @Inject UserManager userManager;

    @Override
    public void run() {
        this.userManager.saveAll();
    }
}
