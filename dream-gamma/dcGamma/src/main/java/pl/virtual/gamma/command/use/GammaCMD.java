package pl.virtual.gamma.command.use;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.virtual.gamma.command.CommandUse;
import pl.virtual.gamma.manager.User;

import java.util.List;

public class GammaCMD extends CommandUse {
    public GammaCMD(String name, List<String> aliases) { super(name, aliases); }

    @Override
    public void run(CommandSender sender, String[] args) {
        if(sender instanceof Player){
            Player p = (Player)sender;
            User u = userManager.getOrCreateUser(p);
            u.setGamma(!u.isGamma());

            if (u.isGamma()) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999999, 1));
                message.message(p, configPlugin.messageCommandOn.type, configPlugin.messageCommandOn.message);
            }
            else {
                p.removePotionEffect(PotionEffectType.NIGHT_VISION);
                message.message(p, configPlugin.messageCommandOff.type, configPlugin.messageCommandOff.message);
            }

        }else System.out.println("Tak komenda jest tylko dla graczy");
    }

    @Override
    public List<String> tab(Player p, String[] args) {
        return null;
    }
}
