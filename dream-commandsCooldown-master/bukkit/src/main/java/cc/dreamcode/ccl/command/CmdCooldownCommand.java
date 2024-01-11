package cc.dreamcode.ccl.command;

import cc.dreamcode.ccl.config.MessageConfig;
import cc.dreamcode.ccl.config.PluginConfig;
import cc.dreamcode.command.annotations.RequiredPermission;
import cc.dreamcode.command.bukkit.BukkitCommand;
import cc.dreamcode.utilities.ParseUtil;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.optional.CustomOptional;
import com.google.common.collect.Lists;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

@RequiredPermission
public class CmdCooldownCommand extends BukkitCommand {

    public CmdCooldownCommand() {
        super("commandcooldown", "commandcl", "ccl");
    }

    private @Inject MessageConfig messageConfig;
    private @Inject PluginConfig pluginConfig;

    @Override
    public void content(@NonNull CommandSender sender, @NonNull String[] args) {
        if (args.length != 0) {
            if (args[0].equalsIgnoreCase("reload")) {
                this.messageConfig.load();
                this.pluginConfig.load();
                this.messageConfig.reload.send(sender);
                return;
            }
            else if (args[0].equalsIgnoreCase("all")) {
                if (args.length != 2) {
                    this.messageConfig.usage.send(sender, Collections.singletonMap("usage", "/commandcooldown all <time>"));
                    return;
                }
                CustomOptional.of(ParseUtil.parsePeriod(args[1])).ifPresentOrElse(duration -> {
                    this.pluginConfig.allCommandsCool_down = args[1];
                    this.messageConfig.allCommandsCool_downSet.send(sender, Collections.singletonMap("time", args[1]));
                    this.pluginConfig.save();
                }, () -> {
                    this.messageConfig.badTime.send(sender);
                });
                return;
            }
            else if (args[0].equalsIgnoreCase("add")) {
                if (args.length != 3) {
                    this.messageConfig.usage.send(sender, Collections.singletonMap("usage", "/commandcooldown add <command> <time>"));
                    return;
                }
                String command = args[1].contains("/") ? args[1].toLowerCase().replaceFirst("/", "") : args[1].toLowerCase();
                CustomOptional.of(ParseUtil.parsePeriod(args[2])).ifPresentOrElse(duration -> {
                    this.pluginConfig.cool_downs.put(command, args[2]);
                    this.messageConfig.cool_downAdd.send(sender, new MapBuilder<String, Object>()
                            .put("command", command)
                            .put("time", args[2])
                            .build());
                    this.pluginConfig.save();
                }, () -> this.messageConfig.badTime.send(sender));
                return;
            }
            else if (args[0].equalsIgnoreCase("remove")) {
                if (args.length != 2) {
                    this.messageConfig.usage.send(sender, Collections.singletonMap("usage", "/commandcooldown remove <command>"));
                    return;
                }
                String command = args[1].contains("/") ? args[1].toLowerCase().replaceFirst("/", "") : args[1].toLowerCase();
                if (!this.pluginConfig.cool_downs.containsKey(command)) {
                    this.messageConfig.cool_downDoesNotExists.send(sender);
                    return;
                }

                this.pluginConfig.cool_downs.remove(command);

                this.pluginConfig.save();
                this.messageConfig.cool_downRemove.send(sender, Collections.singletonMap("command", command));
                return;
            }
            else if (args[0].equalsIgnoreCase("list")) {
                this.pluginConfig.cool_downs.forEach((s, s2) -> this.messageConfig.cool_downList.send(sender, new MapBuilder<String, Object>()
                        .put("command", s)
                        .put("time", s2)
                        .build()));
                return;
            }
        }
        this.messageConfig.commandHelp.send(sender);
    }

    @Override
    public List<String> tab(@NonNull CommandSender sender, @NonNull String[] args) {
        if (args.length == 1) {
            return Lists.newArrayList("reload", "all", "add", "remove", "list");
        }
        return null;
    }
}
