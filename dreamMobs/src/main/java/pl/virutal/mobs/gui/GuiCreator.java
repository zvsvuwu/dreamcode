package pl.virutal.mobs.gui;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import pl.virutal.mobs.main.Main;
import pl.virutal.mobs.manager.UserManager;
import pl.virutal.mobs.util.ChatUtil;
import pl.virutal.mobs.util.ItemBuilder;
import pl.virutal.mobs.util.Message;

@Getter
public abstract class GuiCreator implements InventoryHolder {

    protected final Inventory inv;
    private final String title;
    private final int size;
    public Main plugin = Main.getPlugin();
    public UserManager userManager = plugin.getUserManager();
    public Message message = plugin.getMessage();

    public GuiCreator(String title, int rows) {
        this.title = title;
        this.size = rows * 9;
        this.inv = Bukkit.createInventory(this, size, ChatUtil.fixColor(title));
    }
    public void setItem(int i, ItemBuilder itemBuilder){
        inv.setItem(i - 1, itemBuilder.toItemStack());
    }
    public void setItem(int i, ItemStack itemStack){
        inv.setItem(i - 1, itemStack);
    }
    @Override
    public Inventory getInventory() {
        return inv;
    }

    public abstract void setContents(Player p);

    public void show(Player p) {
        if (inv != null) setContents(p);
        p.openInventory(this.inv);
    }

    public abstract void handleClickAction(InventoryClickEvent e);
}
