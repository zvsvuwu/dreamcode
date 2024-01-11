package cc.dreamcode.ffa.user.command;

import cc.dreamcode.command.bukkit.BukkitCommand;
import cc.dreamcode.ffa.config.MessageConfig;
import cc.dreamcode.ffa.config.PluginConfig;
import cc.dreamcode.ffa.user.User;
import cc.dreamcode.ffa.user.UserCache;
import cc.dreamcode.ffa.user.menu.SaveInventoryMenu;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SaveInventoryCommand extends BukkitCommand {

    private final UserCache userCache;

    private final Tasker tasker;

    private final PluginConfig pluginConfig;
    private final MessageConfig messageConfig;

    @Inject
    public SaveInventoryCommand(UserCache userCache, Tasker tasker, PluginConfig pluginConfig, MessageConfig messageConfig) {
        super("saveeq", "zapiszeq");
        this.userCache = userCache;
        this.tasker = tasker;
        this.pluginConfig = pluginConfig;
        this.messageConfig = messageConfig;
    }

    @Override
    public void content(@NonNull CommandSender sender, @NonNull String[] args) {
        if (!(sender instanceof Player)) {
            return;
        }
        Player player = (Player) sender;
        final User user = this.userCache.get(player);
        new SaveInventoryMenu(user, player, this.tasker, this.pluginConfig, this.messageConfig).open();
    }

    @Override
    public List<String> tab(@NonNull CommandSender sender, @NonNull String[] args) {
        return null;
    }
}
