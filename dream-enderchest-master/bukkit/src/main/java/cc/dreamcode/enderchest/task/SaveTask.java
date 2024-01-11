package cc.dreamcode.enderchest.task;

import cc.dreamcode.enderchest.EnderchestPlugin;
import cc.dreamcode.enderchest.config.PluginConfig;
import cc.dreamcode.enderchest.user.UserManager;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.scheduler.BukkitRunnable;

public class SaveTask {

    private @Inject EnderchestPlugin enderchestPlugin;
    private @Inject UserManager userManager;
    private @Inject PluginConfig pluginConfig;

    public void run() {
        new BukkitRunnable() {
            @Override
            public void run() {
                userManager.saveAll();
            }
        }.runTaskTimerAsynchronously(this.enderchestPlugin, this.pluginConfig.saveInteraval, this.pluginConfig.saveInteraval);
    }
}
