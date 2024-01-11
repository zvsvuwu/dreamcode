package cc.dreamcode.spawn.command;

import cc.dreamcode.command.annotations.RequiredPlayer;
import cc.dreamcode.command.bukkit.BukkitCommand;
import cc.dreamcode.spawn.SpawnManager;
import cc.dreamcode.spawn.config.MessageConfig;
import cc.dreamcode.spawn.config.PluginConfig;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@RequiredPlayer
public class SetSpawnCommand extends BukkitCommand {

    private final SpawnManager spawnManager;
    private final PluginConfig config;
    private final MessageConfig messageConfig;

    @Inject
    public SetSpawnCommand(final SpawnManager spawnManager, final PluginConfig config, final MessageConfig messageConfig) {
        super("setspawn");
        this.spawnManager = spawnManager;
        this.config = config;
        this.messageConfig = messageConfig;
    }

    @Override
    public void content(@NonNull CommandSender sender, @NonNull String[] args) {
        Player player = (Player) sender;

        if(!player.hasPermission(config.adminPermission)) {
            messageConfig.noPermission.send(sender);
            return;
        }

        spawnManager.setSpawnLocation(player.getLocation());
        messageConfig.setSpawn.send(player);
    }

    @Override
    public List<String> tab(@NonNull CommandSender sender, @NonNull String[] args) {
        return null;
    }
}
