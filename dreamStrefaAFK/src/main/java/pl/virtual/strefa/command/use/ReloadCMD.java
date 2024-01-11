package pl.virtual.strefa.command.use;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.virtual.strefa.command.CommandUse;
import java.util.List;

public class ReloadCMD extends CommandUse {
    public ReloadCMD(String name, List<String> aliases) {super(name, aliases);}

    @Override
    public void run(CommandSender sender, String[] args) {
        if(sender.hasPermission(configPlugin.reloadPerms)){
            message.message(sender, configPlugin.messageReloadSuccess.type,configPlugin.messageReloadSuccess.message);
            configPlugin.load();
            configPlugin.save();
        }else{
            message.message(sender, configPlugin.messageReloadNoPerms.type,configPlugin.messageReloadNoPerms.message);
        }
    }

    @Override
    public List<String> tab(Player p, String[] args) {
        return null;
    }
}
