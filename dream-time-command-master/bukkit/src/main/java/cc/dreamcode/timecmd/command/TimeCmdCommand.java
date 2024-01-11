package cc.dreamcode.timecmd.command;

import cc.dreamcode.command.annotations.RequiredPermission;
import cc.dreamcode.command.bukkit.BukkitCommand;
import cc.dreamcode.timecmd.config.MessageConfig;
import cc.dreamcode.timecmd.config.PluginConfig;
import cc.dreamcode.utilities.ParseUtil;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.optional.CustomOptional;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiredPermission
public class TimeCmdCommand extends BukkitCommand {

    private @Inject PluginConfig pluginConfig;
    private @Inject MessageConfig messageConfig;

    public TimeCmdCommand() {
        super("timecommand", "tc");
    }

    @Override
    public void content(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        if (args.length != 0) {
            if (args[0].equalsIgnoreCase("reload")) {
                this.pluginConfig.load();
                this.messageConfig.load();
                this.messageConfig.reload.send(sender);
                return;
            }
            else if (args[0].equalsIgnoreCase("create")) {
                if (args.length >= 3) {
                    String cmd = args[1].toLowerCase().replaceFirst("/", "");
                    CustomOptional.of(ParseUtil.parsePeriod(args[2])).ifPresentOrElse(duration -> {
                        this.pluginConfig.timeCommands.put(cmd, args[2]);
                        this.pluginConfig.save();
                        this.messageConfig.commandCreated.send(sender, new MapBuilder<String, Object>()
                                .put("command", cmd)
                                .put("time", args[2])
                                .build());
                    }, () -> {
                        this.messageConfig.badTime.send(sender);
                    });
                }
                else {
                    this.messageConfig.usage.send(sender, Collections.singletonMap("usage", "/timecommand create <command> <time>"));
                }
                return;
            }
            else if (args[0].equalsIgnoreCase("delete")) {
                if (args.length >= 2) {
                    String cmd = args[1].toLowerCase().replaceFirst("/", "");
                    if (!this.pluginConfig.timeCommands.containsKey(cmd)) {
                        this.messageConfig.commandDoesNotExists.send(sender);
                        return;
                    }
                    this.pluginConfig.timeCommands.remove(cmd);
                    this.pluginConfig.save();
                    this.messageConfig.commandDeleted.send(sender, new MapBuilder<String, Object>()
                            .put("command", cmd)
                            .build());
                }
                else {
                    this.messageConfig.usage.send(sender, Collections.singletonMap("usage", "/timecommand delete <command>"));
                }
                return;
            }
            else if (args[0].equalsIgnoreCase("list")) {
                this.pluginConfig.timeCommands.forEach((cmd, time) -> {
                    this.messageConfig.listFormat.send(sender, new MapBuilder<String, Object>()
                            .put("command", cmd)
                            .put("time", time)
                            .build());
                });
                return;
            }
        }
        this.messageConfig.commandHelp.send(sender);
    }

    @Override
    public List<String> tab(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        if (args.length == 1) {
            return Arrays.asList("reload", "create", "delete", "list");
        }
        return null;
    }
}
