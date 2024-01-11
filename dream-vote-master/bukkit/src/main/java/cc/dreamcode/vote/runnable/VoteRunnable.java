package cc.dreamcode.vote.runnable;

import cc.dreamcode.platform.bukkit.component.scheduler.Scheduler;
import cc.dreamcode.vote.BukkitVotePlugin;
import cc.dreamcode.vote.config.MessageConfig;
import cc.dreamcode.vote.config.PluginConfig;
import cc.dreamcode.vote.manager.VoteManager;
import eu.okaeri.injector.annotation.Inject;

import java.util.Collections;

@Scheduler(interval = 20, delay = 20)
public class VoteRunnable implements Runnable {
    private @Inject BukkitVotePlugin bukkitVotePlugin;
    private @Inject PluginConfig pluginConfig;
    private @Inject MessageConfig messageConfig;
    private @Inject VoteManager voteManager;

    private int time = 0;

    @Override
    public void run() {
        if (time == this.pluginConfig.voteTime) {
            this.bukkitVotePlugin.getServer().getOnlinePlayers().forEach(player -> {
                this.messageConfig.voteStartAnnouncement.send(player, Collections.singletonMap("time", time));
            });
            this.voteManager.setVoteInProgress(true);
            time--;
            return;
        }

        if (time != 0) {
            time--;
            return;
        }

        int forSize = this.voteManager.getForVotes().size();
        int againstSize = this.voteManager.getAgainstVotes().size();

        if (forSize > againstSize) {
            this.bukkitVotePlugin.getServer().getOnlinePlayers().forEach(player -> {
                this.messageConfig.forVoteWin.send(player);
            });

            this.bukkitVotePlugin.getServer().getScheduler().runTask(this.bukkitVotePlugin, () -> {

                this.bukkitVotePlugin.getServer().dispatchCommand(
                        this.bukkitVotePlugin.getServer().getConsoleSender(),
                        this.pluginConfig.voteEndCommand
                                .replace("/", "")
                );
            });
        }

        if (forSize == againstSize) {
            this.bukkitVotePlugin.getServer().getOnlinePlayers().forEach(player -> {
                this.messageConfig.equalVotes.send(player);
            });
        }

        if (forSize < againstSize) {
            this.bukkitVotePlugin.getServer().getOnlinePlayers().forEach(player -> {
                this.messageConfig.againstVoteWin.send(player);
            });
        }

        this.voteManager.setVoteInProgress(false);
        this.voteManager.getForVotes().clear();
        this.voteManager.getAgainstVotes().clear();
        this.voteManager.getPlayersWhoVoted().clear();

        this.setTime();
    }

    public void setTime() {
        this.time = this.pluginConfig.voteTime + this.pluginConfig.voteCreateTime;
    }
}
