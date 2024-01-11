package pl.virtual.elytra.util;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Location;
import pl.virtual.elytra.config.ConfigPlugin;
import pl.virtual.elytra.config.Cord;
import pl.virtual.elytra.main.Main;

public class DataUtil {

    public Main plugin = Main.getPlugin();
    public ConfigPlugin configPlugin = plugin.getConfigPlugin();

    public boolean isInRegion(Location loc) {

        if (configPlugin.wolrdguardIntegration) {
            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionQuery query = container.createQuery();
            ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(loc));
            for (ProtectedRegion pr : set) {
                for (String region: configPlugin.worldguardRegions) {
                    if (pr.getId().equalsIgnoreCase(region)) return true;
                }
            }
        }
        if(configPlugin.areaIntegration) {
            for (Cord st : configPlugin.area) {
                if ((loc.getBlockY() > st.firstY) && (loc.getBlockY() < st.secondY)) {
                    if ((loc.getBlockX() > st.firstX) && (loc.getBlockX() < st.secondX)) {
                        if ((loc.getBlockZ() > st.firstZ) && (loc.getBlockZ() < st.secondZ)) {
                            return true;
                        }
                    }
                }
            }
        }
        if (configPlugin.worldIntegration) {
            for (String st : configPlugin.worldName) {
                if (loc.getWorld().getName().equals(st)) {
                    return true;
                }
            }
        }
        return false;
    }
}
