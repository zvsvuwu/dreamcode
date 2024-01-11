package cc.dreamcode.antygrief.manager;

import lombok.Getter;

import java.util.HashMap;
import java.util.UUID;

public class AntyGriefManager {
    private final @Getter HashMap<UUID, Long> playersOnCooldown = new HashMap<>();
}
