package cc.dreamcode.wallet.menu;

import cc.dreamcode.menu.bukkit.BukkitMenuBuilder;
import cc.dreamcode.menu.bukkit.base.BukkitMenu;
import cc.dreamcode.menu.bukkit.setup.BukkitMenuPlayerSetup;
import cc.dreamcode.utilities.StringUtil;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import cc.dreamcode.wallet.WalletPlugin;
import cc.dreamcode.wallet.config.PluginConfig;
import cc.dreamcode.wallet.shared.MoneyUtil;
import cc.dreamcode.wallet.user.User;
import cc.dreamcode.wallet.user.UserManager;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class WalletMenu implements BukkitMenuPlayerSetup {

    private final WalletPlugin walletPlugin;
    private final PluginConfig pluginConfig;
    private final UserManager userManager;

    @Override
    public BukkitMenu build(@NonNull HumanEntity humanEntity) {
        final BukkitMenuBuilder builder = this.pluginConfig.walletMenuBuilder;
        final BukkitMenu bukkitMenu = builder.buildEmpty(new MapBuilder<String, Object>()
                .put("nick", humanEntity.getName())
                .build());

        final User user = this.userManager.getUserByPlayer(humanEntity);
        builder.getItems().forEach((slot, item) -> {

            if (this.pluginConfig.receiveDailyRewardSlot == slot) {
                bukkitMenu.setItem(slot, new ItemBuilder(item)
                        .fixColors(new MapBuilder<String, Object>()
                                .put("nick", humanEntity.getName())
                                .put("money", MoneyUtil.formatMoney(this.pluginConfig.moneySuffix, user.getMoney()))
                                .build())
                        .toItemStack(),
                        e -> {
                            e.getWhoClicked().closeInventory();

                            if (!(e.getWhoClicked() instanceof Player)) {
                                return;
                            }

                            final Player player = (Player) e.getWhoClicked();
                            player.chat("/odbierznagrode");
                        });
                return;
            }

            bukkitMenu.setItem(slot, new ItemBuilder(item)
                    .fixColors(new MapBuilder<String, Object>()
                            .put("nick", humanEntity.getName())
                            .put("money", MoneyUtil.formatMoney(this.pluginConfig.moneySuffix, user.getMoney()))
                            .build())
                    .toItemStack());
        });

        this.pluginConfig.productList.forEach(product ->
            bukkitMenu.setItem(product.getSlotInMenu(), ItemBuilder.of(product.getDisplayItem())
                    .fixColors(new MapBuilder<String, Object>()
                            .put("nick", humanEntity.getName())
                            .put("name", StringUtil.replace(product.getName(), new MapBuilder<String, Object>()
                                    .put("nick", humanEntity.getName())
                                    .build()))
                            .put("cost", MoneyUtil.formatMoney(this.pluginConfig.moneySuffix, product.getCost()))
                            .put("money", MoneyUtil.formatMoney(this.pluginConfig.moneySuffix, user.getMoney()))
                            .build())
                    .toItemStack(), e -> {
                        final PurchaseMenu purchaseMenu = this.walletPlugin.createInstance(PurchaseMenu.class);

                        purchaseMenu.setProduct(product);
                        purchaseMenu.build(e.getWhoClicked()).open(e.getWhoClicked());
                    }));

        return bukkitMenu;
    }
}
