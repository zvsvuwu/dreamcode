package pl.virtual.death.listener.use;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import pl.virtual.death.listener.ListenerUse;
import pl.virtual.death.manager.User;
import pl.virtual.death.util.DataUtil;
import pl.virtual.death.util.RandomUtil;

public class onDeathEvent extends ListenerUse {

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        if (e.getEntity() instanceof Player) {
            Player p = e.getEntity();
            User u = userManager.getOrCreateUser(p);
            if (configPlugin.wolrdguardIntegration){
                if (!DataUtil.isInRegion(p.getLocation())){
                    return;
                }
            }

            p.getWorld().dropItemNaturally(p.getLocation(), p.getInventory().getHelmet());
            p.getWorld().dropItemNaturally(p.getLocation(), p.getInventory().getChestplate());
            p.getWorld().dropItemNaturally(p.getLocation(), p.getInventory().getLeggings());
            p.getWorld().dropItemNaturally(p.getLocation(), p.getInventory().getBoots());

            p.getInventory().setHelmet(new ItemStack(Material.AIR));
            p.getInventory().setChestplate(new ItemStack(Material.AIR));
            p.getInventory().setLeggings(new ItemStack(Material.AIR));
            p.getInventory().setBoots(new ItemStack(Material.AIR));

            ItemStack[] itemStacks = p.getInventory().getContents().clone();
            if (itemStacks == null)return;
            int rantomInt = RandomUtil.getRandInt(0,1);
            for(int i=rantomInt; i <= 35; i=i+2) {
                if (itemStacks[i] == null)continue;
                p.getWorld().dropItemNaturally(p.getLocation(), itemStacks[i]);
                itemStacks[i].setType(Material.AIR);
            }
            e.getDrops().clear();
            u.setInventory(itemStacks);
        }
    }
}
