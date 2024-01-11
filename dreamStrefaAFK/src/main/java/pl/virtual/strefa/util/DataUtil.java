package pl.virtual.strefa.util;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Location;
import pl.virtual.strefa.config.ConfigPlugin;
import pl.virtual.strefa.config.Cord;
import pl.virtual.strefa.main.Main;

public class DataUtil {

    public Main plugin = Main.getPlugin();
    public ConfigPlugin configPlugin = plugin.getConfigPlugin();

    public boolean isInAntyAfk(Location loc) {

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

        for(Cord st: configPlugin.afkArea){
            if ((loc.getBlockY() > st.firstY) && (loc.getBlockY() < st.secondY)) {
                if ((loc.getBlockX() > st.firstX) && (loc.getBlockX() < st.secondX)) {
                    if ((loc.getBlockZ() > st.firstZ) && (loc.getBlockZ() < st.secondZ)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
