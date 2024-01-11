package cc.dreamcode.cobblex.features.command;

import cc.dreamcode.cobblex.features.notice.NoticeService;
import cc.dreamcode.cobblex.features.validation.ValidationService;
import lombok.NonNull;
import org.bukkit.command.CommandSender;

public interface CommandArgHandler extends NoticeService, ValidationService {

    void handle(@NonNull CommandSender sender, @NonNull String[] args);

}
