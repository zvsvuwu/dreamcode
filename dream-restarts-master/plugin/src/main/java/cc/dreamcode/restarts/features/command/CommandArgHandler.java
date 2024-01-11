package cc.dreamcode.restarts.features.command;

import cc.dreamcode.restarts.features.notice.NoticeSender;
import lombok.NonNull;
import org.bukkit.command.CommandSender;

public interface CommandArgHandler extends NoticeSender, CommandValidator {

    void handle(@NonNull CommandSender sender, @NonNull String[] args);

}
