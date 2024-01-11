package cc.dreamcode.helpop.command;

import cc.dreamcode.helpop.HelpopPlugin;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public interface CommandPlatform {
    void handle(@NonNull CommandSender sender, @NonNull String[] args);

    List<String> tab(@NonNull Player player, @NonNull String[] args);

    default <T> T createInstance(@NonNull Class<T> clazz) {
        return HelpopPlugin.getHelpopPlugin().createInstance(clazz);
    }
}
