package cc.dreamcode.leaderboard.command.top;

import cc.dreamcode.leaderboard.command.CommandHandler;
import cc.dreamcode.leaderboard.command.annotations.RequiredPlayer;
import cc.dreamcode.leaderboard.menus.LeaderboardMenu;
import cc.dreamcode.leaderboard.model.FutureImpl;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

@RequiredPlayer
public class TopCommand extends CommandHandler implements FutureImpl {
    public TopCommand() {
        super("top", Collections.singletonList("topka"));
    }

    @Override
    public void handle(@NonNull CommandSender sender, @NonNull String[] args) {
        final Player player = (Player) sender;

        future(() -> this.createInstance(LeaderboardMenu.class).build(player))
                .join()
                .open(player);
    }

    @Override
    public List<String> tab(@NonNull Player player, @NonNull String[] args) {
        return null;
    }
}
