package cc.dreamcode.shopforkills.menu;

import cc.dreamcode.menu.bukkit.base.BukkitMenu;
import cc.dreamcode.menu.core.setup.MenuPlayerSetup;
import cc.dreamcode.shopforkills.config.MessageConfig;
import cc.dreamcode.shopforkills.config.PluginConfig;
import cc.dreamcode.shopforkills.user.User;
import cc.dreamcode.shopforkills.user.UserManager;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.ItemUtil;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import lombok.NonNull;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.Optional;

public class ShopMenu implements MenuPlayerSetup<BukkitMenu, Player> {

    private @Inject PluginConfig pluginConfig;
    private @Inject UserManager userManager;
    private @Inject MessageConfig messageConfig;
    private @Inject Tasker tasker;

    @Override
    public BukkitMenu build(@NonNull Player player) {
        Optional<User> optionalUser = userManager.getUser(player.getUniqueId());
        if (!optionalUser.isPresent()) {
            throw new NullPointerException("User null!");
        }

        final User user = optionalUser.get();
        final BukkitMenu bukkitMenu = pluginConfig.shopMenu.buildWithItems(new MapBuilder<String, Object>()
                .put("kills", user.getKills())
                .build());

        bukkitMenu.setCancelInventoryClick(true);
        bukkitMenu.setDisposeWhenClose(true);

        this.pluginConfig.offers.forEach(offer -> {
            ItemBuilder itemBuilder = new ItemBuilder(offer.getItem())
                    .setLore(this.pluginConfig.costLore)
                    .fixColors(new MapBuilder<String, Object>()
                            .put("kills", offer.getKills())
                            .build());

            bukkitMenu.setItem(offer.getSlot(), itemBuilder.toItemStack(), event -> {
                if (user.getKills() < offer.getKills()) {
                    this.messageConfig.notHaveKills.send(player);
                    return;
                }

                ItemUtil.giveItem(player, ItemBuilder.of(offer.getItem()).fixColors().toItemStack());
                this.messageConfig.buyItem.send(player);

                this.tasker.newSharedChain("dbops:" + player.getUniqueId())
                        .async(() -> {
                            user.setKills(user.getKills() + offer.getKills());
                            user.save();
                        })
                        .execute();

                this.tasker.newDelayer(Duration.ZERO)
                        .delayed(() -> build(player).open(player))
                        .executeSync();
            });
        });

        return bukkitMenu;
    }
}
