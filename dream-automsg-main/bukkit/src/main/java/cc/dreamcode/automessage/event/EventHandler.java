package cc.dreamcode.automessage.event;

import lombok.NonNull;
import org.bukkit.Bukkit;

public class EventHandler {

    public static boolean handle(@NonNull AutoMsgEvent autoMsgEvent) {
        Bukkit.getPluginManager().callEvent(autoMsgEvent);
        return autoMsgEvent.isCancelled();
    }
}
