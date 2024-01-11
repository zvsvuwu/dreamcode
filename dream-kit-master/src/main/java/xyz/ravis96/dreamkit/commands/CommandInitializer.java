package xyz.ravis96.dreamkit.commands;

import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import net.dzikoysk.funnycommands.FunnyCommands;
import panda.utilities.text.Joiner;
import xyz.ravis96.dreamkit.PluginMain;
import xyz.ravis96.dreamkit.commands.admin.KitManageCommand;
import xyz.ravis96.dreamkit.commands.binds.CommandInfoBind;
import xyz.ravis96.dreamkit.commands.binds.UserBind;
import xyz.ravis96.dreamkit.commands.completers.KitCompleter;
import xyz.ravis96.dreamkit.commands.completers.UserCompleter;
import xyz.ravis96.dreamkit.commands.user.KitCommand;
import xyz.ravis96.dreamkit.config.subconfig.CommandsConfig;

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

        CommandComponents userCommands = new CommandComponents("user")
                .command("kit", commands.kitCommand, KitCommand.class);

        CommandComponents adminCommands = new CommandComponents("admin")
                .command("kitmanage", commands.kitManageCommand, KitManageCommand.class);

        return FunnyCommands.configuration(() -> this.plugin)
                .registerDefaultComponents()

                .placeholders(userCommands.placeholders)
                .placeholders(adminCommands.placeholders)

                .injector(this.plugin.getInjector().fork(resources -> {
                }))

                .bind(new UserBind(this.plugin.getUserManager()))
                .bind(new CommandInfoBind())

                .completer(new UserCompleter(this.plugin.getUserManager()))
                .completer(new KitCompleter(this.plugin.getPluginConfig().getKitConfig()))

                .permissionHandler((message, permission) ->
                        this.plugin.getNmsAccessor().getNoticeAccessor().send(
                                this.plugin.getMessageConfig().noPermission, message.getCommandSender(),
                                new ImmutableMap.Builder<String, String>()
                                        .put("%PERM%", permission).build()
                        ))

                .commands(userCommands.commands)
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
