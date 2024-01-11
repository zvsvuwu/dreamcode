package com.eripe14.prestige.commands;

import com.eripe14.prestige.PrestigePlugin;
import com.eripe14.prestige.commands.user.PrestigeCommand;
import com.eripe14.prestige.config.CommandsConfig;
import lombok.RequiredArgsConstructor;
import net.dzikoysk.funnycommands.FunnyCommands;
import xyz.ravis96.dreambasis.bukkit.command.CommandInitializer;

@RequiredArgsConstructor
public class CommandMap {

    private final PrestigePlugin plugin;

    public FunnyCommands init() {
        final CommandsConfig commandsConfig = this.plugin.getCommandsConfig();

        final CommandInitializer.CommandComponents adminComponent = new CommandInitializer.CommandComponents("user")
                .command("prestige", commandsConfig.prestigeCommand, PrestigeCommand.class);

        CommandInitializer commandInitializer = new CommandInitializer(this.plugin);
        commandInitializer.setPermissionNotice(this.plugin.getMessageConfig().noPermission);

        return commandInitializer.create(adminComponent);
    }
}
