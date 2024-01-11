package cc.dreamcode.report.command.report;

import cc.dreamcode.report.ReportPlugin;
import cc.dreamcode.report.builder.MapBuilder;
import cc.dreamcode.report.command.CommandHandler;
import cc.dreamcode.report.command.annotations.RequiredPlayer;
import cc.dreamcode.report.config.MessageConfig;
import cc.dreamcode.report.config.PluginConfig;
import cc.dreamcode.report.config.report.ReportConfig;
import cc.dreamcode.report.menus.ReportMenu;
import cc.dreamcode.report.model.report.Report;
import cc.dreamcode.report.model.user.User;
import cc.dreamcode.report.model.user.UserRepository;
import cc.dreamcode.report.utilities.CountUtil;
import cc.dreamcode.report.utilities.StringUtil;
import cc.dreamcode.report.utilities.TimeUtil;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredPlayer
public class ReportCommand extends CommandHandler {

    private @Inject ReportPlugin reportPlugin;
    private @Inject PluginConfig pluginConfig;
    private @Inject MessageConfig messageConfig;
    private @Inject UserRepository userRepository;

    public ReportCommand() {
        super("report", Collections.singletonList("zglos"));
        this.addArgument(PanelArgument.class);
        this.addArgument(ReloadArgument.class);
        this.addArgument(ToggleArgument.class);
        this.addArgument(CloseallArgument.class);
    }

    @Override
    public void handle(@NonNull CommandSender sender, @NonNull String[] args) {
        final Player player = (Player) sender;
        if (args.length == 0) {
            this.send(this.messageConfig.usage, sender, new MapBuilder<String, Object>()
                    .put("usage", "/report [nick]")
                    .build());
            return;
        }

        final String nick = args[0];
        final Player playerReported = this.reportPlugin.getServer().getPlayerExact(nick);
        if (playerReported == null) {
            this.send(this.messageConfig.noPlayer, player);
            return;
        }

        if (playerReported.getName().equals(player.getName())) {
            this.send(this.messageConfig.actionPlayedOnSelf, player);
            return;
        }

        final ReportConfig reportConfig = this.pluginConfig.reportConfig;
        final User user = this.userRepository.findOrCreateByPlayer(player).join();

        final long time = CountUtil.getCountDownSeconds(user.getReportCoolDown(), reportConfig.coolDownDuration);
        if (time > 0 && !player.hasPermission("rpl.report.bypass")) {
            this.send(this.messageConfig.coolDown, player, new MapBuilder<String, Object>()
                    .put("time", TimeUtil.convertSeconds(time))
                    .build());
            return;
        }

        final User userReported = this.userRepository.findOrCreateByPlayer(playerReported).join();
        if (!player.hasPermission("rpl.report.bypass")) {
            user.setReportCoolDown(System.currentTimeMillis());
            user.saveAsync();
        }

        if (args.length == 1) {
            final ReportMenu reportMenu = this.createInstance(ReportMenu.class);
            reportMenu.setUserTarget(userReported);

            reportMenu.build(player).open(player);
            return;
        }

        final String reason = StringUtil.join(args, " ", 1, args.length);
        final Report report = new Report(
                userReported.getReportMap().size() + 1,
                player.getName(),
                userReported.getName(),
                reason,
                Instant.now()
        );

        userReported.getReportMap().put(report.getId(), report);
        userReported.saveAsync();

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
    }

    @Override
    public List<String> tab(@NonNull Player player, @NonNull String[] args) {
        if (args.length == 1) {
            return this.reportPlugin.getServer().getOnlinePlayers()
                    .stream()
                    .map(Player::getName)
                    .collect(Collectors.toList());
        }

        return null;
    }
}
