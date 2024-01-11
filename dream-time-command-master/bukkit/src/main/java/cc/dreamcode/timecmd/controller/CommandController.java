package cc.dreamcode.timecmd.controller;

import cc.dreamcode.timecmd.config.MessageConfig;
import cc.dreamcode.timecmd.config.PluginConfig;
import cc.dreamcode.timecmd.mcversion.api.VersionAccessor;
import cc.dreamcode.utilities.ParseUtil;
import cc.dreamcode.utilities.TimeUtil;
import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandController implements Listener {

    private @Inject PluginConfig pluginConfig;
    private @Inject VersionAccessor versionAccessor;
    private @Inject MessageConfig messageConfig;

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();

        if (player.hasPermission(this.pluginConfig.bypassPermission)) {
            return;
        }

        final String cmd = event.getMessage().toLowerCase().replaceFirst("/", "");
        final String command = (cmd.contains(" ") ? cmd.split(" ")[0] : cmd);

        final String time = this.pluginConfig.timeCommands.getOrDefault(command, null);

        if (time == null) {
            return;
        }

        ParseUtil.parsePeriod(time).ifPresent(duration -> {
            long seconds = duration.getSeconds();
            long playerSeconds = (((long) this.versionAccessor.getTime(player)) / 20L);
            if (seconds > playerSeconds) {
                event.setCancelled(true);
                this.messageConfig.cantUseCommand.send(player, new MapBuilder<String, Object>()
                        .put("time", TimeUtil.convertSeconds(seconds))
                        .put("player-time", TimeUtil.convertSeconds(playerSeconds))
                        .put("missing-time", TimeUtil.convertSeconds(seconds - playerSeconds))
                        .build());
            }
        });
    }
}
