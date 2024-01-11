package cc.dreamcode.report.menus;

import cc.dreamcode.menu.bukkit.BukkitMenuProvider;
import cc.dreamcode.menu.bukkit.base.BukkitMenu;
import cc.dreamcode.menu.bukkit.base.BukkitMenuPaginated;
import cc.dreamcode.menu.bukkit.setup.BukkitMenuPaginatedPlayerSetup;
import cc.dreamcode.menu.serdes.bukkit.helper.ItemHelper;
import cc.dreamcode.report.ReportPlugin;
import cc.dreamcode.report.builder.MapBuilder;
import cc.dreamcode.report.config.MessageConfig;
import cc.dreamcode.report.config.PluginConfig;
import cc.dreamcode.report.config.report.PanelMenuConfig;
import cc.dreamcode.report.exception.PluginRuntimeException;
import cc.dreamcode.report.model.user.User;
import cc.dreamcode.report.notice.NoticeSender;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;

public class ReportPanelMenu implements BukkitMenuPaginatedPlayerSetup, NoticeSender {

    private @Inject ReportPlugin reportPlugin;
    private @Inject PluginConfig pluginConfig;
    private @Inject MessageConfig messageConfig;
    private @Inject BukkitMenuProvider bukkitMenuProvider;

    @Setter private User userTarget;

    @Override
    public BukkitMenuPaginated build(@NonNull HumanEntity humanEntity) {
        if (this.userTarget == null) {
            throw new PluginRuntimeException("UserTarget is null.");
        }

        final Player player = (Player) humanEntity;

        final PanelMenuConfig panelMenuConfig = this.pluginConfig.panelMenuConfig;
        final BukkitMenu bukkitMenu = panelMenuConfig.panelMenu.buildWithItem(new MapBuilder<String, String>()
                .put("{nick}", this.userTarget.getName())
                .build());

        bukkitMenu.getHolder().setActionOnSlot(panelMenuConfig.cancelSlot, e ->
                e.getWhoClicked().closeInventory());

        final BukkitMenuPaginated bukkitMenuPaginated = this.bukkitMenuProvider.createPaginated(bukkitMenu);

        bukkitMenuPaginated.setPreviousPageSlot(panelMenuConfig.previousPageSlot, sender ->
                this.send(this.messageConfig.firstPageReach, sender));

        bukkitMenuPaginated.setNextPageSlot(panelMenuConfig.nextPageSlot, sender ->
                this.send(this.messageConfig.lastPageReach, sender));

        this.userTarget.getReportMap().forEach((id, report) ->
                bukkitMenuPaginated.addStorageItem(new ItemHelper(panelMenuConfig.item).fixColors(new MapBuilder<String, String>()
                        .put("{id}", String.valueOf(report.getId()))
                        .put("{reporter}", report.getNameReporter())
                        .put("{target}", report.getNameTarget())
                        .put("{reason}", report.getReason())
                        .build()),
                        e -> {
                            e.setCancelled(true);
                            e.getWhoClicked().closeInventory();

                            if (e.getAction().equals(InventoryAction.PICKUP_ALL)) {
                                final Player playerTarget = this.reportPlugin.getServer().getPlayer(this.userTarget.getUniqueId());
                                if (playerTarget == null) {
                                    this.send(this.messageConfig.noPlayer, player);
                                    return;
                                }

                                player.teleport(playerTarget);
                                return;
                            }
                            if (e.getAction().equals(InventoryAction.PICKUP_HALF)) {
                                this.userTarget.getReportMap().remove(report.getId());
                                this.userTarget.saveAsync();

                                this.send(this.messageConfig.reportRemoved, player, new MapBuilder<String, Object>()
                                        .put("nick", this.userTarget.getName())
                                        .put("id", report.getId())
                                        .build());
                            }
                        }));

        return bukkitMenuPaginated;
    }
}
