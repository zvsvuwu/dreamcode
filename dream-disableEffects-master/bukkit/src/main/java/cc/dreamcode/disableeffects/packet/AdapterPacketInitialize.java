package cc.dreamcode.disableeffects.packet;

import cc.dreamcode.disableeffects.BukkitDisableEffectsPlugin;
import cc.dreamcode.disableeffects.manager.RegionManager;
import com.comphenix.protocol.ProtocolLibrary;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;

public class AdapterPacketInitialize {

    private @Inject BukkitDisableEffectsPlugin bukkitDisableEffectsPlugin;
    private @Inject Tasker tasker;
    private @Inject RegionManager regionManager;

    public void init() {
        ProtocolLibrary.getProtocolManager().addPacketListener(
                new EffectPacketAdapter(this.bukkitDisableEffectsPlugin, this.tasker, this.regionManager));
    }
}
