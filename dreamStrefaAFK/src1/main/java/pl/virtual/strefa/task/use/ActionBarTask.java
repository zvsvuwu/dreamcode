package pl.virtual.strefa.task.use;

import org.bukkit.Bukkit;
import pl.virtual.strefa.manager.User;
import pl.virtual.strefa.task.TaskUse;
import pl.virtual.strefa.util.ChatUtil;
import pl.virtual.strefa.util.TimerUtil;

public class ActionBarTask extends TaskUse {
    public ActionBarTask(long time) {super(time);}

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(all -> {
            if (all.hasPermission(configPlugin.antyAfkPerms)) return;
            User u = userManager.getOrCreateUser(all);
            if (dataUtil.isInAntyAfk(all.getLocation())) {
                if (configPlugin.awardMessageStatus && configPlugin.awardStatus) {
                    message.message(all, configPlugin.awardType, ChatUtil.fixColor(configPlugin.awardMessage.replace("{TIME}", TimerUtil.convertLong((u.getTime() + configPlugin.awardTime - (System.currentTimeMillis() / 1000L))))));
                }
            }
        });
    }
}
