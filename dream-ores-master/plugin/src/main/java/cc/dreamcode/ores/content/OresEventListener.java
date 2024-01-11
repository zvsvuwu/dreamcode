package cc.dreamcode.ores.content;

import cc.dreamcode.ores.config.PluginConfig;
import cc.dreamcode.ores.utilities.InventoryUtils;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class OresEventListener implements Listener {
    private @Inject PluginConfig pluginConfig;
    private @Inject InventoryUtils inventoryUtils;

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        pluginConfig.oresList.forEach((material, material2) -> {
            if (e.getBlock().getType() != material.parseMaterial()) return;
            e.setCancelled(true);
            assert material2.parseMaterial() != null;
            inventoryUtils.giveItems(e.getPlayer(), new ItemStack(material2.parseMaterial()));
            e.getPlayer().giveExp(e.getExpToDrop());
            e.getBlock().setType(Material.AIR);
            e.getPlayer().getInventory().getItemInHand().setDurability((short) (e.getPlayer().getItemInHand().getDurability() + 1));
        });
    }
}
