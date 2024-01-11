package cc.dreamcode.disableeffects.worldguard;

import org.bukkit.Location;

import java.util.List;

public interface WorldGuardHook {

    List<String> getRegions(Location location);

    boolean isInBlockedRegion(Location location);
}
