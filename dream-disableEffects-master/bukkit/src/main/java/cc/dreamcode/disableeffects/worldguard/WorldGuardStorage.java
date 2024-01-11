package cc.dreamcode.disableeffects.worldguard;

import cc.dreamcode.disableeffects.BukkitDisableEffectsPlugin;
import cc.dreamcode.disableeffects.config.PluginConfig;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import eu.okaeri.injector.annotation.Inject;
import lombok.Getter;
import org.bukkit.plugin.Plugin;

public class WorldGuardStorage {

    private @Inject BukkitDisableEffectsPlugin bukkitDisableEffectsPlugin;
    private @Inject PluginConfig pluginConfig;
    private @Getter boolean findWorldGuard = false;
    private @Getter WorldGuardHook worldGuardHook;

    public boolean init() {
        try {
            Plugin plugin = this.bukkitDisableEffectsPlugin.getServer().getPluginManager().getPlugin("WorldGuard");
            if (plugin == null) {
                return false;
            }
            if (buildHook(plugin)) {
                findWorldGuard = true;
                return true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean buildHook(Plugin plugin) {
        String version = plugin.getDescription().getVersion();
        if (version.startsWith("6")) {
            this.worldGuardHook = new WorldGuardHookV6(WorldGuardPlugin.inst().getRegionContainer(), this.pluginConfig);
        }
        else if (version.startsWith("7")) {
            this.worldGuardHook = new WorldGuardHookV7(WorldGuard.getInstance().getPlatform().getRegionContainer(), this.pluginConfig);
        }
        else {
            this.bukkitDisableEffectsPlugin.getDreamLogger().error("Uzywasz niewspieranej wersji worldguarda, wspierane wersje 6.x, 7.x!");
            return false;
        }
        return true;
    }


}
