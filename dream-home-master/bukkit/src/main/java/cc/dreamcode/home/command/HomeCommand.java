package cc.dreamcode.home.command;

import cc.dreamcode.command.annotations.RequiredPermission;
import cc.dreamcode.command.annotations.RequiredPlayer;
import cc.dreamcode.command.bukkit.BukkitCommand;
import cc.dreamcode.home.manager.UserManager;
import cc.dreamcode.home.menu.HomeMenu;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@RequiredPlayer
@RequiredPermission(permission = "dream-home.home")
public class HomeCommand extends BukkitCommand {
    private @Inject UserManager userManager;

    public HomeCommand() {
        super("home", "homes", "dom", "domy", "sethome", "ustawdom", "nowydom");
    }

    @Override
    public void content(@NonNull CommandSender sender, @NonNull String[] args) {
        Player player = (Player) sender;
        HomeMenu homeMenu = this.createInstance(HomeMenu.class);

        homeMenu.setUser(this.userManager.getUserByPlayer(player));

        homeMenu.build(player).open(player);
    }

    @Override
    public List<String> tab(@NonNull Player player, @NonNull String[] args) {
        return null;
    }
}
