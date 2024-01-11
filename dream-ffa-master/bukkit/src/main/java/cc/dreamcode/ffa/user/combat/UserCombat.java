package cc.dreamcode.ffa.user.combat;

import lombok.Data;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * UserCombat.java
 * Purpose: The UserCombat is a class that contains User combat-related stuff.
 * * @author trueman96
 * @version 1.0-beta.1
 * @since 2023-11-24
 */
@Data
public class UserCombat {

    private UUID lastAttackPlayer, lastAssistPlayer;
    private long lastAttackTime, lastAssistTime;

    public boolean isInCombat() {
        return this.lastAttackTime > System.currentTimeMillis();
    }

    public void reset() {
        this.lastAttackTime = 0L;
        this.lastAssistTime = 0L;
        this.lastAssistPlayer = null;
        this.lastAttackPlayer = null;
    }

    public void setLastAttackPlayer(Player lastAttackPlayer) {
        this.lastAttackPlayer = lastAttackPlayer.getUniqueId();
    }
}
