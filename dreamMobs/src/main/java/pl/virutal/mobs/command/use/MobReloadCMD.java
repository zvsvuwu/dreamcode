package pl.virutal.mobs.command.use;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.virutal.mobs.command.CommandUse;

import java.util.List;

public class MobReloadCMD extends CommandUse {
    public MobReloadCMD(String name, List<String> aliases) {super(name, aliases);}

    @Override
    public void run(CommandSender sender, String[] args) {
        if(sender.hasPermission(configPlugin.reloadPrems)){
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
