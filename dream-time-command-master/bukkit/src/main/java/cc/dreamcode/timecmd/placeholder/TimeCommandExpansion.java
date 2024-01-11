package cc.dreamcode.timecmd.placeholder;

import cc.dreamcode.timecmd.mcversion.api.VersionAccessor;
import cc.dreamcode.utilities.TimeUtil;
import eu.okaeri.injector.annotation.Inject;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class TimeCommandExpansion extends PlaceholderExpansion {

    private @Inject VersionAccessor versionAccessor;

    @Override
    public @NotNull String getIdentifier() {
        return "dreamtimecmd";
    }

    @Override
    public @NotNull String getAuthor() {
        return "eastcause";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if (params.equalsIgnoreCase("time")) {
            long time = ((long) versionAccessor.getTime(player) / 20L);
            return TimeUtil.convertSeconds(time);
        }
        return null;
    }
}
