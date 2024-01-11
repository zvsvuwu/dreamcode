package cc.dreamcode.firework.command;

import cc.dreamcode.command.annotations.RequiredPermission;
import cc.dreamcode.command.bukkit.BukkitCommand;
import cc.dreamcode.firework.FireWorkPlugin;
import cc.dreamcode.firework.config.MessageConfig;
import cc.dreamcode.firework.config.PluginConfig;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

@RequiredPermission
public class FireWorkCommand extends BukkitCommand {

    private @Inject FireWorkPlugin fireWorkPlugin;
    private @Inject MessageConfig messageConfig;
    private @Inject PluginConfig pluginConfig;

    public FireWorkCommand() {
        super("fajerwerka");
    }

    @Override
    public void content(@NonNull CommandSender sender, @NonNull String[] args) {
        if (args.length == 0) {
            this.messageConfig.usage.send(sender, new MapBuilder<String, Object>()
                    .put("usage", "/fajerwerka <player/all>")
                    .build());
            return;
        }

        final ItemStack firework = ItemBuilder.of(this.pluginConfig.fireworkItem)
                .fixColors()
                .toItemStack();

        if (args[0].equalsIgnoreCase("all")) {
            this.fireWorkPlugin.getServer().getOnlinePlayers()
                    .stream()
                    .filter(onlinePlayer -> !onlinePlayer.getInventory().containsAtLeast(firework, 1))
                    .forEach(onlinePlayer -> onlinePlayer.getInventory().addItem(firework).forEach((i, item) ->
                            onlinePlayer.getWorld().dropItem(onlinePlayer.getLocation(), item)));

            this.messageConfig.fireWorkGiven.send(sender);
            this.messageConfig.fireWorkReceived.sendAll(new MapBuilder<String, Object>()
                    .put("admin", sender.getName())
                    .build()
            );

            return;
        }

        final Player customer = this.fireWorkPlugin.getServer().getPlayerExact(args[0]);
        if (customer == null) {
            this.messageConfig.noPlayer.send(sender);
            return;
        }

        if (customer.getInventory().containsAtLeast(firework, 1)) {
            this.messageConfig.fireWorkExists.send(customer);
            return;
        }

        customer.getInventory().addItem(firework).forEach((i, item) ->
                customer.getWorld().dropItem(customer.getLocation(), item));

        this.messageConfig.fireWorkReceived.send(customer, new MapBuilder<String, Object>()
                .put("admin", sender.getName())
                .build());

        if (!customer.getName().equals(sender.getName())) {
            this.messageConfig.fireWorkPlayerGiven.send(sender, new MapBuilder<String, Object>()
                    .put("player", customer.getName())
                    .build());
        }
    }

    @Override
    public List<String> tab(@NonNull CommandSender sender, @NonNull String[] args) {
        if (args.length == 1) {
            return this.fireWorkPlugin.getServer().getOnlinePlayers()
                    .stream()
                    .map(Player::getName)
                    .collect(Collectors.toList());
        }

        return null;
    }
}
