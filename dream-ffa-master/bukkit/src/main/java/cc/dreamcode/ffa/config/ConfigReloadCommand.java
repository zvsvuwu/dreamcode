package cc.dreamcode.ffa.config;

import cc.dreamcode.command.bukkit.BukkitCommand;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ConfigReloadCommand extends BukkitCommand {

    private final PluginConfig pluginConfig;
    private final MessageConfig messageConfig;

    @Inject
    public ConfigReloadCommand(PluginConfig pluginConfig, MessageConfig messageConfig) {
        super("ffareload");
        this.pluginConfig = pluginConfig;
        this.messageConfig = messageConfig;
    }

    @Override
    public void content(@NonNull CommandSender sender, @NonNull String[] args) {
        this.messageConfig.load(true);
        this.pluginConfig.load(true);
        this.messageConfig.reloaded.send(sender);
    }

    @Override
    public List<String> tab(@NonNull CommandSender sender, @NonNull String[] args) {
        return null;
    }
}
