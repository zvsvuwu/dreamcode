package cc.dreamcode.tops.command;

import cc.dreamcode.command.annotations.RequiredPlayer;
import cc.dreamcode.command.bukkit.BukkitCommand;
import cc.dreamcode.tops.user.top.TopManager;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.List;

@RequiredPlayer
public class TopCommand extends BukkitCommand {

    private @Inject TopManager topManager;

    public TopCommand() {
        super("top", "topki");
    }

    @Override
    public void content(@NonNull CommandSender sender, @NonNull String[] args) {
        this.topManager.openMenu((HumanEntity) sender);
    }

    @Override
    public List<String> tab(@NonNull Player player, @NonNull String[] args) {
        return null;
    }
}