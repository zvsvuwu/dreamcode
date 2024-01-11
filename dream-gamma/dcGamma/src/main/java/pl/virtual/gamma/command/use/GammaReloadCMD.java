package pl.virtual.gamma.command.use;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.virtual.gamma.command.CommandUse;

import java.util.List;

public class GammaReloadCMD extends CommandUse{
    public GammaReloadCMD(String name, List<String> aliases) { super(name, aliases); }

    @Override
    public void run(CommandSender sender, String[] args) {
        configPlugin.load();
        configPlugin.save();
        message.message(sender, configPlugin.messageReload.type, configPlugin.messageReload.message);
    }

    @Override
    public List<String> tab(Player p, String[] args) {
        return null;
    }
}
