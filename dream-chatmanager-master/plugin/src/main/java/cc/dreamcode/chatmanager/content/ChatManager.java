package cc.dreamcode.chatmanager.content;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

public class ChatManager {
    @Getter @Setter private boolean chatLocked = false;

    @Getter private final Map<UUID, Long> playersOnCooldown = new HashMap<>();
}
