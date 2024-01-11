package com.eripe14.prestige.commands;

import com.eripe14.prestige.PrestigePlugin;
import com.eripe14.prestige.config.MessageConfig;
import com.eripe14.prestige.config.subconfig.PrestigeConfig;
import com.eripe14.prestige.features.user.UserManager;
import net.dzikoysk.funnyguilds.FunnyGuilds;
import org.panda_lang.utilities.inject.annotations.Inject;
import xyz.ravis96.dreambasis.bukkit.notice.NoticeSender;

public abstract class CommandUse extends NoticeSender {

    @Inject public PrestigePlugin plugin;

    @Inject public MessageConfig messageConfig;

    @Inject public PrestigeConfig prestigeConfig;

    @Inject public UserManager userManager;

    @Inject public FunnyGuilds funnyGuilds;

}
