package cc.dreamcode.killreward.command;

import cc.dreamcode.command.annotations.RequiredPermission;
import cc.dreamcode.command.bukkit.BukkitCommand;
import cc.dreamcode.killreward.boost.Boost;
import cc.dreamcode.killreward.boost.BoostManager;
import cc.dreamcode.killreward.config.MessageConfig;
import cc.dreamcode.killreward.config.PluginConfig;
import cc.dreamcode.killreward.util.PercentUtil;
import cc.dreamcode.killreward.util.TimeUtil;
import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

@RequiredPermission(permission = "dream-killreward.command")
public class KillRewardCommand extends BukkitCommand {

    private final PluginConfig pluginConfig;
    private final MessageConfig messageConfig;
    private final BoostManager boostManager;

    @Inject
    public KillRewardCommand(final PluginConfig pluginConfig, final MessageConfig messageConfig, final BoostManager boostManager) {
        super("killreward", "killrewards");

        this.pluginConfig = pluginConfig;
        this.messageConfig = messageConfig;
        this.boostManager = boostManager;
    }

    @Override
    public void content(@NonNull CommandSender sender, @NonNull String[] args) {
        if(args.length < 1) {
            this.messageConfig.usage.send(sender, new MapBuilder<String, Object>().put("usage", "/killreward [reload/boost]").build());
            return;
        }

        switch (args[0]) {
            case "reload":
                this.pluginConfig.load();
                this.messageConfig.load();

                this.messageConfig.reload.send(sender);
                break;
            case "boost":
                if(args.length < 4) {
                    this.messageConfig.usage
                            .send(
                                    sender,
                                    new MapBuilder<String, Object>().put("usage", "/killreward boost [gracz/all] [szansa] [czas w sekundach]")
                            .build());
                    return;
                }

                boolean all = args[1].contains("all");
                String chance = args[2];
                long duration;

                try {
                    duration = Long.parseLong(args[3]) * 1000;
                } catch (NumberFormatException e) {
                    this.messageConfig.notNumber.send(sender);
                    return;
                }

                Boost boost = new Boost(duration, Instant.now().plusMillis(duration).toEpochMilli(), PercentUtil.unFormatPercent(chance));

                if(all) {
                    this.messageConfig.boostAddAll.send(sender, new MapBuilder<String, Object>()
                            .put("chance", PercentUtil.unFormatPercent(chance))
                            .put("duration", TimeUtil.formatMilliseconds(duration))
                            .build());
                    this.boostManager.addBoostToAll(boost);
                    return;
                }

                Player target = Bukkit.getPlayerExact(args[1]);

                if(target == null || !target.isOnline()) {
                    this.messageConfig.offlinePlayer.send(sender);
                    return;
                }

                this.messageConfig.boostAdd.send(sender, new MapBuilder<String, Object>()
                        .put("chance", PercentUtil.unFormatPercent(chance))
                        .put("player", target.getName())
                        .put("duration", TimeUtil.formatMilliseconds(duration))
                        .build());
                this.boostManager.addBoost(target, boost);
                break;
        }
    }

    @Override
    public List<String> tab(@NonNull CommandSender sender, @NonNull String[] args) {
        if(sender.hasPermission("dream-killreward.command")) {
            if(args.length == 1) {
                return Arrays.asList("reload", "boost");
            } else if(args[0].equalsIgnoreCase("boost")) {
                switch (args.length) {
                    case 2:
                        List<String> targetComplete = new ArrayList<>();
                        targetComplete.add("all");
                        Bukkit.getOnlinePlayers().forEach(player -> targetComplete.add(player.getName()));
                        return targetComplete;
                    case 3:
                        return Collections.singletonList("50%");
                    case 4:
                        return Collections.singletonList("30");
                }
            }
        }
        return null;
    }
}
