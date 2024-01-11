package xyz.ravis96.dreamfreeze.nms.notice;

import com.cryptomorin.xseries.messages.ActionBar;
import com.cryptomorin.xseries.messages.Titles;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;

public class NoticeAccessor {
    private void send(Notice notice, Player player, String text) {
        switch (notice.getType()) {
            case CHAT: {
                player.sendMessage(text);
                break;
            }
            case ACTION_BAR: {
                ActionBar.sendActionBar(player, text);
                break;
            }
            case TITLE: {
                Titles.sendTitle(player, text, " ");
                break;
            }
            case SUBTITLE: {
                Titles.sendTitle(player, " ", text);
                break;
            }
            case SIDEBAR: {
                final FastBoard fastBoard = new FastBoard(player);
                fastBoard.updateLines(text);

                final Timer timer = new Timer();
                final TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        if(fastBoard.isDeleted()) return;
                        fastBoard.delete();
                    }
                };

                timer.schedule(task, notice.getDuration() * 50L);
                break;
            }
            default: {
                player.sendMessage(text);
            }
        }
    }

    public void send(Notice notice, Player player) {
        this.send(notice, player, notice.getColoredText());
    }

    public void send(Notice notice, CommandSender commandSender) {
        if(commandSender instanceof Player) {
            this.send(notice, (Player) commandSender);
            return;
        }

        commandSender.sendMessage(notice.getColoredText());
    }

    public void send(Notice notice, Collection<Player> players) {
        players.forEach(player -> this.send(notice, player));
    }

    public void send(Notice notice, Player player, Map<String, String> mapReplacer) {
        final String text = this.replaceFromMap(notice.getColoredText(), mapReplacer);

        this.send(notice, player, text);
    }

    public void send(Notice notice, CommandSender commandSender, Map<String, String> mapReplacer) {
        if(commandSender instanceof Player) {
            this.send(notice, (Player) commandSender, mapReplacer);
            return;
        }

        final String text = this.replaceFromMap(notice.getColoredText(), mapReplacer);
        commandSender.sendMessage(text);
    }

    public void send(Notice notice, Collection<Player> players, Map<String, String> mapReplacer) {
        players.forEach(player -> this.send(notice, player, mapReplacer));
    }

    private String replaceFromMap(final String text, Map<String, String> map) {
        return map.entrySet().stream()
                .map(entryToReplace -> (Function<String, String>) s ->
                        s.replace(entryToReplace.getKey(), entryToReplace.getValue()))
                .reduce(Function.identity(), Function::andThen)
                .apply(text);
    }
}
