package cc.dreamcode.guisuffix.command;

import cc.dreamcode.command.annotations.RequiredPermission;
import cc.dreamcode.command.bukkit.BukkitCommand;
import cc.dreamcode.guisuffix.config.MessageConfig;
import cc.dreamcode.guisuffix.config.PluginConfig;
import cc.dreamcode.guisuffix.suffix.menu.item.SuffixItem;
import cc.dreamcode.guisuffix.user.User;
import cc.dreamcode.guisuffix.user.UserCache;
import cc.dreamcode.utilities.ParseUtil;
import cc.dreamcode.utilities.TimeUtil;
import cc.dreamcode.utilities.builder.ListBuilder;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.optional.CustomOptional;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredPermission(permission = "dreamcode.adminsuffix")
public class AdminSuffixCommand extends BukkitCommand {

    private final PluginConfig pluginConfig;

    private final MessageConfig messageConfig;

    private final Tasker tasker;

    private final UserCache userCache;

    @Inject
    public AdminSuffixCommand(PluginConfig pluginConfig, MessageConfig messageConfig, Tasker tasker, UserCache userCache) {
        super("adminsuffix", "asuffix");

        this.pluginConfig = pluginConfig;
        this.messageConfig = messageConfig;
        this.tasker = tasker;
        this.userCache = userCache;
    }

    @Override
    public void content(@NonNull CommandSender sender, @NonNull String[] args) {
        if (args.length == 0) {
            this.messageConfig.adminSuffixCommandUsage.send(sender);
            return;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            this.messageConfig.load();
            this.pluginConfig.load();

            this.messageConfig.configurationReloaded.send(sender);

            return;
        }

        if (args[0].equalsIgnoreCase("cooldown")) {
            if (args.length != 3) {
                this.messageConfig.commandUsage.send(sender, new MapBuilder<String, Object>()
                        .put("usage", "/asuffix cooldown (suffix/usunsuffix) (czas np. 1s)")
                        .build());

                return;
            }

            if (args[1].equalsIgnoreCase("suffix")) {
                CustomOptional.of(ParseUtil.parsePeriod(args[2])).ifPresentOrElse(duration -> {
                    this.pluginConfig.suffixCooldown = duration;

                    this.messageConfig.cooldownSet.send(sender, new MapBuilder<String, Object>()
                            .put("command", "suffix")
                            .put("time", TimeUtil.convertDurationSeconds(duration))
                            .build());

                    this.pluginConfig.save();
                }, () -> this.messageConfig.badTime.send(sender));
            }

            if (args[1].equalsIgnoreCase("usunsuffix")) {
                CustomOptional.of(ParseUtil.parsePeriod(args[2])).ifPresentOrElse(duration -> {
                    this.pluginConfig.removeSuffixCooldown = duration;

                    this.messageConfig.cooldownSet.send(sender, new MapBuilder<String, Object>()
                            .put("command", "suffix")
                            .put("time", TimeUtil.convertDurationSeconds(duration))
                            .build());

                    this.pluginConfig.save();
                }, () -> this.messageConfig.badTime.send(sender));
            }
            return;
        }

        if (args[0].equalsIgnoreCase("nadaj")) {
            if (args.length == 3) {
                Player target = Bukkit.getPlayer(args[1]);

                if (target == null) {
                    this.messageConfig.playerOffline.send(sender);
                    return;
                }

                Optional<SuffixItem> suffixItem = this.pluginConfig.menuConfig.suffixes.stream().filter(suffix -> suffix.getName().equalsIgnoreCase(args[2])).findAny();

                if (!suffixItem.isPresent()) {
                    this.messageConfig.invalidSuffix.send(sender);
                    return;
                }

                final User user = this.userCache.get(target);

                if (user.getSuffixes().contains(suffixItem.get().getName())) {
                    this.messageConfig.hasSuffix.send(sender);
                    return;
                }

                this.tasker.newSharedChain("userupdate:" + target.getName())
                        .async(() -> {
                            user.getSuffixes().add(suffixItem.get().getName());
                            user.save();
                        })
                        .execute();

                this.messageConfig.suffixGiven.send(sender, new MapBuilder<String, Object>()
                        .put("player", target.getName())
                        .put("suffix", suffixItem.get().getName())
                        .put("formatted", suffixItem.get().getSuffix().trim())
                        .build());

                this.messageConfig.suffixReceived.send(target, new MapBuilder<String, Object>()
                        .put("player", sender.getName())
                        .put("suffix", suffixItem.get().getName())
                        .put("formatted", suffixItem.get().getSuffix().trim())
                        .build());
            }

            return;
        }

        this.messageConfig.adminSuffixCommandUsage.send(sender);
    }

    @Override
    public List<String> tab(@NonNull CommandSender sender, @NonNull String[] args) {
        if (!sender.hasPermission("dreamcode.adminsuffix")) {
            return null;
        }

        if (args.length == 1) {
            List<String> tabCompleter = new ListBuilder<String>().add("reload").add("cooldown").add("nadaj").build();

            return tabCompleter.stream()
                    .filter(argument -> StringUtil.startsWithIgnoreCase(argument, args[0]))
                    .collect(Collectors.toList());
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("cooldown")) {
                List<String> tabCompleter = new ListBuilder<String>().add("suffix").add("usunsuffix").build();

                return tabCompleter.stream()
                        .filter(argument -> StringUtil.startsWithIgnoreCase(argument, args[1]))
                        .collect(Collectors.toList());
            }

            if (args[0].equalsIgnoreCase("nadaj")) {
                return Bukkit.getOnlinePlayers().stream()
                        .map(Player::getName)
                        .filter(name -> StringUtil.startsWithIgnoreCase(name, args[1]))
                        .collect(Collectors.toList());
            }
        }

        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("cooldown")) {
                if (args[1].equalsIgnoreCase("suffix")) {
                    return Collections.singletonList(TimeUtil.convertDurationSeconds(this.pluginConfig.suffixCooldown));
                }

                if (args[1].equalsIgnoreCase("usunsuffix")) {
                    return Collections.singletonList(TimeUtil.convertDurationSeconds(this.pluginConfig.removeSuffixCooldown));
                }
            }
            if (args[0].equalsIgnoreCase("nadaj")) {
                return this.pluginConfig.menuConfig.suffixes.stream()
                        .map(SuffixItem::getName)
                        .filter(name -> name.startsWith(args[2]))
                        .collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }
}
