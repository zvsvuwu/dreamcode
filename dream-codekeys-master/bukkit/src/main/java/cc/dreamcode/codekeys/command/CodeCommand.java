package cc.dreamcode.codekeys.command;

import cc.dreamcode.codekeys.CodeKeysPlugin;
import cc.dreamcode.codekeys.config.MessageConfig;
import cc.dreamcode.codekeys.config.PluginConfig;
import cc.dreamcode.codekeys.controller.QuitJoinController;
import cc.dreamcode.codekeys.user.UserManager;
import cc.dreamcode.command.annotations.RequiredPlayer;
import cc.dreamcode.command.bukkit.BukkitCommand;
import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.placeholders.context.PlaceholderContext;
import eu.okaeri.placeholders.message.CompiledMessage;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@RequiredPlayer
public class CodeCommand extends BukkitCommand {

    private @Inject CodeKeysPlugin codeKeysPlugin;
    private @Inject MessageConfig messageConfig;
    private @Inject PluginConfig pluginConfig;
    private @Inject UserManager userManager;

    public CodeCommand() {
        super("kod");
    }

    @Override
    public void content(@NonNull CommandSender sender, @NonNull String[] args) {
        Player player = (Player) sender;
        if (args.length != 1) {
            this.messageConfig.usage.send(sender, new MapBuilder<String, Object>()
                    .put("usage", "/kod [kod]")
                    .build());
            return;
        }
        String code = args[0].toUpperCase();
        this.userManager.getUser(player.getUniqueId()).ifPresent(user -> {
            if (user.getCode() != null) {
                this.messageConfig.codeUsing.send(sender);
                return;
            }
            String command = this.pluginConfig.keys.getOrDefault(code, null);
            if (command == null) {
                this.messageConfig.codeDoesNotExists.send(sender);
                return;
            }
            user.setCode(code);
            this.userManager.getQuit().add(user.getUniqueId());
            this.codeKeysPlugin.getServer().dispatchCommand(this.codeKeysPlugin.getServer().getConsoleSender(), PlaceholderContext.of(CompiledMessage.of(command))
                    .with("player", player.getName()).apply());
            this.messageConfig.codeUseSuccess.send(sender, new MapBuilder<String, Object>()
                    .put("code", code)
                    .build());
        });
    }

    @Override
    public List<String> tab(@NonNull CommandSender sender, @NonNull String[] args) {
        return null;
    }
}
