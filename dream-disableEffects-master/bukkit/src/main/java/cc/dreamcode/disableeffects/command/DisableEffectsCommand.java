package cc.dreamcode.disableeffects.command;

import cc.dreamcode.command.annotations.RequiredPermission;
import cc.dreamcode.command.bukkit.BukkitCommand;
import cc.dreamcode.disableeffects.config.MessageConfig;
import cc.dreamcode.disableeffects.config.PluginConfig;
import cc.dreamcode.disableeffects.manager.RegionManager;
import cc.dreamcode.disableeffects.util.EffectRemoveUtil;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@RequiredPermission(permission = "disableeffects.command.admin")
public class DisableEffectsCommand extends BukkitCommand {

    private @Inject MessageConfig messageConfig;
    private @Inject PluginConfig pluginConfig;
    private @Inject RegionManager regionManager;

    public DisableEffectsCommand() {
        super("disableeffects");
    }

    @Override
    public void content(@NonNull CommandSender sender, @NonNull String[] args) {
        this.messageConfig.load();
        this.pluginConfig.load();
        this.messageConfig.reload.send(sender);

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (this.regionManager.isInBlockedRegion(player.getLocation())) {
                EffectRemoveUtil.removeAllEffects(player);
            }
        }
    }

    @Override
    public List<String> tab(@NonNull CommandSender sender, @NonNull String[] args) {
        return null;
    }
}
