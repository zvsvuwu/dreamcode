package cc.dreamcode.anticobweb.controller;

import cc.dreamcode.anticobweb.AntiCobwebPlugin;
import cc.dreamcode.anticobweb.config.MessagesConfig;
import cc.dreamcode.anticobweb.config.PluginConfig;
import cc.dreamcode.anticobweb.manager.CooldownManager;
import cc.dreamcode.utilities.builder.MapBuilder;
import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;
import java.util.UUID;

public class AntiCobwebController implements Listener {

    private @Inject AntiCobwebPlugin antiCobwebPlugin;
    private @Inject PluginConfig pluginConfig;
    private @Inject MessagesConfig messagesConfig;
    private @Inject CooldownManager cooldownManager;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack originalItemStack = event.getItem();
        Action action = event.getAction();

        if (!(action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK))) {
            return;
        }

        if (originalItemStack == null) {
            return;
        }

        ItemStack itemStack = originalItemStack.clone();
        itemStack.setAmount(1);

        if(!itemStack.equals(this.pluginConfig.item)) {
            return;
        }

        UUID uuid = player.getUniqueId();
        if (this.cooldownManager.isOnCooldown(uuid)) {
            Duration cooldown = this.cooldownManager.getCooldown(uuid);
            this.messagesConfig.cooldownMessage.send(
                    player,
                    new MapBuilder<String, Object>()
                            .put("cooldown", cooldown.getSeconds())
                            .build()
            );
            return;
        }
        this.cooldownManager.resetCooldown(uuid);
        if (this.pluginConfig.removeOnUse) {
            originalItemStack.setAmount(originalItemStack.getAmount() - 1);
        }

        World world = player.getWorld();
        Location location = player.getLocation();
        int radius = this.pluginConfig.radius;
        for (int x = -radius; x < radius; x++) {
            for (int y = -radius; y < radius; y++) {
                for (int z = -radius; z < radius; z++) {
                    Block block = world.getBlockAt(location.getBlockX() + x, location.getBlockY() + y, location.getBlockZ() + z);
                    XMaterial xMaterial = XMaterial.matchXMaterial(block.getType());
                    if (xMaterial.equals(XMaterial.COBWEB)) {
                        block.setType(XMaterial.AIR.parseMaterial());
                    }
                }
            }
        }

        if (this.pluginConfig.showUseMessage) {
            this.messagesConfig.useMessage.send(player);
        }
    }
}
