package cc.dreamcode.guisuffix.command;

import cc.dreamcode.command.annotations.RequiredPermission;
import cc.dreamcode.command.annotations.RequiredPlayer;
import cc.dreamcode.command.bukkit.BukkitCommand;
import cc.dreamcode.guisuffix.config.MessageConfig;
import cc.dreamcode.guisuffix.config.PluginConfig;
import cc.dreamcode.guisuffix.suffix.SuffixService;
import cc.dreamcode.guisuffix.user.User;
import cc.dreamcode.guisuffix.user.UserCache;
import cc.dreamcode.utilities.CountUtil;
import cc.dreamcode.utilities.TimeUtil;
import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@RequiredPlayer
@RequiredPermission(permission = "dreamcode.usunsuffix")
public class RemoveSuffixCommand extends BukkitCommand {

    private final MessageConfig messageConfig;

    private final PluginConfig pluginConfig;

    private final UserCache userCache;

    private final SuffixService suffixService;

    private final Tasker tasker;


    @Inject
    public RemoveSuffixCommand(final PluginConfig pluginConfig, final MessageConfig messageConfig, final UserCache userCache, final Tasker tasker, final SuffixService suffixService) {
        super("usunsuffix", "deletesuffix", "removesuffix");

        this.pluginConfig = pluginConfig;
        this.messageConfig = messageConfig;
        this.userCache = userCache;
        this.tasker = tasker;
        this.suffixService = suffixService;
    }

    @Override
    public void content(@NonNull CommandSender sender, @NonNull String[] args) {
        final Player player = (Player) sender;
        final User user = this.userCache.get(player);

        if (!player.hasPermission("dreamcode.guisuffix.cooldown.bypass")) {
            if (user.getRemoveSuffixCooldown() != null) {
                Duration commandCooldown = CountUtil.getCountDown(user.getRemoveSuffixCooldown(), this.pluginConfig.removeSuffixCooldown);

                if (!commandCooldown.isNegative()) {
                    this.messageConfig.commandCooldown.send(player, new MapBuilder<String, Object>().put("time", TimeUtil.convertDurationSeconds(commandCooldown)).build());
                    return;
                }
            }
            this.tasker.newSharedChain("userupdate:" + user.getName())
                    .async(() -> {
                        user.setRemoveSuffixCooldown(Instant.now());
                        user.save();
                    })
                    .execute();
        }

        if (!this.suffixService.hasSuffix(player)) {
            this.messageConfig.noSuffix.send(player);
            return;
        }

        this.suffixService.removeSuffix(player);

        this.messageConfig.suffixRemoved.send(player);
    }

    @Override
    public List<String> tab(@NonNull CommandSender sender, @NonNull String[] args) {
        return null;
    }
}

