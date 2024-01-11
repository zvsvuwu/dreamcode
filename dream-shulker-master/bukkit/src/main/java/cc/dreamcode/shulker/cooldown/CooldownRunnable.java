package cc.dreamcode.shulker.cooldown;

import cc.dreamcode.platform.bukkit.component.scheduler.Scheduler;
import eu.okaeri.injector.annotation.Inject;

@Scheduler(delay = 20, interval = 20)
public class CooldownRunnable implements Runnable {

    private @Inject CooldownManager cooldownManager;

    @Override
    public void run() {
        cooldownManager.removeAllWhenComplete();
    }
}
