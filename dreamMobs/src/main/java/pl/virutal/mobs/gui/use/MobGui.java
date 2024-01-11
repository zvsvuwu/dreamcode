package pl.virutal.mobs.gui.use;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import pl.virutal.mobs.config.ConfigPlugin;
import pl.virutal.mobs.gui.GuiCreator;
import pl.virutal.mobs.main.Main;
import pl.virutal.mobs.manager.User;
import pl.virutal.mobs.util.DataUtil;
import pl.virutal.mobs.util.ItemBuilder;

import java.util.Arrays;

public class MobGui extends GuiCreator {

    public static Main plugin = Main.getPlugin();
    public static ConfigPlugin configPlugin = plugin.getConfigPlugin();

    public MobGui() {
        super(configPlugin.guiMobName, 6);
    }

    @Override
    public void setContents(Player p) {
        User u = userManager.getOrCreateUser(p);
        int i = 1;
        for (XMaterial st : DataUtil.mobList) {
            String lore = "&7Status: &cWylaczony";
            ItemBuilder itemBuilder = new ItemBuilder(st).setName("&4&l" + st.parseMaterial().name());
            String name = st.parseMaterial().name().replace("_SPAWN_EGG", "");
           if (u.getType().equalsIgnoreCase("ZOMBIE_HEAD")) {
               if (configPlugin.disabledSpawnMobs.contains(name)) {
                   lore = "&7Status: &aWlaczony";
               }
           }
           else if (u.getType().equalsIgnoreCase("ZOMBIE_SPAWN_EGG")) {
               if (configPlugin.disabledSpawnMobsFromEgs.contains(name)) {
                   lore = "&7Status: &aWlaczony";
               }
           }
           else if (u.getType().equalsIgnoreCase("SPAWNER")) {
               if (configPlugin.disabledSpawnMobsFromSpawner.contains(name)) {
                   lore = "&7Status: &aWlaczony";
               }
           }
           else if (u.getType().equalsIgnoreCase("PAPER")) {
               if (configPlugin.disabledSpawnMobsFromCommand.contains(name)) {
                   lore = "&7Status: &aWlaczony";
               }
           }
           else if (u.getType().equalsIgnoreCase("GRASS")) {
               if (configPlugin.disabledSpawnMobsFromNatural.contains(name)) {
                   lore = "&7Status: &aWlaczony";
               }
           }
           else if (u.getType().equalsIgnoreCase("WHEAT_SEEDS")) {
               if (configPlugin.disabledSpawnMobsFromBreeding.contains(name)) {
                   lore = "&7Status: &aWlaczony";
               }
           }
            itemBuilder.setLore(Arrays.asList("",lore,""));
            setItem(i, itemBuilder);
            i++;
        }
    }

    @Override
    public void handleClickAction(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        User u = userManager.getOrCreateUser(p);
        String name = e.getCurrentItem().getType().name().replace("_SPAWN_EGG", "");
        if (!e.getCurrentItem().getType().name().equalsIgnoreCase("SPAWNER") && e.getCurrentItem().getType().name().contains("_SPAWN_EGG")){
            switch (u.getType().toUpperCase()) {
                case "ZOMBIE_HEAD": {
                    if (!configPlugin.disabledSpawnMobs.contains(name)) {
                        configPlugin.disabledSpawnMobs.add(name);
                    }else{
                        configPlugin.disabledSpawnMobs.remove(name);
                    }
                    break;
                }
                case "ZOMBIE_SPAWN_EGG": {
                    if (!configPlugin.disabledSpawnMobsFromEgs.contains(name)) {
                        configPlugin.disabledSpawnMobsFromEgs.add(name);
                    }else{
                        configPlugin.disabledSpawnMobsFromEgs.remove(name);
                    }
                    break;
                }
                case "SPAWNER": {
                    if (!configPlugin.disabledSpawnMobsFromSpawner.contains(name)) {
                        configPlugin.disabledSpawnMobsFromSpawner.add(name);
                    }else{
                        configPlugin.disabledSpawnMobsFromSpawner.remove(name);
                    }
                    break;
                }
                case "PAPER": {
                    if (!configPlugin.disabledSpawnMobsFromCommand.contains(name)) {
                        configPlugin.disabledSpawnMobsFromCommand.add(name);
                    }else{
                        configPlugin.disabledSpawnMobsFromCommand.remove(name);
                    }
                    break;
                }
                case "GRASS": {
                    if (!configPlugin.disabledSpawnMobsFromNatural.contains(name)) {
                        configPlugin.disabledSpawnMobsFromNatural.add(name);
                    }else{
                        configPlugin.disabledSpawnMobsFromNatural.remove(name);
                    }
                    break;
                }
                case "WHEAT_SEEDS": {
                    if (!configPlugin.disabledSpawnMobsFromBreeding.contains(name)) {
                        configPlugin.disabledSpawnMobsFromBreeding.add(name);
                    }else{
                        configPlugin.disabledSpawnMobsFromBreeding.remove(name);
                    }
                    break;
                }
            }
            show(p);
        }else{
            message.message(p, configPlugin.guiErrorMob.type, configPlugin.guiErrorMob.message);
        }
    }
}
