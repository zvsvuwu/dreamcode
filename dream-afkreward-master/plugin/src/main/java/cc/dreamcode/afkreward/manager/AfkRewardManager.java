package cc.dreamcode.afkreward.manager;

import lombok.Getter;

import java.util.HashMap;
import java.util.UUID;

public class AfkRewardManager {
    private final @Getter HashMap<UUID, Long> afkPlayers = new HashMap<>();
}
