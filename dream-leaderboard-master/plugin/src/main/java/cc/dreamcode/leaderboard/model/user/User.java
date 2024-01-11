package cc.dreamcode.leaderboard.model.user;

import cc.dreamcode.leaderboard.storage.document.AbstractDocument;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class User extends AbstractDocument {

    private String name;

    private long blockPlaceCount = 0L;
    private long blockBreakCount = 0L;
    private long stonePlaceCount = 0L;
    private long stoneBreakCount = 0L;
    private long obsidianPlaceCount = 0L;
    private long obsidianBreakCount = 0L;
    private long killCount = 0L;
    private long deathCount = 0L;
    private long refileEatCount = 0L;
    private long koxEatCount = 0L;
    private long pearlThrowCount = 0L;
    private long playTime = 0L;
    private long walkDistance = 0L;

    public UUID getUniqueId() {
        return this.getPath().toUUID();
    }

}
