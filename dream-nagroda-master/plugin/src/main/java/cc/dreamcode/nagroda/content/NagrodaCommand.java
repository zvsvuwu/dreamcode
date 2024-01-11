package cc.dreamcode.nagroda.content;

import cc.dreamcode.nagroda.command.CommandHandler;
import cc.dreamcode.nagroda.command.annotations.RequiredPlayer;
import cc.dreamcode.nagroda.config.MessageConfig;
import cc.dreamcode.nagroda.model.user.User;
import cc.dreamcode.nagroda.model.user.UserRepository;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RequiredPlayer
public class NagrodaCommand extends CommandHandler {
    private @Inject MessageConfig messageConfig;
    private @Inject NagrodaManager nagrodaManager;
    private @Inject UserRepository userRepository;

    public NagrodaCommand() {
        super("nagroda", new ImmutableList.Builder<String>()
                .add("nagrodadc")
                .add("nagrodadiscord")
                .build());
    }

    @Override
    public void handle(@NonNull CommandSender sender, @NotNull @NonNull String[] args) throws ExecutionException, InterruptedException {
        Player p = (Player) sender;
        CompletableFuture<User> user = userRepository.findOrCreate(p.getUniqueId(), p.getName());

        if (user.get().isRewardReceived()) {
            send(messageConfig.rewardFail, p);
            return;
        }

        nagrodaManager.createCode(p.getUniqueId());
        send(messageConfig.rewardSuccess, p, new ImmutableMap.Builder<String, Object>()
                .put("kod", nagrodaManager.codes.get(p.getUniqueId()))
                .build());
    }

    @Override
    public List<String> tab(@NonNull Player player, @NotNull @NonNull String[] args) {
        return null;
    }
}
