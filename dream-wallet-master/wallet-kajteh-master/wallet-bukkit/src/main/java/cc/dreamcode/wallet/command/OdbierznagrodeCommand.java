package cc.dreamcode.wallet.command;

import cc.dreamcode.command.annotations.RequiredPlayer;
import cc.dreamcode.command.bukkit.BukkitCommand;
import cc.dreamcode.utilities.ChanceUtil;
import cc.dreamcode.utilities.CountUtil;
import cc.dreamcode.utilities.TimeUtil;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.wallet.config.MessageConfig;
import cc.dreamcode.wallet.config.PluginConfig;
import cc.dreamcode.wallet.shared.MoneyUtil;
import cc.dreamcode.wallet.user.User;
import cc.dreamcode.wallet.user.UserManager;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@RequiredPlayer
public class OdbierznagrodeCommand extends BukkitCommand {

    private final PluginConfig pluginConfig;
    private final MessageConfig messageConfig;
    private final UserManager userManager;
    private final Tasker tasker;

    @Inject
    public OdbierznagrodeCommand(PluginConfig pluginConfig, MessageConfig messageConfig, UserManager userManager, Tasker tasker) {
        super("odbierznagrode");
        this.pluginConfig = pluginConfig;
        this.messageConfig = messageConfig;
        this.userManager = userManager;
        this.tasker = tasker;
    }

    @Override
    public void content(@NonNull CommandSender sender, @NonNull String[] args) {
        final Player player = (Player) sender;
        final User user = this.userManager.getUserByPlayer(player);

        if (user.getReward() != null) {
            final Duration coolDown = this.pluginConfig.rewardCoolDown;
            final Duration countDown = CountUtil.getCountDown(user.getReward(), coolDown);
            if (!CountUtil.isOut(countDown)) {
                this.messageConfig.coolDownAreNotCompleted.send(player, new MapBuilder<String, Object>()
                        .put("time", TimeUtil.convertDurationMills(countDown))
                        .build());
                return;
            }
        }

        user.setReward(Instant.now());

        final double money = ChanceUtil.getRandomDouble(this.pluginConfig.minReward, this.pluginConfig.maxReward);
        this.tasker.newSharedChain("dbops:" + player.getUniqueId())
                .async(() -> {
                    user.addMoney(money);
                    user.save();
                })
                .sync(() -> {
                    this.messageConfig.rewardReceived.send(sender, new MapBuilder<String, Object>()
                            .put("money", MoneyUtil.formatMoney(this.pluginConfig.moneySuffix, money))
                            .build());

                    this.messageConfig.rewardReceivedBroadcast.sendAll(new MapBuilder<String, Object>()
                            .put("nick", player.getName())
                            .put("money", MoneyUtil.formatMoney(this.pluginConfig.moneySuffix, money))
                            .build());
                })
                .execute();
    }

    @Override
    public List<String> tab(@NonNull CommandSender sender, @NonNull String[] args) {
        return null;
    }
}
