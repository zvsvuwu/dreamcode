package cc.dreamcode.report.command.report;

import cc.dreamcode.report.builder.MapBuilder;
import cc.dreamcode.report.command.ArgumentHandler;
import cc.dreamcode.report.command.annotations.RequiredPermission;
import cc.dreamcode.report.config.MessageConfig;
import cc.dreamcode.report.model.user.User;
import cc.dreamcode.report.model.user.UserRepository;
import cc.dreamcode.report.optional.CustomOptional;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredPermission(permission = "rpl.report.closeall")
public class CloseallArgument extends ArgumentHandler {

    private @Inject MessageConfig messageConfig;
    private @Inject UserRepository userRepository;

    public CloseallArgument() {
        super("closeall", 1);
    }

    @Override
    public void handle(@NonNull CommandSender sender, @NonNull String[] args) {
        if (args.length == 1) {
            this.send(this.messageConfig.usage, sender, new MapBuilder<String, Object>()
                    .put("usage", "/report closeall [nick]")
                    .build());
            return;
        }

        final Player player = (Player) sender;
        final String nick = args[1];
        final Optional<User> optionalUser = this.userRepository.findByName(nick, true).join();
        CustomOptional.of(optionalUser).ifPresentOrElse(userTarget -> {
            userTarget.setReportMap(new HashMap<>());
            userTarget.saveAsync();

            this.send(this.messageConfig.reportCleared, player, new MapBuilder<String, Object>()
                    .put("nick", userTarget.getName())
                    .build());
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
