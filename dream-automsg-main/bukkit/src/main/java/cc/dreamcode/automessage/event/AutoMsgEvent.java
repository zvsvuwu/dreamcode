package cc.dreamcode.automessage.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@Setter
public class AutoMsgEvent extends Event implements Cancellable {
    private final HandlerList handlers = new HandlerList();
    private boolean cancelled;
}
