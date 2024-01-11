package cc.dreamcode.afkreward.runnable;

import cc.dreamcode.afkreward.AfkRewardPlugin;
import cc.dreamcode.afkreward.config.MessageConfig;
import cc.dreamcode.afkreward.config.PluginConfig;
import cc.dreamcode.afkreward.manager.AfkRewardManager;
import cc.dreamcode.platform.bukkit.component.scheduler.Scheduler;
import cc.dreamcode.utilities.bukkit.ItemUtil;
import eu.okaeri.injector.annotation.Inject;

import java.util.Optional;

@Scheduler(interval = 20, delay = 20)
public class AfkRewardRunnable implements Runnable{
    private @Inject AfkRewardPlugin afkRewardPlugin;
    private @Inject PluginConfig pluginConfig;
    private @Inject MessageConfig messageConfig;
    private @Inject AfkRewardManager afkRewardManager;

    @Override
    public void run() {
        this.afkRewardManager.getAfkPlayers().forEach((uuid, along) -> {
            int time = (int) (System.currentTimeMillis() - along)/1000;

            Optional.ofNullable(this.pluginConfig.afkItems.get(time)).ifPresent(itemStack ->
                    this.afkRewardPlugin.getServer().getScheduler().runTask(this.afkRewardPlugin,
                            () -> ItemUtil.giveItem(this.afkRewardPlugin.getServer().getPlayer(uuid), itemStack)));

            Optional.ofNullable(this.messageConfig.announcements.get(time)).ifPresent(bukkitNotice ->
                    bukkitNotice.send(this.afkRewardPlugin.getServer().getPlayer(uuid)));
        });
    }
}
