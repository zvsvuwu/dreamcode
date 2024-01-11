package cc.dreamcode.shaman.user;

import cc.dreamcode.platform.bukkit.component.scheduler.Scheduler;
import eu.okaeri.injector.annotation.Inject;

@Scheduler(interval = 300, delay = 300)
public class UserSaveTask implements Runnable {

    @Inject private UserManager userManager;

    @Override
    public void run() {
        this.userManager.saveAll();
    }
}
