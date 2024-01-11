package pl.virtual.gamma.command;

import lombok.NonNull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pl.virtual.gamma.config.ConfigPlugin;
import pl.virtual.gamma.main.Main;
import pl.virtual.gamma.manager.UserManager;
import pl.virtual.gamma.util.Message;

import java.util.ArrayList;
import java.util.List;

public abstract class CommandUse extends Command implements PluginIdentifiableCommand {

    public Main plugin = Main.getPlugin();
    public ConfigPlugin configPlugin = plugin.getConfigPlugin();
    public UserManager userManager = plugin.getUserManager();
    public Message message = plugin.getMessage();

    public CommandUse(String name, List<String> aliases) {
        super(name);
        if(aliases != null) {
            setAliases(aliases);
        }
    }

    @NonNull
    @Override
    public Plugin getPlugin() {
        return this.plugin;
    }

    public abstract void run(CommandSender sender, String[] args);
    public abstract List<String> tab(Player p, String[] args);

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] arguments) {
        run(sender, arguments);
        return true;
    }

    public List<String> tabComplete(CommandSender sender, String label, String[] args) {
        List<String> completions = tab((Player) sender, args);
        if(completions == null) return new ArrayList<>();
        return completions;
    }

}
