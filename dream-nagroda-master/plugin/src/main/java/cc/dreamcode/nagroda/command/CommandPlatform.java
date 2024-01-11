package cc.dreamcode.nagroda.command;

import cc.dreamcode.nagroda.NagrodaPlugin;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface CommandPlatform {
    void handle(@NonNull CommandSender sender, @NonNull String[] args) throws ExecutionException, InterruptedException;

    List<String> tab(@NonNull Player player, @NonNull String[] args);

    default <T> T createInstance(@NonNull Class<T> clazz) {
        return NagrodaPlugin.getNagrodaPlugin().createInstance(clazz);
    }
}
