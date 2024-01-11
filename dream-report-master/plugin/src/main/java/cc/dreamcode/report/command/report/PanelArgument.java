package cc.dreamcode.report.command.report;

import cc.dreamcode.report.ReportPlugin;
import cc.dreamcode.report.builder.MapBuilder;
import cc.dreamcode.report.command.ArgumentHandler;
import cc.dreamcode.report.command.annotations.RequiredPermission;
import cc.dreamcode.report.command.annotations.RequiredPlayer;
import cc.dreamcode.report.config.MessageConfig;
import cc.dreamcode.report.menus.ReportPanelMenu;
import cc.dreamcode.report.model.user.User;
import cc.dreamcode.report.model.user.UserRepository;
import cc.dreamcode.report.optional.CustomOptional;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredPlayer
@RequiredPermission(permission = "rpl.report.panel")
public class PanelArgument extends ArgumentHandler {

    private @Inject ReportPlugin reportPlugin;
    private @Inject MessageConfig messageConfig;
    private @Inject UserRepository userRepository;

    public PanelArgument() {
        super("panel", 1);
    }

    @Override
    public void handle(@NonNull CommandSender sender, @NonNull String[] args) {
        if (args.length == 1) {
            this.send(this.messageConfig.usage, sender, new MapBuilder<String, Object>()
                    .put("usage", "/report panel [nick]")
                    .build());
            return;
        }

        final Player player = (Player) sender;
        final String nick = args[1];
        final Optional<User> optionalUser = this.userRepository.findByName(nick, true).join();
        CustomOptional.of(optionalUser).ifPresentOrElse(userTarget -> {
            if (userTarget.getReportMap().isEmpty()) {
                this.send(this.messageConfig.reportEmpty, player);
                return;
            }

            final ReportPanelMenu reportPanelMenu = this.createInstance(ReportPanelMenu.class);
            reportPanelMenu.setUserTarget(userTarget);

            reportPanelMenu.build(player).openFirstPage(player);
        }, () -> this.send(this.messageConfig.noPlayerOrCannotFindReports, player));
    }

    @Override
    public List<String> tab(@NonNull Player player, @NonNull String[] args) {
        if (args.length == 2) {
            return this.userRepository
                    .streamAll()
                    .map(User::getName)
                    .collect(Collectors.toList());
        }

        return null;
    }
}
