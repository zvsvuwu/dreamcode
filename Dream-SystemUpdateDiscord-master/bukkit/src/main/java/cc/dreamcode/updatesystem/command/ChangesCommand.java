package cc.dreamcode.updatesystem.command;

import cc.dreamcode.command.bukkit.BukkitCommand;
import cc.dreamcode.updatesystem.config.MessageConfig;
import cc.dreamcode.updatesystem.config.PluginConfig;
import cc.dreamcode.updatesystem.menu.MessageMenu;
import cc.dreamcode.updatesystem.message.MessageManager;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ChangesCommand extends BukkitCommand {
    public ChangesCommand() {
        super("zmiany");
    }

    @Inject private MessageConfig messageConfig;
    @Inject private PluginConfig pluginConfig;
    @Inject private MessageMenu messageMenu;
    @Inject private MessageManager messageManager;

    @Override
    public void content(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        if(args.length != 0 && args[0].equalsIgnoreCase("reload") && sender.hasPermission("systemupdate.command.admin")){
            this.messageConfig.load();
            this.pluginConfig.load();

            this.messageMenu.update(this.messageManager.getMessages().values());

            this.messageConfig.reload.send(sender);
            return;
        }
        tryOpen((Player) sender);
    }

    public void tryOpen(Player player) {
        if (((System.currentTimeMillis() - this.messageMenu.getLastUpdate()) / 1000L) > 3600) {
            this.messageMenu.update(this.messageManager.getMessages().values());
        }
        this.messageMenu.getMessageMenu().open(player);
    }

    @Override
    public List<String> tab(@NonNull Player player, @NotNull @NonNull String[] args) {
        return null;
    }
}
