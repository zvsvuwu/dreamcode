package cc.dreamcode.pickaxe.region.controller;

import cc.dreamcode.pickaxe.config.MessageConfig;
import cc.dreamcode.pickaxe.config.PluginConfig;
import cc.dreamcode.pickaxe.region.Region;
import cc.dreamcode.pickaxe.user.User;
import cc.dreamcode.pickaxe.user.UserRepository;
import cc.dreamcode.utilities.bukkit.ChatUtil;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class RegionWandController implements Listener {
    private final UserRepository userRepository;
    private final PluginConfig pluginConfig;
    private final MessageConfig messageConfig;

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEvent(PlayerInteractEvent event) {
        Player source = event.getPlayer();
        ItemStack itemInHand = event.getItem();

        if (pluginConfig.regionWand.getItemMeta() == null) {
            this.messageConfig.regionWandDoesNotHaveItemMeta.send(source);
            return;
        }

        if (itemInHand == null || itemInHand.getItemMeta() == null || event.getClickedBlock() == null) {
            return;
        }

        if (!itemInHand.getItemMeta().getDisplayName().equals(ChatUtil.fixColor(pluginConfig.regionWand.getItemMeta().getDisplayName()))) {
            return;
        }

        Location corner = event.getClickedBlock().getLocation();
        for (Region region : pluginConfig.regions) {
            if (region.isIn(corner)) {
                this.messageConfig.inOtherRegion.send(source);
                return;
            }
        }

        event.setCancelled(true);

        User user = this.userRepository.findOrCreateByHumanEntity(source);
        switch (event.getAction()) {
            case LEFT_CLICK_BLOCK: {
                user.setFirstCorner(corner);
                user.save();

                this.messageConfig.setSecondCorner.send(source);
                break;
            }
            case RIGHT_CLICK_BLOCK: {
                user.setSecondCorner(corner);
                user.save();

                this.messageConfig.setFirstCorner.send(source);
                break;
            }
        }
    }
}
