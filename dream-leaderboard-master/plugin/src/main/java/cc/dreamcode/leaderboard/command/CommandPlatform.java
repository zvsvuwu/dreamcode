package cc.dreamcode.leaderboard.command;

import cc.dreamcode.leaderboard.LeaderboardPlugin;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public interface CommandPlatform {
    void handle(@NonNull CommandSender sender, @NonNull String[] args);

    List<String> tab(@NonNull Player player, @NonNull String[] args);

    default <T> T createInstance(@NonNull Class<T> clazz) {
        return LeaderboardPlugin.getLeaderboardPlugin().createInstance(clazz);
    }
}
