package cc.dreamcode.chatmanager.content;

import cc.dreamcode.chatmanager.ChatManagerPlugin;
import cc.dreamcode.chatmanager.builder.MapBuilder;
import cc.dreamcode.chatmanager.config.MessageConfig;
import cc.dreamcode.chatmanager.config.PluginConfig;
import cc.dreamcode.chatmanager.notice.NoticeSender;
import cc.dreamcode.chatmanager.utilities.TimeUtil;
import com.iridium.iridiumcolorapi.IridiumColorAPI;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatManagerEventsListener implements Listener, NoticeSender {
    private @Inject PluginConfig pluginConfig;
    private @Inject MessageConfig messageConfig;
    private @Inject ChatManager chatManager;

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();

        if (p.hasPermission("dreamcm.admin")) return;

        if (chatManager.isChatLocked()) {
            e.setCancelled(true);
            send(messageConfig.lockedChat, p);
            return;
        }

        if (chatManager.getPlayersOnCooldown().containsKey(p.getUniqueId())) {
            long timeElapsed = System.currentTimeMillis() - chatManager.getPlayersOnCooldown().get(p.getUniqueId());
            long timeLeft = pluginConfig.chatCooldown * 1000L - timeElapsed;
            if (timeElapsed < pluginConfig.chatCooldown * 1000L) {
                e.setCancelled(true);
                send(messageConfig.playerIsOnCooldown, p, new MapBuilder<String, Object>()
                        .put("time", TimeUtil.convertMills(timeLeft))
                        .build());
                return;
            }
            chatManager.getPlayersOnCooldown().remove(p.getUniqueId());
        }

        chatManager.getPlayersOnCooldown().put(p.getUniqueId(), System.currentTimeMillis());

        if (pluginConfig.blockCurseWords) {

            pluginConfig.curseWordsList.forEach(s -> {

                if (!e.getMessage().toLowerCase().contains(s)) return;
                e.setCancelled(true);
                send(messageConfig.forbiddenWordDetected, p);
            });
        }

        if (pluginConfig.blockLinks) {
            Pattern pattern = Pattern.compile("(http(s)?://.)?(www\\.)?[-a-zA-Z0-9@:%._+~#=]{2,256}( ?\\. ?| ?\\(?dot\\)? ?)[a-z]{2,6}\\b([-a-zA-Z0-9@:%_+.~#?&/=]*)");
            Matcher patterMatch = pattern.matcher(e.getMessage().toLowerCase().replaceAll("\\s+", ""));

            if (patterMatch.find()) {
                e.setCancelled(true);
                send(messageConfig.linkDetected, p);
            }
        }

        if (!pluginConfig.sendAdminNotice) return;
        ChatManagerPlugin.getChatManagerPlugin().getServer().getOnlinePlayers().forEach(player -> {
            if (!player.hasPermission("dreamcm.admin")) return;

            send(messageConfig.adminNotice, player, new MapBuilder<String, Object>()
                    .put("gracz", p.getName())
                    .put("tresc", e.getMessage())
                    .build());
        });
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (!pluginConfig.sendMotd) return;

        send(messageConfig.messageOfTheDay, p, new MapBuilder<String, Object>()
                .put("gracz", p.getName())
                .build());
    }
}
