package cc.dreamcode.enderchest.command;

import cc.dreamcode.command.annotations.RequiredPermission;
import cc.dreamcode.command.bukkit.BukkitCommand;
import cc.dreamcode.enderchest.ec.EnderchestManager;
import cc.dreamcode.enderchest.user.UserManager;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@RequiredPermission(permission = "ec.command.enderchest")
public class EnderchestCommand extends BukkitCommand {

    private @Inject EnderchestManager enderchestManager;
    private @Inject UserManager userManager;

    public EnderchestCommand() {
        super("enderchest", "ec");
    }

    @Override
    public void content(@NonNull CommandSender sender, @NonNull String[] args) {
        Player player = (Player) sender;
        this.enderchestManager.openMenu(player);
    }

    @Override
    public List<String> tab(@NonNull CommandSender sender, @NonNull String[] args) {
        return null;
    }
}
