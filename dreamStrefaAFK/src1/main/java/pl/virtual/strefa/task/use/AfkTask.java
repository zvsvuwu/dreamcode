package pl.virtual.strefa.task.use;

import org.bukkit.Bukkit;
import pl.virtual.strefa.manager.User;
import pl.virtual.strefa.task.TaskUse;
import pl.virtual.strefa.util.ChatUtil;

public class AfkTask extends TaskUse {
    public AfkTask(long time) {super(time);}

    @Override
    public void run() {
        if (configPlugin.kickStatus) {
            Bukkit.getOnlinePlayers().forEach(all -> {
                if (all.hasPermission(configPlugin.antyAfkPerms)) return;
                User u = userManager.getOrCreateUser(all);
                if (dataUtil.isInAntyAfk(all.getLocation())){
                    if (configPlugin.awardStatus) {
                        if (u.getTime() + configPlugin.awardTime < System.currentTimeMillis() / 1000L) {
                            configPlugin.itemAward.forEach(item -> ChatUtil.giveItems(all, item));
                            configPlugin.commandAward.forEach(cmd -> plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), cmd.replace("{PLAYER}", all.getName())));
                            userManager.resetTimer(u);
                            if (configPlugin.awardAddMessageStatus) message.message(all, configPlugin.awardAddType, ChatUtil.fixColor(configPlugin.awardAddMessage));
                        }
                    }
                    return;
                }
                if (u.getTime() + configPlugin.afkTime < System.currentTimeMillis() / 1000L) {
                    all.kickPlayer(ChatUtil.fixColor(configPlugin.kickReason));
                }
            });
        }
    }
}
