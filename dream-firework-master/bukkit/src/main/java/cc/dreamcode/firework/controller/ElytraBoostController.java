package cc.dreamcode.firework.controller;

import cc.dreamcode.firework.config.PluginConfig;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import com.destroystokyo.paper.event.player.PlayerElytraBoostEvent;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class ElytraBoostController implements Listener {

    private @Inject PluginConfig pluginConfig;

    @EventHandler
    public void onElytraBoost(PlayerElytraBoostEvent e) {
        final Player player = e.getPlayer();
        final ItemStack item = e.getItemStack();
        final ItemStack firework = ItemBuilder.of(this.pluginConfig.fireworkItem)
                .fixColors()
                .toItemStack();

        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        if (!item.isSimilar(firework)) {
            return;
        }

        e.setShouldConsume(false);
    }
}
