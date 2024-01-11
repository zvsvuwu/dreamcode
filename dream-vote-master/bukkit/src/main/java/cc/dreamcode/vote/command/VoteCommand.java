package cc.dreamcode.vote.command;

import cc.dreamcode.command.bukkit.BukkitCommand;
import cc.dreamcode.utilities.builder.ListBuilder;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.vote.config.MessageConfig;
import cc.dreamcode.vote.manager.VoteManager;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class VoteCommand extends BukkitCommand {
    private @Inject VoteManager voteManager;
    private @Inject MessageConfig messageConfig;

    public VoteCommand() {
        super("vote", "glosowanie");
    }

    @Override
    public void content(@NonNull CommandSender sender, @NonNull String[] args) {
        Player player = (Player) sender;

        if (args.length != 1 || (!args[0].equals("za") && !args[0].equals("przeciw"))) {
            this.messageConfig.usage.send(player, Collections.singletonMap("usage", "/vote <za/przeciw>"));
            return;
        }

        if (!this.voteManager.isVoteInProgress()) {
            this.messageConfig.noActiveVoting.send(player);
            return;
        }

        if (this.voteManager.getPlayersWhoVoted().contains(player.getUniqueId())) {
            this.messageConfig.alreadyVoted.send(player);
            return;
        }

        if (args[0].equals("za")) {
            this.voteManager.getForVotes().add(player.getUniqueId());
            this.voteManager.getPlayersWhoVoted().add(player.getUniqueId());
            this.messageConfig.forVoteSuccess.send(player);
        }

        if (args[0].equals("przeciw")) {
            this.voteManager.getAgainstVotes().add(player.getUniqueId());
            this.voteManager.getPlayersWhoVoted().add(player.getUniqueId());
            this.messageConfig.againstVoteSuccess.send(player);
        }

    }

    @Override
    public List<String> tab(@NonNull Player player, @NonNull String[] args) {
        if (args.length == 1) {
            return new ListBuilder<String>()
                    .add("za")
                    .add("przeciw")
                    .build();
        }
        return null;
    }
}
