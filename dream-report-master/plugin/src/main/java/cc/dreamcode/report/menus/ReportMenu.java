package cc.dreamcode.report.menus;

import cc.dreamcode.menu.bukkit.base.BukkitMenu;
import cc.dreamcode.menu.bukkit.setup.BukkitMenuPlayerSetup;
import cc.dreamcode.report.ReportPlugin;
import cc.dreamcode.report.builder.MapBuilder;
import cc.dreamcode.report.config.MessageConfig;
import cc.dreamcode.report.config.PluginConfig;
import cc.dreamcode.report.config.report.ReportMenuConfig;
import cc.dreamcode.report.exception.PluginRuntimeException;
import cc.dreamcode.report.model.report.Report;
import cc.dreamcode.report.model.user.User;
import cc.dreamcode.report.model.user.UserRepository;
import cc.dreamcode.report.notice.NoticeSender;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.time.Instant;

public class ReportMenu implements BukkitMenuPlayerSetup, NoticeSender {

    private @Inject ReportPlugin reportPlugin;
    private @Inject PluginConfig pluginConfig;
    private @Inject MessageConfig messageConfig;
    private @Inject UserRepository userRepository;

    @Setter private User userTarget;

    @Override
    public BukkitMenu build(@NonNull HumanEntity humanEntity) {
        if (this.userTarget == null) {
            throw new PluginRuntimeException("UserTarget is null.");
        }

        final Player player = (Player) humanEntity;

        final ReportMenuConfig reportMenuConfig = this.pluginConfig.reportMenuConfig;
        final BukkitMenu bukkitMenu = reportMenuConfig.reportMenu.buildWithItem(new MapBuilder<String, String>()
                .put("{nick}", this.userTarget.getName())
                .build());

        reportMenuConfig.reportSlotMap.forEach(((integer, reason) ->
                bukkitMenu.getHolder().setActionOnSlot(integer, e -> {
                    e.getWhoClicked().closeInventory();

                    final Report report = new Report(
                            this.userTarget.getReportMap().size() + 1,
                            player.getName(),
                            this.userTarget.getName(),
                            reason,
                            Instant.now()
                    );

                    this.userTarget.getReportMap().put(report.getId(), report);
                    this.userTarget.saveAsync();

                    this.send(this.messageConfig.reportSend, player, new MapBuilder<String, Object>()
                            .put("nick", report.getNameTarget())
                            .put("id", report.getId())
                            .build());

                    this.reportPlugin.getServer().getOnlinePlayers()
                            .stream()
                            .filter(playerOnline -> playerOnline.hasPermission("rpl.report.admin"))
                            .filter(playerOnline -> {
                                final User userOnline = this.userRepository.findOrCreateByPlayer(playerOnline).join();
                                return userOnline.isReportToggle();
                            })
                            .forEach(playerOnline -> this.send(this.messageConfig.reportAdmin, playerOnline, new MapBuilder<String, Object>()
                                    .put("id", report.getId())
                                    .put("reporter", report.getNameReporter())
                                    .put("target", report.getNameTarget())
                                    .put("reason", report.getReason())
                                    .build()));
                })));

        bukkitMenu.getHolder().setActionOnSlot(reportMenuConfig.cancelSlot, e ->
                e.getWhoClicked().closeInventory());

        return bukkitMenu;
    }
}
