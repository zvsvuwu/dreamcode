package cc.dreamcode.leaderboard.menus;

import cc.dreamcode.leaderboard.LeaderboardService;
import cc.dreamcode.leaderboard.LeaderboardType;
import cc.dreamcode.leaderboard.builder.MapBuilder;
import cc.dreamcode.leaderboard.config.MessageConfig;
import cc.dreamcode.leaderboard.config.PluginConfig;
import cc.dreamcode.leaderboard.config.leaderboard.LeaderboardConfig;
import cc.dreamcode.leaderboard.exception.PluginRuntimeException;
import cc.dreamcode.leaderboard.notice.NoticeSender;
import cc.dreamcode.leaderboard.utilities.TimeUtil;
import cc.dreamcode.menu.bukkit.BukkitMenuProvider;
import cc.dreamcode.menu.bukkit.base.BukkitMenu;
import cc.dreamcode.menu.bukkit.base.BukkitMenuPaginated;
import cc.dreamcode.menu.bukkit.setup.BukkitMenuPaginatedSetup;
import cc.dreamcode.menu.serdes.bukkit.BukkitMenuBuilder;
import cc.dreamcode.menu.serdes.bukkit.helper.ItemHelper;
import eu.okaeri.injector.annotation.Inject;
import lombok.Setter;

import java.text.DecimalFormat;

public class LeaderboardTopMenu implements BukkitMenuPaginatedSetup, NoticeSender {

    private @Inject PluginConfig pluginConfig;
    private @Inject MessageConfig messageConfig;
    private @Inject LeaderboardService leaderboardService;
    private @Inject BukkitMenuProvider bukkitMenuProvider;

    @Setter private LeaderboardType leaderboardType;

    @Override
    public BukkitMenuPaginated build() {
        if (this.leaderboardType == null) {
            throw new PluginRuntimeException("Leaderboard type must be provided.");
        }

        final LeaderboardConfig leaderboardConfig = this.pluginConfig.leaderboardConfig;
        final String displayName = leaderboardConfig.topDisplayNames.get(leaderboardType);
        if (displayName == null) {
            throw new PluginRuntimeException("DisplayName for " + this.leaderboardType.getId() + " type not found.");
        }

        final BukkitMenuBuilder bukkitMenuBuilder = leaderboardConfig.leaderboardMenu;
        final BukkitMenu bukkitMenu = bukkitMenuBuilder.buildWithItem(new MapBuilder<String, String>()
                .put("{name}", displayName)
                .build());

        final BukkitMenuPaginated bukkitMenuPaginated = this.bukkitMenuProvider.createPaginated(bukkitMenu);
        bukkitMenuPaginated.setPreviousPageSlot(leaderboardConfig.previousPageSlot, sender ->
                this.send(this.messageConfig.firstPageReach, sender));

        bukkitMenuPaginated.setNextPageSlot(leaderboardConfig.nextPageSlot, sender ->
                this.send(this.messageConfig.lastPageReach, sender));

        this.leaderboardService.getLeaderboardMap().get(this.leaderboardType).forEach((position, top) -> {
            final String value;
            if (this.leaderboardType.equals(LeaderboardType.PLAY_TIME_COUNT)) {
                value = TimeUtil.convertSeconds(top.getCount());
            }
            else if (this.leaderboardType.equals(LeaderboardType.WALK_DISTANCE_COUNT)) {
                double kilometers = top.getCount() * 0.001;
                value = new DecimalFormat("0.00").format(kilometers) + "km";
            }
            else {
                value = String.valueOf(top.getCount());
            }

            bukkitMenuPaginated.addStorageItem(new ItemHelper(leaderboardConfig.topItem).fixColors(new MapBuilder<String, String>()
                    .put("{place}", String.valueOf(position))
                    .put("{nick}", top.getUsername())
                    .put("{value}", value)
                    .put("{display_name}", displayName)
                    .build()));
        });

        return bukkitMenuPaginated;
    }
}
