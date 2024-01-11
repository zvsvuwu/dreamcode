package cc.dreamcode.antygrief.command;

import cc.dreamcode.antygrief.config.MessageConfig;
import cc.dreamcode.antygrief.config.PluginConfig;
import cc.dreamcode.command.annotations.RequiredPermission;
import cc.dreamcode.command.annotations.RequiredPlayer;
import cc.dreamcode.command.bukkit.BukkitCommand;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

@RequiredPlayer
@RequiredPermission(permission = "dream-antigrief.reload")
public class AntiGriefCommand extends BukkitCommand {
    private @Inject PluginConfig pluginConfig;
    private @Inject MessageConfig messageConfig;

    public AntiGriefCommand() {
        super("antigrief");
    }

    @Override
    public void content(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        this.pluginConfig.load();

        this.messageConfig.reloadedConfig.send(sender);
    }

    @Override
    public List<String> tab(@NonNull Player player, @NotNull @NonNull String[] args) {
        return Collections.singletonList("reload");
    }
}
