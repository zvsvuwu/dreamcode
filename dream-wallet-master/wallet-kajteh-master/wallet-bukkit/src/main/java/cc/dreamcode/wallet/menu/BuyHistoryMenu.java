package cc.dreamcode.wallet.menu;

import cc.dreamcode.menu.bukkit.BukkitMenuBuilder;
import cc.dreamcode.menu.bukkit.BukkitMenuProvider;
import cc.dreamcode.menu.bukkit.base.BukkitMenu;
import cc.dreamcode.menu.bukkit.base.BukkitMenuPaginated;
import cc.dreamcode.menu.bukkit.setup.BukkitMenuPaginatedSetup;
import cc.dreamcode.notice.minecraft.MinecraftNoticeType;
import cc.dreamcode.notice.minecraft.bukkit.BukkitNotice;
import cc.dreamcode.platform.bukkit.exception.BukkitPluginException;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import cc.dreamcode.wallet.product.ProductBuy;
import cc.dreamcode.wallet.user.User;
import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.injector.annotation.Inject;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

@Getter
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class BuyHistoryMenu implements BukkitMenuPaginatedSetup {

    @Setter private User user;
    private final BukkitMenuProvider bukkitMenuProvider;

    @Override
    public BukkitMenuPaginated build() {
        if(this.user == null) {
            throw new BukkitPluginException("User is null! Cannot build menu.");
        }

        if(this.user.getProductBuyList().isEmpty()) {
            return null;
        }

        final BukkitMenuBuilder bukkitMenuBuilder = new BukkitMenuBuilder(
                "Historia zakupów: {player}",
                5,
                new MapBuilder<Integer, ItemStack>()
                        .put(new Integer[]{0, 8, 36, 44}, new ItemBuilder(XMaterial.BLUE_STAINED_GLASS_PANE.parseMaterial()).setName(" ").toItemStack())
                        .put(new Integer[]{1, 7, 9, 17, 27, 35, 37, 43}, new ItemBuilder(XMaterial.LIGHT_BLUE_STAINED_GLASS_PANE.parseMaterial()).setName(" ").toItemStack())
                        .put(new Integer[]{2, 3, 5, 6, 18, 26, 38, 42}, new ItemBuilder(XMaterial.WHITE_STAINED_GLASS_PANE.parseMaterial()).setName(" ").toItemStack())
                        .put(new Integer[]{4, 40}, new ItemBuilder(XMaterial.LIGHT_GRAY_STAINED_GLASS_PANE.parseMaterial()).setName(" ").toItemStack())
                        .put(41, new ItemBuilder(XMaterial.LIME_DYE.parseMaterial())
                                .setName("&aNastępna strona")
                                .toItemStack())
                        .put(39, new ItemBuilder(XMaterial.RED_DYE.parseMaterial())
                                .setName("&cPoprzednia strona")
                                .toItemStack())
                        .build()
        );
        final BukkitMenu bukkitMenu = bukkitMenuBuilder.buildEmpty(new MapBuilder<String, Object>()
                .put("player", this.user.getName())
                .build());

        bukkitMenuBuilder.getItems().forEach((integer, itemStack) -> bukkitMenu.setItem(integer, ItemBuilder.of(itemStack).fixColors().toItemStack()));

        final BukkitMenuPaginated bukkitMenuPaginated = this.bukkitMenuProvider.createPaginated(bukkitMenu);

        bukkitMenuPaginated.setNextPageSlot(41, humanEntity ->
                new BukkitNotice(MinecraftNoticeType.CHAT, "&cNie ma następnej strony!").send(humanEntity));

        bukkitMenuPaginated.setPreviousPageSlot(39, humanEntity ->
                new BukkitNotice(MinecraftNoticeType.CHAT, "&cJesteś na pierwszej stronie!").send(humanEntity));

        this.user.getProductBuyList().forEach(productBuy -> bukkitMenuPaginated.addStorageItem(this.buildProductItem(productBuy)));

        return bukkitMenuPaginated;
    }

    private ItemStack buildProductItem(@NonNull ProductBuy productBuy) {
        return new ItemBuilder(XMaterial.KNOWLEDGE_BOOK.parseMaterial())
                .setName("&dZakup produktu")
                .setLore(
                        "&8» &7Produkt: &f{id}",
                        "&8» &7Data zakupu: &f{date}"
                )
                .fixColors(new MapBuilder<String, Object>()
                        .put("id", productBuy.getId())
                        .put("date", productBuy.getFormattedDate())
                        .build())
                .toItemStack();
    }
}
