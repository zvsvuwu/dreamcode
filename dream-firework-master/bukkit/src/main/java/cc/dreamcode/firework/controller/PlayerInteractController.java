package cc.dreamcode.firework.controller;

import cc.dreamcode.firework.config.PluginConfig;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractController implements Listener {

    private @Inject PluginConfig pluginConfig;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        final ItemStack item = e.getItem();
        if (item == null || !item.isSimilar(ItemBuilder.of(this.pluginConfig.fireworkItem)
                .fixColors()
                .toItemStack())) {
            return;
        }

        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            e.setUseItemInHand(Event.Result.DENY);
        }
    }
}
