package cc.dreamcode.leaderboard.config;

import cc.dreamcode.leaderboard.config.annotations.Configuration;
import cc.dreamcode.leaderboard.config.leaderboard.LeaderboardConfig;
import cc.dreamcode.leaderboard.config.storage.StorageConfig;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

@Configuration(
        child = "config.yml"
)
@Header("## Dream-Leaderboard (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {
    @Comment("Uzupelnij ponizsze menu danymi.")
    public StorageConfig storageConfig = new StorageConfig();
    public LeaderboardConfig leaderboardConfig = new LeaderboardConfig();
}
