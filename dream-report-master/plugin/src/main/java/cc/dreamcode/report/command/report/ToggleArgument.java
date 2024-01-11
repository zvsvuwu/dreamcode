package cc.dreamcode.report.command.report;

import cc.dreamcode.report.ReportPlugin;
import cc.dreamcode.report.command.ArgumentHandler;
import cc.dreamcode.report.command.annotations.RequiredPermission;
import cc.dreamcode.report.command.annotations.RequiredPlayer;
import cc.dreamcode.report.config.MessageConfig;
import cc.dreamcode.report.model.user.User;
import cc.dreamcode.report.model.user.UserRepository;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@RequiredPlayer
@RequiredPermission(permission = "rpl.report.toggle")
public class ToggleArgument extends ArgumentHandler {

    private @Inject ReportPlugin reportPlugin;
    private @Inject MessageConfig messageConfig;
    private @Inject UserRepository userRepository;

    public ToggleArgument() {
        super("toggle", 1);
    }

    @Override
    public void handle(@NonNull CommandSender sender, @NonNull String[] args) {
        final Player player = (Player) sender;

        final User user = this.userRepository.findOrCreateByPlayer(player).join();
        user.setReportToggle(!user.isReportToggle());
        user.saveAsync();

        this.send(user.isReportToggle()
                ? this.messageConfig.reportToggleOn
                : this.messageConfig.reportToggleOff, player);
    }

    @Override
    public List<String> tab(@NonNull Player player, @NonNull String[] args) {
        return null;
    }
}
