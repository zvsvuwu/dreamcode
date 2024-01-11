package cc.dreamcode.ffa.user.ranking;

import cc.dreamcode.command.bukkit.BukkitCommand;
import cc.dreamcode.ffa.config.MessageConfig;
import cc.dreamcode.notice.minecraft.MinecraftNoticeType;
import cc.dreamcode.notice.minecraft.bukkit.BukkitNotice;
import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.command.CommandSender;

import java.util.List;

public class UserRankingCommand extends BukkitCommand {

    private final UserRanking userRanking;

    private final MessageConfig messageConfig;

    @Inject
    public UserRankingCommand(final UserRanking userRanking, final MessageConfig messageConfig) {
        super("ranking", "ffatops");
        this.userRanking = userRanking;
        this.messageConfig = messageConfig;
    }

    @Override
    public void content(@NonNull CommandSender sender, @NonNull String[] args) {
        if (args.length != 1) {
            this.messageConfig.usage.send(sender, new MapBuilder<String, Object>()
                    .put("usage", "/ranking <points|kill-streak|max-kill-streak>-<place>")
                    .build());
            return;
        }
        BukkitNotice.of(MinecraftNoticeType.CHAT, UserRankingUtil.requestTops(args[0], this.userRanking, this.messageConfig))
                .send(sender);
    }

    @Override
    public List<String> tab(@NonNull CommandSender sender, @NonNull String[] args) {
        return null;
    }
}
