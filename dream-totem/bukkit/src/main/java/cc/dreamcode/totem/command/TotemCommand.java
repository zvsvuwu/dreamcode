package cc.dreamcode.totem.command;

import cc.dreamcode.command.annotations.RequiredPermission;
import cc.dreamcode.command.annotations.RequiredPlayer;
import cc.dreamcode.command.bukkit.BukkitCommand;
import cc.dreamcode.totem.config.MessageConfig;
import cc.dreamcode.totem.config.PluginConfig;
import cc.dreamcode.totem.inventory.TotemMenuHolder;
import cc.dreamcode.utilities.bukkit.ChatUtil;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

@RequiredPlayer
public class TotemCommand extends BukkitCommand {
    @Inject private TotemMenuHolder totemMenuHolder;
    @Inject private PluginConfig pluginConfig;
    @Inject private MessageConfig messageConfig;

    public TotemCommand() {
        super("totem");
    }

    @Override
    public void content(@NonNull CommandSender sender, @NonNull String[] args) {
        final Player player = (Player)sender;
        if (args.length == 0) {
            this.totemMenuHolder.open(player);
            return;
        }

        if (!player.hasPermission("dream.totem.reload")) {
            return;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            this.pluginConfig.load();
            this.messageConfig.load();
            this.totemMenuHolder.update();
            player.sendMessage(ChatUtil.fixColor("&a&lPomyślnie przeładowano konfigurację."));
        }
    }

    @Override
    public List<String> tab(@NonNull CommandSender sender, @NonNull String[] args) {
        if(args.length == 1 && sender.hasPermission("dream.totem.reload")){
            return Collections.singletonList("reload");
        }

        return Collections.emptyList();
    }
}
