package cc.dreamcode.dropsmp.features.user;

import cc.dreamcode.dropsmp.config.MessageConfig;
import cc.dreamcode.dropsmp.config.PluginConfig;
import cc.dreamcode.dropsmp.config.subconfig.DropSmpConfig;
import cc.dreamcode.dropsmp.features.notice.NoticeService;
import cc.dreamcode.dropsmp.features.user.persistence.User;
import cc.dreamcode.dropsmp.features.user.persistence.UserRepository;
import cc.dreamcode.dropsmp.nms.api.NmsAccessor;
import cc.dreamcode.dropsmp.nms.api.inventory.InventoryAccessor;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class UserEmblemUseEvent implements Listener, NoticeService {

    private @Inject PluginConfig pluginConfig;
    private @Inject UserRepository userRepository;
    private @Inject MessageConfig messageConfig;

    private @Inject NmsAccessor nmsAccessor;

    @EventHandler
    public void emblemUse(PlayerInteractEvent e) {
        final Player player = e.getPlayer();
        final DropSmpConfig dropSmpConfig = pluginConfig.dropSmpConfig;
        final InventoryAccessor inventoryAccessor = this.nmsAccessor.getInventoryAccessor();
        final ItemStack item = new ItemStack(inventoryAccessor.getItemInMainHand(player));
        final User user = userRepository.get(player, false);
        dropSmpConfig.emblemList.forEach(emblem -> {
            final ItemStack em = emblem.getEmblem();
            final double add = emblem.getAdd();
            if (item.isSimilar(em)) {
                player.getInventory().removeItem(item);
                switch (emblem.getEmblemType()) {
                    case SPEED: {
                        user.setSpeed(user.getSpeed() + add);
                        return;
                    }
                    case STRENGTH: {
                        user.setStrength(user.getStrength() + add);
                        return;
                    }
                    case RESISTANCE: {
                        user.setResistance(user.getResistance() + add);
                    }
                    case RESTART: {
                        user.setResistance(emblem.getAdd());
                        user.setSpeed(emblem.getAdd());
                        user.setStrength(emblem.getAdd());
                    }
                }
                this.send(messageConfig.correctlyUseEmblem, player);
            }
        });
    }
}
