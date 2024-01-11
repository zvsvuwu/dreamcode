package cc.dreamcode.ffa.user.ranking;

import cc.dreamcode.ffa.config.MessageConfig;
import cc.dreamcode.ffa.user.User;
import cc.dreamcode.utilities.ParseUtil;
import cc.dreamcode.utilities.bukkit.ChatUtil;
import eu.okaeri.placeholders.context.PlaceholderContext;
import eu.okaeri.placeholders.message.CompiledMessage;

public class UserRankingUtil {

    public static String requestTops(final String parameter, final UserRanking userRanking, final MessageConfig messageConfig) {
        if (parameter.startsWith("points")) {
            int position = ParseUtil.parseInteger(parameter.replace("points", "").replace("-", "")).orElse(0);
            if (position > 0) {
                position--;
            }
            User user;
            if ((userRanking.getPointsRanking().size() - 1) >= position) {
                user = userRanking.getPointsRanking().get(position);
            } else {
                user = null;
            }
            if (user != null) {
                return ChatUtil.fixColor(PlaceholderContext.of(CompiledMessage.of(messageConfig.pointsRankingFormat))
                        .with("user-name", user.getName())
                        .with("user-points", user.getStatistics().getPoints())
                        .apply());
            }
            return ChatUtil.fixColor(messageConfig.pointsRankingNotFound);
        } else if (parameter.startsWith("kill-streak")) {
            int position = ParseUtil.parseInteger(parameter.replace("kill-streak", "").replace("-", "")).orElse(0);
            if (position > 0) {
                position--;
            }
            User user;
            if ((userRanking.getKillStreakRanking().size() - 1) >= position) {
                user = userRanking.getKillStreakRanking().get(position);
            } else {
                user = null;
            }
            if (user != null) {
                return ChatUtil.fixColor(PlaceholderContext.of(CompiledMessage.of(messageConfig.killStreakRankingFormat))
                        .with("user-name", user.getName())
                        .with("user-ks", user.getStatistics().getKillStreak())
                        .apply());
            }
            return ChatUtil.fixColor(messageConfig.killStreakRankingNotFound);
        } else if (parameter.startsWith("max-kill-streak")) {
            int position = ParseUtil.parseInteger(parameter.replace("max-kill-streak", "").replace("-", "")).orElse(0);
            if (position > 0) {
                position--;
            }
            User user;
            if ((userRanking.getMaxKillStreakRanking().size() - 1) >= position) {
                user = userRanking.getMaxKillStreakRanking().get(position);
            } else {
                user = null;
            }
            if (user != null) {
                return ChatUtil.fixColor(PlaceholderContext.of(CompiledMessage.of(messageConfig.maxKillStreakRankingFormat))
                        .with("user-name", user.getName())
                        .with("user-max-ks", user.getStatistics().getMaxKillStreak())
                        .apply());
            }
            return ChatUtil.fixColor(messageConfig.maxKillStreakRankingNotFound);
        }
        return "param not found";
    }
}
