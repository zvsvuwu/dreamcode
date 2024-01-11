package pl.virutal.mobs.gui.use;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import pl.virutal.mobs.config.ConfigPlugin;
import pl.virutal.mobs.gui.GuiCreator;
import pl.virutal.mobs.main.Main;
import pl.virutal.mobs.manager.User;
import pl.virutal.mobs.util.ItemBuilder;

public class MenuGui extends GuiCreator {

    public static Main plugin = Main.getPlugin();
    public static ConfigPlugin configPlugin = plugin.getConfigPlugin();
    public static MobGui mobGui = plugin.getMobGui();

    public MenuGui() {
        super(configPlugin.guiMenuName, 3);
    }

    @Override
    public void setContents(Player p) {
        ItemBuilder head = new ItemBuilder(XMaterial.ZOMBIE_HEAD).setName("&4&lOgolna blokada mobow");
        ItemBuilder egg = new ItemBuilder(XMaterial.ZOMBIE_SPAWN_EGG).setName("&4&lBlokada mobow z jajka");
        ItemBuilder spawner = new ItemBuilder(XMaterial.SPAWNER).setName("&4&lBlokada mobow z spawnera");
        ItemBuilder command = new ItemBuilder(XMaterial.PAPER).setName("&4&lBlokada mobow z komend");
        ItemBuilder natural = new ItemBuilder(XMaterial.GRASS).setName("&4&lBlokada naturalnego spawnu mobow");
        ItemBuilder breeding = new ItemBuilder(XMaterial.WHEAT_SEEDS).setName("&4&lBlokada rozmnażania mobow");

        setItem(11, head);
        setItem(12, egg);
        setItem(13, spawner);

        setItem(15, command);
        setItem(16, natural);
        setItem(17, breeding);
    }

    @Override
    public void handleClickAction(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();
        User u = userManager.getOrCreateUser(p);
        int slot = e.getSlot() + 1;
        if (slot >= 11 && slot <= 17 && slot != 14){
            u.setType(e.getCurrentItem().getType().name());
            mobGui.show(p);
        }
    }
}
