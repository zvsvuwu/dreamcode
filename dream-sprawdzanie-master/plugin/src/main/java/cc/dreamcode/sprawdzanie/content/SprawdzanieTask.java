package cc.dreamcode.sprawdzanie.content;

import cc.dreamcode.sprawdzanie.SprawdzaniePlugin;
import cc.dreamcode.sprawdzanie.config.MessageConfig;
import cc.dreamcode.sprawdzanie.config.PluginConfig;
import cc.dreamcode.sprawdzanie.notice.NoticeSender;
import cc.dreamcode.sprawdzanie.scheduler.annotations.Scheduler;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.TimeUnit;

@Scheduler(start = 0L, interval = 1L, unit = TimeUnit.SECONDS)
public class SprawdzanieTask extends BukkitRunnable implements NoticeSender {
    private @Inject PluginConfig pluginConfig;
    private @Inject MessageConfig messageConfig;
    private @Inject SprawdzanieManager sprawdzanieManager;

    @Override
    public void run() {
        SprawdzaniePlugin.getSprawdzaniePlugin().getServer().getOnlinePlayers().forEach(player -> {
            if (!sprawdzanieManager.getSprawdzaniGracze().containsKey(player)) return;
            if (!pluginConfig.sendTitle) return;
            send(messageConfig.checkTitle, player);
            send(messageConfig.checkMessage, player);
        });
    }
}
