package cc.dreamcode.cobblex.features.command;

import cc.dreamcode.cobblex.PluginMain;
import cc.dreamcode.cobblex.config.MessageConfig;
import cc.dreamcode.cobblex.exception.PluginValidationException;
import cc.dreamcode.cobblex.features.command.annotations.RequiredPermission;
import cc.dreamcode.cobblex.features.command.annotations.RequiredPlayer;
import cc.dreamcode.cobblex.features.menu.MenuBaseBuilder;
import cc.dreamcode.cobblex.features.notice.NoticeService;
import cc.dreamcode.cobblex.features.validation.ValidationService;
import lombok.NonNull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public abstract class CommandHandler extends Command implements PluginIdentifiableCommand, NoticeService, ValidationService {

    public CommandHandler(String name, List<String> aliases) {
        super(name);
        if (aliases != null) {
            setAliases(aliases);
        }
    }

    @NonNull
    @Override
    public Plugin getPlugin() {
        return PluginMain.getPluginMain();
    }

    public abstract void handle(@NonNull CommandSender sender, @NonNull String[] args);

    public abstract List<String> tab(@NonNull Player player, @NonNull String[] args);

    @Override
    public boolean execute(@NonNull CommandSender sender, @NonNull String commandLabel, @NonNull String[] arguments) {
        final MessageConfig messageConfig = PluginMain.getPluginMain().getMessageConfig();
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
        catch (PluginValidationException e) {
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
        final MessageConfig messageConfig = PluginMain.getPluginMain().getMessageConfig();

        RequiredPermission requiredPermission = commandArgHandlerClass.getAnnotation(RequiredPermission.class);
        if (requiredPermission != null) {
            whenNot(sender.hasPermission(requiredPermission.permission()), messageConfig.noPermission);
        }

        RequiredPlayer requiredPlayer = commandArgHandlerClass.getAnnotation(RequiredPlayer.class);
        if (requiredPlayer != null) {
            whenNot(sender instanceof Player, messageConfig.noPlayer);
        }

        return PluginMain.getPluginMain().getInjector().createInstance(commandArgHandlerClass);
    }

    public MenuBaseBuilder getMenu(@NonNull Class<? extends MenuBaseBuilder> menuBaseBuilder) {
        return PluginMain.getPluginMain().getInjector().createInstance(menuBaseBuilder);
    }

}
