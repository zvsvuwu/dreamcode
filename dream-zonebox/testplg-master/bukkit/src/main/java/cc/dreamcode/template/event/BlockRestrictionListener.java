package cc.dreamcode.template.event;

import cc.dreamcode.template.config.MessageConfig;
import cc.dreamcode.template.config.PluginConfig;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;


@RequiredArgsConstructor
public class BlockRestrictionListener implements Listener {

    private @Inject MessageConfig messageConfig;
    private @Inject PluginConfig pluginConfig;
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Material blockType = event.getBlock().getType();
        if (!this.pluginConfig.allowedBlocks.contains(blockType)) {
            if(canBypassAll(player) || canBypassPlace(player))  return;
            event.setCancelled(true);
            if(this.pluginConfig.shouldSendMessages) {
                this.messageConfig.cantPlaceBlock.send(player);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Material blockType = event.getBlock().getType();
        if (!this.pluginConfig.allowedBlocks.contains(blockType)) {
            if(canBypassAll(player) || canBypassBreak(player)) return;
            event.setCancelled(true);
            if(this.pluginConfig.shouldSendMessages) {
                this.messageConfig.cantBreakblock.send(player);
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