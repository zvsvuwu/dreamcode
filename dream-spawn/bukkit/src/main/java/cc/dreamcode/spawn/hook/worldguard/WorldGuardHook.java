package cc.dreamcode.spawn.hook.worldguard;


import cc.dreamcode.spawn.hook.PluginHook;
import cc.dreamcode.spawn.hook.PluginHookType;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Location;
import org.bukkit.block.Block;

public class WorldGuardHook implements PluginHook {

    @Override
    public PluginHookType getPluginHookType() {
        return PluginHookType.WORLD_GUARD;
    }

    public ApplicableRegionSet getRegions(Location location) {
        Block block = location.getBlock();
        RegionContainer regionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regionManager = regionContainer.get(BukkitAdapter.adapt(block.getWorld()));
        if (regionManager == null) {
            return null;
        }
        return regionManager.getApplicableRegions(BukkitAdapter.asBlockVector(block.getLocation()));
    }
}