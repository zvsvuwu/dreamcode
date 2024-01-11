package cc.dreamcode.shopforkills.command;

import cc.dreamcode.command.bukkit.BukkitCommand;
import cc.dreamcode.shopforkills.menu.ShopMenu;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ShopForKillsCommmand extends BukkitCommand {

    private @Inject ShopMenu shopMenu;

    public ShopForKillsCommmand() {
        super("sklepzakille");
    }

    @Override
    public void content(@NonNull CommandSender sender, @NonNull String[] args) {
        Player player = (Player) sender;
        this.shopMenu.build(player).open(player);
    }

    @Override
    public List<String> tab(@NonNull CommandSender sender, @NonNull String[] args) {
        return null;
    }
}
