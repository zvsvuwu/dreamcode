package cc.dreamcode.leaderboard;

import cc.dreamcode.leaderboard.model.user.UserRepository;
import eu.okaeri.injector.annotation.Inject;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class LeaderboardService {

    private @Inject UserRepository userRepository;

    @Getter private Map<LeaderboardType, Map<Long, Top>> leaderboardMap = this.emptyLeaderboard();

    public Map<LeaderboardType, Map<Long, Top>> emptyLeaderboard() {
        final Map<LeaderboardType, Map<Long, Top>> emptyLeaderboardMap = new HashMap<>();

        Arrays.stream(LeaderboardType.values()).forEach(leaderboardType ->
                emptyLeaderboardMap.put(leaderboardType, new HashMap<>()));

        return emptyLeaderboardMap;
    }

    public void updateMap() {
        this.leaderboardMap = this.emptyLeaderboard();

        Arrays.stream(LeaderboardType.values()).forEach(leaderboardType -> {
            final AtomicLong atomicLong = new AtomicLong(1);
            this.userRepository.streamAll()
                    .sorted(Comparator.comparingLong(leaderboardType.getFunction()).reversed())
                    .map(user -> new Top(user.getName(), leaderboardType.getFunction().applyAsLong(user)))
                    .forEach(top -> this.leaderboardMap.get(leaderboardType).put(atomicLong.getAndIncrement(), top));
        });
    }

    public long getPosition(@NonNull LeaderboardType leaderboardType, @NonNull String username) {
        return this.leaderboardMap.entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(leaderboardType))
                .map(Map.Entry::getValue)
                .map(longTopMap -> longTopMap.entrySet()
                        .stream()
                        .filter(entry -> entry.getValue().getUsername().equalsIgnoreCase(username))
                        .map(Map.Entry::getKey)
                        .findFirst()
                        .orElse(0L))
                .findFirst()
                .orElse(0L);
    }

    @Data
    @RequiredArgsConstructor
    public static class Top {

        private final String username;
        private final long count;

    }

}
