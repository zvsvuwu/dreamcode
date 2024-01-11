package cc.dreamcode.anvil.controller;

import cc.dreamcode.anvil.config.MessageConfig;
import cc.dreamcode.anvil.config.PluginConfig;
import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class AnvilClickController implements Listener {

    public @Inject PluginConfig pluginConfig;
    public @Inject MessageConfig messageConfig;

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockClick(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        Block clickedBlock = e.getClickedBlock();
        if (clickedBlock == null || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            return;
        }
        if (clickedBlock.getType() == Material.ANVIL) {
            e.setCancelled(true);


            ItemStack itemInHand = player.getItemInHand();


            if (itemInHand == null || itemInHand.getType() == Material.AIR) {
                this.messageConfig.mustHoldTool.send(player);
                return;
            }

            if (itemInHand.getDurability() == 0) {
                this.messageConfig.itemCantBeRepaired.send(player);
                return;
            }

            if (player.getInventory().containsAtLeast(new ItemStack(this.pluginConfig.blockForRepair.parseMaterial()), this.pluginConfig.repairCost)) {
                itemInHand.setDurability((short) 0);
                player.getInventory().removeItem(new ItemStack(this.pluginConfig.blockForRepair.parseMaterial(), this.pluginConfig.repairCost));
                player.setItemInHand(itemInHand);
                this.messageConfig.successfullyRepaired.send(player);
                return;
            }
            this.messageConfig.dontHaveEnoughItem.send(player, new MapBuilder<String, Object>()
                    .put("block", this.pluginConfig.blockForRepair + " x" + this.pluginConfig.repairCost)
                    .build());
        }
    }
}
