package cc.dreamcode.disableeffects.worldguard;

import cc.dreamcode.disableeffects.config.PluginConfig;
import com.sk89q.worldguard.bukkit.RegionContainer;
import com.sk89q.worldguard.bukkit.RegionQuery;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;

import java.util.List;
import java.util.stream.Collectors;

public class WorldGuardHookV6 implements WorldGuardHook {

    private final RegionContainer regionContainer;
    private final PluginConfig pluginConfig;

    public WorldGuardHookV6(final RegionContainer regionContainer, final PluginConfig pluginConfig) {
        this.regionContainer = regionContainer;
        this.pluginConfig = pluginConfig;
    }

    public List<String> getRegions(Location location) {
        RegionQuery regionQuery = this.regionContainer.createQuery();
        ApplicableRegionSet regions = regionQuery.getApplicableRegions(location);
        return regions.getRegions().stream().map(ProtectedRegion::getId).collect(Collectors.toList());
    }

    public boolean isInBlockedRegion(Location location) {
        if (this.pluginConfig.disabledWorlds.contains(location.getWorld().getName())) {
            return true;
        }
        List<String> regions = getRegions(location);
        return regions.stream().anyMatch(this.pluginConfig.disabledRegions::contains);
    }

}
