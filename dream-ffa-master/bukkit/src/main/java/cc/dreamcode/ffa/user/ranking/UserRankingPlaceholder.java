package cc.dreamcode.ffa.user.ranking;

import cc.dreamcode.ffa.config.MessageConfig;
import cc.dreamcode.ffa.config.PluginConfig;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

@RequiredArgsConstructor(onConstructor_= @Inject)
public class UserRankingPlaceholder extends PlaceholderExpansion {

    private final UserRanking userRanking;
    private final MessageConfig messageConfig;
    private final PluginConfig pluginConfig;

    @Override
    public @NonNull String getIdentifier() {
        return this.pluginConfig.userRankingPlaceholderIdentifier;
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
        final String parameter = params.toLowerCase();
        return UserRankingUtil.requestTops(parameter, this.userRanking, this.messageConfig);
    }
}
