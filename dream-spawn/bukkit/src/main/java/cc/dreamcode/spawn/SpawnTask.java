package cc.dreamcode.spawn;

import cc.dreamcode.platform.bukkit.component.scheduler.Scheduler;
import cc.dreamcode.spawn.config.MessageConfig;
import cc.dreamcode.spawn.config.PluginConfig;
import cc.dreamcode.spawn.util.TimeUtil;
import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;

@Scheduler(delay = 20L, interval = 20L)
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class SpawnTask implements Runnable {

    private final SpawnPlugin spawnPlugin;
    private final SpawnManager spawnManager;
    private final PluginConfig config;
    private final MessageConfig messageConfig;

    @Override
    public void run() {
        long currentTimeMillis = System.currentTimeMillis();
        Bukkit.getOnlinePlayers().forEach(player -> this.spawnManager.getTeleport(player).ifPresent(teleport -> {
            if(teleport - currentTimeMillis >= 1000) {
                this.messageConfig.teleportMessage.forEach(bukkitNotice -> bukkitNotice.send(player, new MapBuilder<String, Object>()
                        .put("time", TimeUtil.formatTime(teleport - currentTimeMillis)).build()));

                if (this.config.teleportSoundsEnabled) {
                    this.config.teleportSounds.forEach(sound -> player.playSound(player.getLocation(), sound, 1.0F, 1.0F));
                }
                return;
            }

            if (this.config.teleportEffectsEnabled) {
                this.spawnPlugin.getServer().getScheduler().runTask(this.spawnPlugin, () -> this.config.teleportEffects.forEach(player::removePotionEffect));
            }

            this.spawnPlugin.getServer().getScheduler().runTask(this.spawnPlugin, () -> player.teleport(config.spawnLocation));
            this.spawnManager.removeTeleport(player);
            this.messageConfig.successMessage.send(player);
        }));
    }
}
