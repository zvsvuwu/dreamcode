package cc.dreamcode.codekeys.user;

import cc.dreamcode.platform.bukkit.component.scheduler.Scheduler;
import eu.okaeri.injector.annotation.Inject;

@Scheduler(interval = 1200, delay = 1200)
public class UserSaveTask implements Runnable {

    @Inject private UserManager userManager;

    @Override
    public void run() {
        this.userManager.saveAll();
    }
}
