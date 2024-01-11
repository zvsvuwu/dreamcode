package cc.dreamcode.ffa.user;

import cc.dreamcode.ffa.config.PluginConfig;
import cc.dreamcode.ffa.user.User;
import cc.dreamcode.ffa.user.UserCache;
import cc.dreamcode.ffa.user.UserStatistics;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

@RequiredArgsConstructor(onConstructor_= @Inject)
public class UserPlaceholder extends PlaceholderExpansion {

    private final UserCache userCache;
    private final PluginConfig pluginConfig;

    @Override
    public @NonNull String getIdentifier() {
        return this.pluginConfig.userPlaceholderIdentifier;
    }

    @Override
    public @NonNull String getAuthor() {
        return "dreamcode";
    }

    @Override
    public @NonNull String getVersion() {
        return "1.0-beta.1";
    }

    @Override
    public String onRequest(OfflinePlayer player, @NonNull String params) {
        if (player == null) {
            return "player is null";
        }

        final User user = this.userCache.get(player.getUniqueId());
        final UserStatistics statistics = user.getStatistics();
        switch (params.toLowerCase()) {
            case "name": {
                return player.getName();
            }
            case "points": {
                return String.valueOf(statistics.getPoints());
            }
            case "kills": {
                return String.valueOf(statistics.getKills());
            }
            case "deaths": {
                return String.valueOf(statistics.getDeaths());
            }
            case "assists": {
                return String.valueOf(statistics.getAssists());
            }
            case "kill-streak": {
                return String.valueOf(statistics.getKillStreak());
            }
            case "max-kill-streak": {
                return String.valueOf(statistics.getMaxKillStreak());
            }
        }
        return "param not found";
    }
}
