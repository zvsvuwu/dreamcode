package cc.dreamcode.antygrief.hook.funnyguilds;

import cc.dreamcode.antygrief.hook.PluginHook;
import cc.dreamcode.antygrief.hook.PluginHookType;
import net.dzikoysk.funnyguilds.FunnyGuilds;
import org.bukkit.Location;


public class FunnyGuildsHook implements PluginHook {

    @Override
    public PluginHookType getPluginHookType() {
        return PluginHookType.FUNNY_GUILDS;
    }

    public boolean onGuildRegion(Location location) {
        FunnyGuilds funnyGuilds = FunnyGuilds.getInstance();

        return funnyGuilds.getRegionManager().isInRegion(location);
    }

}
