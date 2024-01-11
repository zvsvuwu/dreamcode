package cc.dreamcode.ffa.user.command;

import cc.dreamcode.command.bukkit.BukkitCommand;
import cc.dreamcode.ffa.config.MessageConfig;
import cc.dreamcode.ffa.user.User;
import cc.dreamcode.ffa.user.UserCache;
import cc.dreamcode.ffa.user.UserStatistics;
import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class KillStreakCommand extends BukkitCommand {

    private final UserCache userCache;

    private final MessageConfig messageConfig;

    @Inject
    public KillStreakCommand(UserCache userCache, MessageConfig messageConfig) {
        super("killstreak", "ks");
        this.userCache = userCache;
        this.messageConfig = messageConfig;
    }

    @Override
    public void content(@NonNull CommandSender sender, @NonNull String[] args) {
        if (!(sender instanceof Player)) {
            return;
        }
        Player player = (Player) sender;
        final User user = this.userCache.get(player);
        final UserStatistics statistics = user.getStatistics();
        this.messageConfig.killStreakInfo.send(player, new MapBuilder<String, Object>()
                .put("current_ks", statistics.getKillStreak())
                .put("maximum_ks", statistics.getMaxKillStreak())
                .build());
    }

    @Override
    public List<String> tab(@NonNull CommandSender sender, @NonNull String[] args) {
        return null;
    }
}
