package cc.dreamcode.ffa.deposit;

import cc.dreamcode.ffa.config.PluginConfig;
import cc.dreamcode.platform.bukkit.component.scheduler.Scheduler;
import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * UserItemsDepositTask.java
 * Purpose: The UserItemsDepositTask is a class that takes excess items from config.
 * * @author trueman96
 * @version 1.0-beta.1
 * @since 2023-11-25
 */
@RequiredArgsConstructor(onConstructor_= @Inject)
@Scheduler(delay = 0L, interval = 60L)
public class DepositItemTask implements Runnable {

    private final PluginConfig pluginConfig;

    @Override
    public void run() {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            for (final DepositItem depositItem : this.pluginConfig.depositItems) {
                final ItemStack item = depositItem.getItem();
                final int itemToRemove = countItemsIgnoreItemMeta(player, item);
                final int depositLimit = depositItem.getLimit();
                if (itemToRemove > depositLimit) {
                    final int amountToRemove = itemToRemove - depositLimit;
                    ItemStack itemStack = new ItemStack(item);
                    itemStack.setAmount(amountToRemove);
                    removeItemIgnoreItemMeta(player, itemStack);

                    depositItem.getNotice()
                            .send(player, new MapBuilder<String, Object>()
                                    .put("amount", amountToRemove)
                                    .build());
                }
            }
        }
    }

    public static void removeItemIgnoreItemMeta(final Player player, final ItemStack item) {
        if (item == null || item.getType() == Material.AIR) {
            return;
        }

        int amountLeft = item.getAmount();
        final ItemStack[] contents = player.getInventory().getContents();
        for (int i = 0; i < contents.length; i++) {
            final ItemStack itemStack = contents[i];
            if (itemStack == null || itemStack.getType() != item.getType() || itemStack.getDurability() != item.getDurability()) {
                continue;
            }

            if (amountLeft >= itemStack.getAmount()) {
                amountLeft -= itemStack.getAmount();

                player.getInventory().setItem(i, new ItemStack(Material.AIR));
            } else {
                itemStack.setAmount(itemStack.getAmount() - amountLeft);
            }

            if (amountLeft == 0) {
                return;
            }
        }
    }

    public static int countItemsIgnoreItemMeta(final Player player, final ItemStack item) {
        if (item == null || item.getType() == Material.AIR) {
            return 0;
        }

        final PlayerInventory inventory = player.getInventory();
        int count = 0;
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);

            if (itemStack == null || item.getType() != itemStack.getType() || itemStack.getDurability() != item.getDurability()) {
                continue;
            }

            count += itemStack.getAmount();
        }

        return count;
    }
}
