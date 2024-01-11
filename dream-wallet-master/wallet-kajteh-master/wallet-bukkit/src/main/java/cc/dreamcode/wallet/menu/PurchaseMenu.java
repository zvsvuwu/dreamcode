package cc.dreamcode.wallet.menu;

import cc.dreamcode.menu.bukkit.BukkitMenuBuilder;
import cc.dreamcode.menu.bukkit.base.BukkitMenu;
import cc.dreamcode.menu.bukkit.setup.BukkitMenuPlayerSetup;
import cc.dreamcode.utilities.StringUtil;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import cc.dreamcode.wallet.WalletPlugin;
import cc.dreamcode.wallet.config.MessageConfig;
import cc.dreamcode.wallet.config.PluginConfig;
import cc.dreamcode.wallet.product.Product;
import cc.dreamcode.wallet.product.ProductBuy;
import cc.dreamcode.wallet.shared.MoneyUtil;
import cc.dreamcode.wallet.user.User;
import cc.dreamcode.wallet.user.UserManager;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.HumanEntity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class PurchaseMenu implements BukkitMenuPlayerSetup {

    private final WalletPlugin walletPlugin;
    private final PluginConfig pluginConfig;
    private final MessageConfig messageConfig;
    private final UserManager userManager;
    private final WalletMenu walletMenu;
    private final Tasker tasker;

    @Setter private Product product;

    @Override
    public BukkitMenu build(@NonNull HumanEntity humanEntity) {
        final BukkitMenuBuilder menuBuilder = this.pluginConfig.purchaseMenuBuilder;
        final BukkitMenu bukkitMenu = menuBuilder.buildEmpty(new MapBuilder<String, Object>()
                .put("name", StringUtil.replace(this.product.getName(), new MapBuilder<String, Object>()
                        .put("nick", humanEntity.getName())
                        .build()))
                .put("cost", MoneyUtil.formatMoney(this.pluginConfig.moneySuffix, this.product.getCost()))
                .build());

        bukkitMenu.setDisposeWhenClose(true);

        menuBuilder.getItems().forEach((slot, item) -> {
            if (this.pluginConfig.purchaseProductSlot.contains(slot)) {
                bukkitMenu.setItem(slot, ItemBuilder.of(item)
                        .fixColors(new MapBuilder<String, Object>()
                                .put("name", StringUtil.replace(this.product.getName(), new MapBuilder<String, Object>()
                                        .put("nick", humanEntity.getName())
                                        .build()))
                                .put("cost", MoneyUtil.formatMoney(this.pluginConfig.moneySuffix, this.product.getCost()))
                                .build())
                        .toItemStack(), e -> {
                            e.getWhoClicked().closeInventory();

                            final User user = this.userManager.getUserByPlayer(humanEntity);
                            if (user.getMoney() < this.product.getCost()) {
                                this.messageConfig.cannotAffordThat.send(e.getWhoClicked(), new MapBuilder<String, Object>()
                                        .put("name", StringUtil.replace(this.product.getName(), new MapBuilder<String, Object>()
                                                        .put("nick", e.getWhoClicked().getName())
                                                        .build()))
                                        .put("cost", MoneyUtil.formatMoney(this.pluginConfig.moneySuffix, this.product.getCost()))
                                        .build());
                                return;
                            }

                            this.tasker.newSharedChain("dbops:" + e.getWhoClicked().getUniqueId())
                                    .async(() -> {
                                        user.takeMoney(this.product.getCost());
                                        user.addMoneySpent(this.product.getCost());
                                        user.getProductBuyList().add(new ProductBuy(this.product.getId(), new Date().toInstant()));
                                        user.save();
                                    })
                                    .abortIfException()
                                    .sync(() -> {
                                        if (this.product.getItems() != null) {
                                            this.product.getItems().forEach(itemStack ->
                                                    e.getWhoClicked().getInventory().addItem(ItemBuilder.of(itemStack).fixColors().toItemStack())
                                                            .forEach((i, itemLost) -> e.getWhoClicked().getWorld().dropItem(e.getWhoClicked().getLocation(), itemLost)));
                                        }

                                        this.product.getCommands().forEach(command ->
                                                this.walletPlugin.getServer().dispatchCommand(
                                                        this.walletPlugin.getServer().getConsoleSender(),
                                                        StringUtil.replace(command, new MapBuilder<String, Object>()
                                                                .put("nick", e.getWhoClicked().getName())
                                                                .build())
                                                ));

                                        this.messageConfig.productPurchased.send(e.getWhoClicked(), new MapBuilder<String, Object>()
                                                .put("nick", e.getWhoClicked().getName())
                                                .put("name", StringUtil.replace(this.product.getName(), new MapBuilder<String, Object>()
                                                        .put("nick", e.getWhoClicked().getName())
                                                        .build()))
                                                .put("cost", MoneyUtil.formatMoney(this.pluginConfig.moneySuffix, this.product.getCost()))
                                                .put("money", MoneyUtil.formatMoney(this.pluginConfig.moneySuffix, user.getMoney()))
                                                .build());

                                        this.messageConfig.productPurchasedBroadcast.send(e.getWhoClicked(), new MapBuilder<String, Object>()
                                                .put("nick", e.getWhoClicked().getName())
                                                .put("name", StringUtil.replace(this.product.getName(), new MapBuilder<String, Object>()
                                                        .put("nick", e.getWhoClicked().getName())
                                                        .build()))
                                                .put("cost", MoneyUtil.formatMoney(this.pluginConfig.moneySuffix, this.product.getCost()))
                                                .put("money", MoneyUtil.formatMoney(this.pluginConfig.moneySuffix, user.getMoney()))
                                                .build());
                                    })
                                    .execute();
                        });
                return;
            }

            if (this.pluginConfig.declineOfferSlot.contains(slot)) {
                bukkitMenu.setItem(slot, ItemBuilder.of(item)
                        .fixColors(new MapBuilder<String, Object>()
                                .put("name", StringUtil.replace(this.product.getName(), new MapBuilder<String, Object>()
                                        .put("nick", humanEntity.getName())
                                        .build()))
                                .put("cost", MoneyUtil.formatMoney(this.pluginConfig.moneySuffix, this.product.getCost()))
                                .build())
                        .toItemStack(), e -> this.walletMenu.build(e.getWhoClicked()).open(e.getWhoClicked()));
                return;
            }

            bukkitMenu.setItem(slot, ItemBuilder.of(item)
                    .fixColors(new MapBuilder<String, Object>()
                            .put("name", StringUtil.replace(this.product.getName(), new MapBuilder<String, Object>()
                                    .put("nick", humanEntity.getName())
                                    .build()))
                            .put("cost", MoneyUtil.formatMoney(this.pluginConfig.moneySuffix, this.product.getCost()))
                            .build())
                    .toItemStack());
        });

        return bukkitMenu;
    }
}
