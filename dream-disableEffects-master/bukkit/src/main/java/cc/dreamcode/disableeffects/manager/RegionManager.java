package cc.dreamcode.disableeffects.manager;

import cc.dreamcode.disableeffects.config.PluginConfig;
import cc.dreamcode.disableeffects.worldguard.WorldGuardStorage;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.Location;

public class RegionManager {

    private @Inject WorldGuardStorage worldGuardStorage;
    private @Inject PluginConfig pluginConfig;

    public boolean isInBlockedRegion(Location location) {
        if (this.worldGuardStorage.isFindWorldGuard()) {
            return this.worldGuardStorage.getWorldGuardHook().isInBlockedRegion(location);
        }
        return this.pluginConfig.disabledWorlds.contains(location.getWorld().getName());
    }
}
