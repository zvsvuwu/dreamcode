package cc.dreamcode.dropsmp.features.user;

import cc.dreamcode.dropsmp.config.PluginConfig;
import cc.dreamcode.dropsmp.config.subconfig.DropSmpConfig;
import cc.dreamcode.dropsmp.features.randomizer.DoubleRandomizer;
import cc.dreamcode.dropsmp.nms.api.NmsAccessor;
import cc.dreamcode.dropsmp.nms.api.inventory.InventoryAccessor;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class UserCaseOpenEvent implements Listener {
    private @Inject PluginConfig pluginConfig;

    private @Inject NmsAccessor nmsAccessor;

    @EventHandler
    public void caseOpen(BlockPlaceEvent e) {
        final Player player = e.getPlayer();
        final InventoryAccessor inventoryAccessor = this.nmsAccessor.getInventoryAccessor();
        final ItemStack stack = new ItemStack(inventoryAccessor.getItemInMainHand(player));
        final DropSmpConfig dropSmpConfig = pluginConfig.dropSmpConfig;
        if (stack.isSimilar(dropSmpConfig.cases)) {
            e.setCancelled(true);
            player.getInventory().removeItem(stack);
            final Block block = e.getBlock();
            dropSmpConfig.items.forEach(item -> {
                if (item.getChance() <= DoubleRandomizer.range(0D, 100D)) {
                    block.getWorld().dropItemNaturally(block.getLocation(), item.getItemStack());
                }
            });
        }
    }
}
