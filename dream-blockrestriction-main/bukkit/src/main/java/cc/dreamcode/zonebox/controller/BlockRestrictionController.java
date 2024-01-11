package cc.dreamcode.zonebox.controller;

import cc.dreamcode.zonebox.config.MessageConfig;
import cc.dreamcode.zonebox.config.PluginConfig;
import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

@RequiredArgsConstructor
public class BlockRestrictionController implements Listener {

    private @Inject MessageConfig messageConfig;
    private @Inject PluginConfig pluginConfig;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        XMaterial blockType = XMaterial.matchXMaterial(event.getBlock().getType());
        if (!this.pluginConfig.allowedBlocks.contains(blockType)) {
            if (canBypassAll(player) || canBypassPlace(player)) {
                return;
            }

            event.setCancelled(true);
            if (this.pluginConfig.shouldSendMessages) {
                this.messageConfig.cantPlaceBlock.send(player);
            }
        }


    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        XMaterial blockType = XMaterial.matchXMaterial(event.getBlock().getType());
        if (!this.pluginConfig.allowedBlocks.contains(blockType)) {
            if (canBypassAll(player) || canBypassBreak(player)) {
                return;
            }

            event.setCancelled(true);
            if (this.pluginConfig.shouldSendMessages) {
                this.messageConfig.cantBreakBlock.send(player);
            }
        }
    }


    public boolean canBypassAll(Player player) {
        return player.hasPermission(this.pluginConfig.allowAll);
    }

    public boolean canBypassBreak(Player player) {
        return player.hasPermission(this.pluginConfig.allowBreak);
    }

    public boolean canBypassPlace(Player player) {
        return player.hasPermission((this.pluginConfig.allowPlace));
    }

}