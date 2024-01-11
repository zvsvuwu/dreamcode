package cc.dreamcode.playtime.commands;

import cc.dreamcode.command.annotations.RequiredPermission;
import cc.dreamcode.command.annotations.RequiredPlayer;
import cc.dreamcode.command.bukkit.BukkitCommand;
import cc.dreamcode.playtime.config.MessageConfig;
import cc.dreamcode.playtime.config.PluginConfig;
import cc.dreamcode.playtime.menus.PlayTimeMenu;
import cc.dreamcode.playtime.nms.api.NmsAccessor;
import cc.dreamcode.utilities.TimeUtil;
import cc.dreamcode.utilities.builders.MapBuilder;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@RequiredPlayer
@RequiredPermission(permission = "dreamplaytime.czas")
public class PlayTimeCommand extends BukkitCommand {
    private @Inject PluginConfig pluginConfig;
    private @Inject MessageConfig messageConfig;
    private @Inject PlayTimeMenu playTimeMenu;
    private @Inject NmsAccessor nmsAccessor;

    public PlayTimeCommand() {
        super("czas", "czasgry");
    }

    @Override
    public void content(@NonNull CommandSender sender, @NonNull String[] args) {
        final Player player = (Player) sender;
        final long ticks = this.nmsAccessor.getStatisticAccessor().getPlayOneTick(player);

        if (this.pluginConfig.useGui) {
            this.playTimeMenu.build(player).open(player);
            return;
        }

        this.messageConfig.playTimeNotice.send(player, new MapBuilder<String, Object>()
                .put("czas", TimeUtil.convertSeconds(ticks / 20))
                .build());
    }

    @Override
    public List<String> tab(@NonNull Player player, @NonNull String[] args) {
        return null;
    }
}
