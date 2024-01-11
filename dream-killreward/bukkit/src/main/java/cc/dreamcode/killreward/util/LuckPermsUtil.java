package cc.dreamcode.killreward.util;

import org.bukkit.entity.Player;

public class LuckPermsUtil {

    public static boolean isPlayerInGroup(Player player, String group) {
        return player.hasPermission("group." + group);
    }
}
