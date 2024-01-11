package cc.dreamcode.restarts.features.command;

import cc.dreamcode.restarts.RestartPlugin;
import cc.dreamcode.restarts.config.MessageConfig;
import cc.dreamcode.restarts.exception.PluginRuntimeException;
import cc.dreamcode.restarts.exception.PluginValidatorException;
import cc.dreamcode.restarts.features.command.annotations.RequiredPermission;
import cc.dreamcode.restarts.features.command.annotations.RequiredPlayer;
import cc.dreamcode.restarts.features.menu.MenuBaseBuilder;
import cc.dreamcode.restarts.features.notice.NoticeSender;
import lombok.NonNull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public abstract class CommandHandler extends Command implements PluginIdentifiableCommand, NoticeSender, CommandValidator {

    public CommandHandler(String name, List<String> aliases) {
        super(name);
        if (aliases != null) {
            setAliases(aliases);
        }
    }

    @NonNull
    @Override
    public Plugin getPlugin() {
        return RestartPlugin.getRestartPlugin();
    }

    protected abstract void handle(@NonNull CommandSender sender, @NonNull String[] args);

    protected abstract List<String> tab(@NonNull Player player, @NonNull String[] args);

    @Override
    public boolean execute(@NonNull CommandSender sender, @NonNull String commandLabel, @NonNull String[] arguments) {
        final MessageConfig messageConfig = RestartPlugin.getRestartPlugin().getInject(MessageConfig.class)
                .orElseThrow(() -> new PluginRuntimeException("Plugin can not get an object from a injector."));

        try {
            RequiredPermission requiredPermission = getClass().getAnnotation(RequiredPermission.class);
            if (requiredPermission != null) {
                whenNot(sender.hasPermission(requiredPermission.permission()), messageConfig.noPermission);
            }

            RequiredPlayer requiredPlayer = getClass().getAnnotation(RequiredPlayer.class);
            if (requiredPlayer != null) {
                whenNot(sender instanceof Player, messageConfig.noPlayer);
            }

            handle(sender, arguments);
        }
        catch (PluginValidatorException e) {
            if (e.getReplaceMap().isEmpty()) {
                this.send(e.getNotice(), sender);
            }
            else {
                this.send(e.getNotice(), sender, e.getReplaceMap());
            }
        }
        return true;
    }

    public @NonNull List<String> tabComplete(@NonNull CommandSender sender, @NonNull String label, @NonNull String[] args) {
        List<String> completions = tab((Player) sender, args);
        if (completions == null) {
            return new ArrayList<>();
        }
        return completions;
    }

    public CommandArgHandler getArgument(@NonNull CommandSender sender, @NonNull Class<? extends CommandArgHandler> commandArgHandlerClass) {
        final MessageConfig messageConfig = RestartPlugin.getRestartPlugin().getInject(MessageConfig.class)
                .orElseThrow(() -> new PluginRuntimeException("Plugin can not get an object from a injector."));

        RequiredPermission requiredPermission = commandArgHandlerClass.getAnnotation(RequiredPermission.class);
        if (requiredPermission != null) {
            whenNot(sender.hasPermission(requiredPermission.permission()), messageConfig.noPermission);
        }

        RequiredPlayer requiredPlayer = commandArgHandlerClass.getAnnotation(RequiredPlayer.class);
        if (requiredPlayer != null) {
            whenNot(sender instanceof Player, messageConfig.noPlayer);
        }

        return RestartPlugin.getRestartPlugin().createInstance(commandArgHandlerClass);
    }

    public MenuBaseBuilder getMenu(@NonNull Class<? extends MenuBaseBuilder> menuBaseBuilder) {
        return RestartPlugin.getRestartPlugin().createInstance(menuBaseBuilder);
    }

}
