package cc.dreamcode.playtime.menus;

import cc.dreamcode.menu.bukkit.base.BukkitMenu;
import cc.dreamcode.menu.bukkit.setup.BukkitMenuPlayerSetup;
import cc.dreamcode.playtime.config.MessageConfig;
import cc.dreamcode.playtime.config.PluginConfig;
import cc.dreamcode.playtime.nms.api.NmsAccessor;
import cc.dreamcode.utilities.TimeUtil;
import cc.dreamcode.utilities.builders.MapBuilder;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

public class PlayTimeMenu implements BukkitMenuPlayerSetup {
    private @Inject PluginConfig pluginConfig;
    private @Inject MessageConfig messageConfig;
    private @Inject NmsAccessor nmsAccessor;

    @Override
    public BukkitMenu build(@NonNull HumanEntity humanEntity) {
        final Player player = (Player) humanEntity;
        final long ticks = this.nmsAccessor.getStatisticAccessor().getPlayOneTick(player);
        
        return pluginConfig.playTimeMenuConfig.playTimeMenu.buildWithItem(new MapBuilder<String, String>()
                .put("{czas}", TimeUtil.convertSeconds(ticks / 20))
                .build());
    }
}
