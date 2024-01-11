package pl.virutal.mobs.command.use;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.virutal.mobs.command.CommandUse;

import java.util.List;

public class MobGuiCMD extends CommandUse {
    public MobGuiCMD(String name, List<String> aliases) {super(name, aliases);}

    @Override
    public void run(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (sender.hasPermission(configPlugin.guiPrems)) {
                menuGui.show(p);
            } else {
                message.message(sender, configPlugin.messageGuiNoPerms.type, configPlugin.messageGuiNoPerms.message);
            }
        }else{
            System.out.println("Ta komenda jest tylko dla graczy");
        }
    }

    @Override
    public List<String> tab(Player p, String[] args) {
        return null;
    }
}
