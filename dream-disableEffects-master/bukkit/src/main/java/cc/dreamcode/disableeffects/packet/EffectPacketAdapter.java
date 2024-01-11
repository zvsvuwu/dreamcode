package cc.dreamcode.disableeffects.packet;

import cc.dreamcode.disableeffects.manager.RegionManager;
import cc.dreamcode.disableeffects.util.EffectRemoveUtil;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import eu.okaeri.tasker.core.Tasker;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class EffectPacketAdapter extends PacketAdapter {

    private final Tasker tasker;
    private final RegionManager regionManager;

    public EffectPacketAdapter(final Plugin plugin, final Tasker tasker, final RegionManager regionManager) {
        super(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.ENTITY_EFFECT);
        this.tasker = tasker;
        this.regionManager = regionManager;
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        Player player = event.getPlayer();

        if (this.regionManager.isInBlockedRegion(player.getLocation())) {
            this.tasker.newChain()
                    .sync(() -> EffectRemoveUtil.removeAllEffects(player))
                    .execute();
        }


    }

}
