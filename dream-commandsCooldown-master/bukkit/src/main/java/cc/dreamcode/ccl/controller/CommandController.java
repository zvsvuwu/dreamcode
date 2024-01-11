package cc.dreamcode.ccl.controller;

import cc.dreamcode.ccl.config.MessageConfig;
import cc.dreamcode.ccl.config.PluginConfig;
import cc.dreamcode.ccl.user.UserManager;
import cc.dreamcode.utilities.ParseUtil;
import cc.dreamcode.utilities.TimeUtil;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Collections;

public class CommandController implements Listener {

    private @Inject PluginConfig pluginConfig;
    private @Inject UserManager userManager;
    private @Inject MessageConfig messageConfig;

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();
        final String cmd = event.getMessage().toLowerCase().replaceFirst("/", "");
        final String command = cmd.contains(" ") ? cmd.split(" ")[0] : cmd;

        userManager.getUser(player.getUniqueId()).ifPresent(user -> {

            ParseUtil.parsePeriod(this.pluginConfig.allCommandsCool_down).ifPresent(period -> {
                user.setGlobalCool_down((period.getSeconds() * 1000L) + System.currentTimeMillis());
            });

            long time = user.getCool_down().getOrDefault(command, 0L);
            if (time >= System.currentTimeMillis()) {
                event.setCancelled(true);
                this.messageConfig.youHaveCool_down.send(player, Collections.singletonMap("time", TimeUtil.convertMills(time - System.currentTimeMillis())));
                return;
            }

            if (user.getGlobalCool_down() >= System.currentTimeMillis()) {
                event.setCancelled(true);
                this.messageConfig.globalCool_down.send(player, Collections.singletonMap("time", TimeUtil.convertMills(user.getGlobalCool_down() - System.currentTimeMillis())));
                return;
            }

            String commandTime = this.pluginConfig.cool_downs.getOrDefault(command, null);
            if (commandTime != null) {
                ParseUtil.parsePeriod(commandTime).ifPresent(period -> {
                    user.getCool_down().put(command, (period.getSeconds() * 1000L) + System.currentTimeMillis());
                    user.invalidate();
                    userManager.save(user);
                });
            }
        });

    }

}
