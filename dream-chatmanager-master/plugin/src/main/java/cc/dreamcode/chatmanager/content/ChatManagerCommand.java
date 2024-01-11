package cc.dreamcode.chatmanager.content;

import cc.dreamcode.chatmanager.ChatManagerPlugin;
import cc.dreamcode.chatmanager.builder.MapBuilder;
import cc.dreamcode.chatmanager.command.CommandHandler;
import cc.dreamcode.chatmanager.command.annotations.RequiredPermission;
import cc.dreamcode.chatmanager.command.annotations.RequiredPlayer;
import cc.dreamcode.chatmanager.config.MessageConfig;
import cc.dreamcode.chatmanager.config.PluginConfig;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@RequiredPlayer
@RequiredPermission(permission = "dreamcm.admin")
public class ChatManagerCommand extends CommandHandler {
    private @Inject MessageConfig messageConfig;
    private @Inject PluginConfig pluginConfig;
    private @Inject ChatManager chatManager;

    public ChatManagerCommand() {
        super("chatmanager", null);
    }

    @Override
    public void handle(@NonNull CommandSender sender, @NonNull String[] args) {
        Player p = (Player) sender;

        if (args.length != 1) {
            send(messageConfig.usage, p, new MapBuilder<String, Object>()
                    .put("usage", "/chatmanager reload")
                    .build());
            return;
        }

        if (args[0].equals("clear")) {
            ChatManagerPlugin.getChatManagerPlugin().getServer().getOnlinePlayers().forEach(player -> {

                if (!player.hasPermission("dreamcm.admin"))  {
                    for (int i=0; i<300;i++) player.sendMessage("");
                }

                send(messageConfig.chatCleared, player, new MapBuilder<String, Object>()
                        .put("gracz", p.getName())
                        .build());
            });
            return;
        }

        if (args[0].equals("unlock")) {
            if (!chatManager.isChatLocked()) {
                send(messageConfig.chatIsAlreadyUnlocked, p);
                return;
            }

            ChatManagerPlugin.getChatManagerPlugin().getServer().getOnlinePlayers().forEach(player -> {
                if (player.hasPermission("dreamcm.admin")) return;
                send(messageConfig.chatUnlockAnnouncement, player, new MapBuilder<String, Object>()
                        .put("gracz", p.getName())
                        .build());
            });
            chatManager.setChatLocked(false);
            send(messageConfig.chatUnlockSuccess, p);
            return;
        }

        if (args[0].equals("lock")) {
            if (chatManager.isChatLocked()) {
                send(messageConfig.chatIsAlreadyLocked, p);
                return;
            }

            ChatManagerPlugin.getChatManagerPlugin().getServer().getOnlinePlayers().forEach(player -> {
                if (player.hasPermission("dreamcm.admin")) return;
                send(messageConfig.chatLockAnnouncement, player, new MapBuilder<String, Object>()
                        .put("gracz", p.getName())
                        .build());
            });
            chatManager.setChatLocked(true);
            send(messageConfig.chatLockSuccess, p);
            return;
        }

        if (args[0].equals("reload")) {
            pluginConfig.load();
            send(messageConfig.configReloaded, p);
            return;
        }

        send(messageConfig.usage, p, new MapBuilder<String, Object>()
                .put("usage", "/chatmanager lock/unlock/clear/reload")
                .build());
    }

    @Override
    public List<String> tab(@NonNull Player player, @NonNull String[] args) {
        if (args.length != 1) return null;
        List<String> arguments = new ArrayList<>();
        arguments.add("lock");
        arguments.add("unlock");
        arguments.add("clear");
        arguments.add("reload");
        return arguments;
    }
}
