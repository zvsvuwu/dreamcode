package cc.dreamcode.ores.content;

import cc.dreamcode.ores.command.CommandHandler;
import cc.dreamcode.ores.command.annotations.RequiredPermission;
import cc.dreamcode.ores.command.annotations.RequiredPlayer;
import cc.dreamcode.ores.config.MessageConfig;
import cc.dreamcode.ores.config.PluginConfig;
import com.google.common.collect.ImmutableMap;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@RequiredPlayer
@RequiredPermission(permission = "dreamores.reload")
public class OresCommand extends CommandHandler {
    private @Inject PluginConfig pluginConfig;
    private @Inject MessageConfig messageConfig;

    public OresCommand() {
        super("ores", null);
    }

    @Override
    public void handle(@NonNull CommandSender sender, @NonNull String[] args) {
        if (args.length != 1) {
            send(messageConfig.usage, sender, new ImmutableMap.Builder<String, Object>()
                    .put("usage", "/ores reload")
                    .build());
            return;
        }
        pluginConfig.load();
        send(messageConfig.reloaded, sender);
    }

    @Override
    public List<String> tab(@NonNull Player player, @NonNull String[] args) {
        if (args.length == 1) {
            List<String> arguments = new ArrayList<>();
            arguments.add("reload");
            return arguments;
        }
        return null;
    }
}
