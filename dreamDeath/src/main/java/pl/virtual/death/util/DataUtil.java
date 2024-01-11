package pl.virtual.death.util;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import pl.virtual.death.config.ConfigPlugin;
import pl.virtual.death.main.Main;

@UtilityClass
public class DataUtil {

    public Main plugin = Main.getPlugin();
    public ConfigPlugin configPlugin = plugin.getConfigPlugin();

    public boolean isInRegion(Location loc) {
        if (configPlugin.wolrdguardIntegration) {
            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionQuery query = container.createQuery();
            ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(loc));
            for (ProtectedRegion pr : set) {
                for (String region: configPlugin.onlyWorldGuardRegion) {
                    if (pr.getId().equalsIgnoreCase(region)) return true;
                }
            }
        }
        return false;
    }
}
