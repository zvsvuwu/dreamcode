package cc.dreamcode.fortune.controller;

import cc.dreamcode.fortune.config.PluginConfig;
import cc.dreamcode.fortune.fortune.FortuneManager;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

public class BlockBreakController implements Listener {

    @Inject
    private FortuneManager fortuneManager;

    @Inject
    private PluginConfig pluginConfig;

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Material material = block.getType();
        Collection<ItemStack> drops = block.getDrops();
        int requiredLevel = fortuneManager.getMaterialFortune(material);

        if (requiredLevel > 0 && event.getPlayer().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
            int playerLevel = event.getPlayer().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

            if (playerLevel >= requiredLevel) {
                for (ItemStack drop : drops) {
                    for (int i = 0; i < getAdditionalDrops(playerLevel); i++) {
                        int dropAmount = getRandomDropAmount(requiredLevel);
                        ItemStack additionalDrop = new ItemStack(drop.getType(), dropAmount);
                        block.getWorld().dropItemNaturally(block.getLocation(), additionalDrop);
                    }
                }
            }
        }
    }

    private int getAdditionalDrops(int playerLevel) {
        int maxDrops = pluginConfig.levelMaxDrops.getOrDefault(playerLevel, 0);
        return ThreadLocalRandom.current().nextInt(maxDrops);
    }

    private int getRandomDropAmount(int requiredLevel) {
        int maxDrops = pluginConfig.levelMaxDrops.getOrDefault(requiredLevel, 0);
        return ThreadLocalRandom.current().nextInt(maxDrops);
    }
}
