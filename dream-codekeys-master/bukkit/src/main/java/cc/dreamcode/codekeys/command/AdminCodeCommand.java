package cc.dreamcode.codekeys.command;

import cc.dreamcode.codekeys.config.MessageConfig;
import cc.dreamcode.codekeys.config.PluginConfig;
import cc.dreamcode.codekeys.user.UserManager;
import cc.dreamcode.command.annotations.RequiredPermission;
import cc.dreamcode.command.bukkit.BukkitCommand;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.optional.CustomOptional;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

@RequiredPermission
public class AdminCodeCommand extends BukkitCommand {

    private @Inject PluginConfig pluginConfig;
    private @Inject MessageConfig messageConfig;
    private @Inject UserManager userManager;

    public AdminCodeCommand() {
        super("akod");
    }

    @Override
    public void content(@NonNull CommandSender sender, @NonNull String[] args) {
        if (args.length != 0) {
            if (args[0].equalsIgnoreCase("create")){
                if (args.length < 3) {
                    this.messageConfig.usage.send(sender, new MapBuilder<String, Object>()
                            .put("usage", "/akod create [kod] [komenda]")
                            .build());
                    return;
                }
                String code = args[1].toUpperCase();
                if (this.pluginConfig.keys.containsKey(code)) {
                    this.messageConfig.codeExists.send(sender);
                    return;
                }
                String command = "";
                for (int i = 2; i < args.length; i++) {
                    command += args[i] + " ";
                }
                if (command.isEmpty()) {
                    this.messageConfig.usage.send(sender, new MapBuilder<String, Object>()
                            .put("usage", "/akod create [kod] [komenda]")
                            .build());
                    return;
                }
                command = command.substring(0, command.length() - 1);
                this.pluginConfig.keys.put(code, command);
                this.pluginConfig.save();
                this.messageConfig.codeCreation.send(sender, new MapBuilder<String, Object>()
                        .put("code", code)
                        .build());
                return;
            }
            else if (args[0].equalsIgnoreCase("delete")) {
                if (args.length != 2) {
                    this.messageConfig.usage.send(sender, new MapBuilder<String, Object>()
                            .put("usage", "/akod delete [kod]")
                            .build());
                    return;
                }
                String code = args[1].toUpperCase();
                if (!this.pluginConfig.keys.containsKey(code)) {
                    this.messageConfig.codeDoesNotExists.send(sender);
                    return;
                }
                this.pluginConfig.keys.remove(code);
                this.pluginConfig.save();
                this.messageConfig.codeDelete.send(sender, new MapBuilder<String, Object>()
                        .put("code", code)
                        .build());
                return;
            }
            else if (args[0].equalsIgnoreCase("list")) {
                this.messageConfig.codeList.send(sender);
                this.pluginConfig.keys.keySet().forEach(s -> sender.sendMessage("&f> " + s));
                return;
            }
            else if (args[0].equalsIgnoreCase("reset")) {
                if (args.length != 2) {
                    this.messageConfig.usage.send(sender, new MapBuilder<String, Object>()
                            .put("usage", "/akod reset [nick/all]")
                            .build());
                    return;
                }
                if (args[1].equalsIgnoreCase("all")) {
                    this.userManager.getUsers().values().forEach(user -> {
                        if (user.getCode() != null) {
                            user.setCode(null);
                            this.userManager.getQuit().add(user.getUniqueId());
                        }
                    });
                    this.messageConfig.resetAllPlayers.send(sender);
                    return;
                }
                else {
                    CustomOptional.of(this.userManager.getByName(args[1])).ifPresentOrElse(user -> {
                        if (user.getCode() == null) {
                            this.messageConfig.playerHasNoCode.send(sender);
                        }
                        else {
                            user.setCode(null);
                            this.userManager.getQuit().add(user.getUniqueId());
                            this.messageConfig.resetPlayer.send(sender);
                        }
                        }, () -> messageConfig.noPlayer.send(sender));
                }
                return;
            }
            else if (args[0].equalsIgnoreCase("reload")) {
                this.messageConfig.load();
                this.pluginConfig.load();
                this.pluginConfig.toUpperCase();
                this.messageConfig.reload.send(sender);
            }
        }
        this.messageConfig.help.send(sender);
    }

    @Override
    public List<String> tab(@NonNull CommandSender sender, @NonNull String[] args) {
        if (args.length == 1) {
            return Arrays.asList("create", "delete", "reset", "list", "reload");
        }
        return null;
    }
}
