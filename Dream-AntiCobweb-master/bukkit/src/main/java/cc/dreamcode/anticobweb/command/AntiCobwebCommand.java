package cc.dreamcode.anticobweb.command;

import cc.dreamcode.anticobweb.config.MessagesConfig;
import cc.dreamcode.anticobweb.config.PluginConfig;
import cc.dreamcode.command.annotations.RequiredPermission;
import cc.dreamcode.command.annotations.RequiredPlayer;
import cc.dreamcode.command.bukkit.BukkitCommand;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@RequiredPlayer
@RequiredPermission(permission = "dream-anticobweb.command")
public class AntiCobwebCommand extends BukkitCommand {

    private @Inject PluginConfig pluginConfig;
    public @Inject MessagesConfig messagesConfig;

    public AntiCobwebCommand() {
        super("anticobweb", "cobweb");
    }

    @Override
    public void content(@NonNull CommandSender sender, @NonNull String[] args) {
        Player player = (Player) sender;
        this.messagesConfig.giveMessage.send(player);
        player.getInventory().addItem(ItemBuilder.of(this.pluginConfig.item).fixColors().toItemStack());
    }

    @Override
    public List<String> tab(@NonNull Player player, @NonNull String[] args) {
        return null;
    }
}
