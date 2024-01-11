package cc.dreamcode.leaderboard;

import cc.dreamcode.leaderboard.model.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.ToLongFunction;

@Getter
@RequiredArgsConstructor
public enum LeaderboardType {
    BLOCK_PLACE_COUNT("block-place", User::getBlockPlaceCount),
    BLOCK_BREAK_COUNT("block-break", User::getBlockBreakCount),
    STONE_PLACE_COUNT("stone-place", User::getStonePlaceCount),
    STONE_BREAK_COUNT("stone-break", User::getStoneBreakCount),
    OBSIDIAN_PLACE_COUNT("obsidian-place", User::getObsidianPlaceCount),
    OBSIDIAN_BREAK_COUNT("obsidian-break", User::getObsidianBreakCount),
    KILL_COUNT("kill", User::getKillCount),
    DEATH_COUNT("death", User::getDeathCount),
    REFILE_EAT_COUNT("refile-eat", User::getRefileEatCount),
    KOX_EAT_COUNT("kox-eat", User::getKoxEatCount),
    PEARL_THROW_COUNT("pearl-throw", User::getPearlThrowCount),
    PLAY_TIME_COUNT("play-time", User::getPlayTime),
    WALK_DISTANCE_COUNT("walk-distance", User::getWalkDistance);

    private final String id;
    private final ToLongFunction<? super User> function;
}
