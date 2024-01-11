package com.eripe14.prestige.tasks.list;

import com.eripe14.prestige.bukkit.TimeUtils;
import com.eripe14.prestige.features.user.User;
import com.eripe14.prestige.tasks.TaskUse;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class UserTask extends TaskUse {

    @Override
    public void runTask() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers( )) {
            User user = this.userManager.getOrCreate(onlinePlayer);

            if (!user.isCanExecuteCommand()) return;

            if (user.getExecutePossibilityExpireIn() < System.currentTimeMillis()) {
                user.setCanExecuteCommand(false);
                user.setExecutePossibilityExpireIn(0L);
                user.save();

                this.sendMessage(onlinePlayer, this.messageConfig.prestigePossibilityExpired);
            }

        }
    }

}
