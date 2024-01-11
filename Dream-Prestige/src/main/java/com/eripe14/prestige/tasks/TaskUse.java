package com.eripe14.prestige.tasks;

import com.eripe14.prestige.config.MessageConfig;
import com.eripe14.prestige.config.subconfig.PrestigeConfig;
import com.eripe14.prestige.features.user.UserManager;
import org.panda_lang.utilities.inject.annotations.Inject;
import xyz.ravis96.dreambasis.bukkit.notice.NoticeSender;

public abstract class TaskUse extends NoticeSender implements Runnable {

    @Inject public UserManager userManager;

    @Inject public MessageConfig messageConfig;

    @Inject public PrestigeConfig prestigeConfig;

    @Override
    public void run() {
        runTask();
    }

    public abstract void runTask();
}
