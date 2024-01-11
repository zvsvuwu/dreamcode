package cc.dreamcode.ffa.user.command;

import cc.dreamcode.command.bukkit.BukkitCommand;
import cc.dreamcode.ffa.config.MessageConfig;
import cc.dreamcode.ffa.config.PluginConfig;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SetSpawnCommand extends BukkitCommand {

    private final Tasker tasker;

    private final PluginConfig pluginConfig;
    private final MessageConfig messageConfig;

    @Inject
    public SetSpawnCommand(Tasker tasker, PluginConfig pluginConfig, MessageConfig messageConfig) {
        super("setspawn");
        this.tasker = tasker;
        this.pluginConfig = pluginConfig;
        this.messageConfig = messageConfig;
    }

    @Override
    public void content(@NonNull CommandSender sender, @NonNull String[] args) {
        if (!(sender instanceof Player)) {
            return;
        }
        if (!sender.hasPermission(this.pluginConfig.spawnLocationPermission)) {
            this.messageConfig.noPermission.send(sender);
            return;
        }

        final Player player = (Player) sender;
        this.pluginConfig.spawnLocation = player.getLocation();
        this.tasker.newSharedChain("config-sync")
                .async(() -> this.pluginConfig.save())
                .execute();
        this.pluginConfig.spawnLocationUpdated.send(player);
    }

    @Override
    public List<String> tab(@NonNull CommandSender sender, @NonNull String[] args) {
        return null;
    }
}
