package xyz.ravis96.dreamfreeze.commands;

import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import net.dzikoysk.funnycommands.FunnyCommands;
import panda.utilities.text.Joiner;
import xyz.ravis96.dreamfreeze.PluginMain;
import xyz.ravis96.dreamfreeze.commands.admin.FreezeCommand;
import xyz.ravis96.dreamfreeze.config.subconfig.CommandsConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@RequiredArgsConstructor
public final class CommandInitializer {

    private final PluginMain plugin;

    public FunnyCommands create() {
        CommandsConfig commands = this.plugin.getPluginConfig().getCommandsConfig();

        CommandComponents adminCommands = new CommandComponents("admin")
                .command("freeze", commands.freezeCommand, FreezeCommand.class);

        return FunnyCommands.configuration(() -> this.plugin)
                .registerDefaultComponents()
                .placeholders(adminCommands.placeholders)
                .injector(this.plugin.getInjector().fork(resources -> {
                }))

                .permissionHandler((message, permission) ->
                        this.plugin.getNmsAccessor().getNoticeAccessor().send(
                                this.plugin.getMessageConfig().noPermission, message.getCommandSender(),
                                new ImmutableMap.Builder<String, String>()
                                        .put("%PERM%", permission).build()
                        ))

                .commands(adminCommands.commands)
                .install();
    }

    // Used from FunnyGuilds CommandComponent
    @RequiredArgsConstructor
    private static final class CommandComponents {

        private final String group;
        private final Map<String, Function<String, String>> placeholders = new HashMap<>();
        private final List<Class<?>> commands = new ArrayList<>();

        private CommandComponents command(String name, CommandsConfig.FunnyCommand configuration, Class<?> command) {
            if (configuration.isEnabled()) {
                this.placeholders.put(group + "." + name + ".name", key -> configuration.getName());
                this.placeholders.put(group + "." + name + ".aliases", key -> Joiner.on(", ").join(configuration.getAliases()).toString());
                this.placeholders.put(group + "." + name + ".description", key -> configuration.getDescription());
                this.commands.add(command);
            }

            return this;
        }

    }

}
