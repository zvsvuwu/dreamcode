package cc.dreamcode.dropsmp.features.tasks;


import cc.dreamcode.dropsmp.config.MessageConfig;
import cc.dreamcode.dropsmp.config.PluginConfig;
import cc.dreamcode.dropsmp.features.notice.Notice;
import cc.dreamcode.dropsmp.features.notice.NoticeService;
import cc.dreamcode.dropsmp.features.user.persistence.User;
import cc.dreamcode.dropsmp.features.user.persistence.UserRepository;
import cc.dreamcode.dropsmp.stereotypes.Scheduler;
import com.google.common.collect.ImmutableMap;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.Bukkit;

@Scheduler(start = 0L, repeat = 20L)
public class ActionbarTask implements Runnable, NoticeService {
    private @Inject MessageConfig messageConfig;
    private @Inject UserRepository userRepository;

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            User user = userRepository.get(player, false);
            this.send(messageConfig.actionbarMessage, player, new ImmutableMap.Builder<String, Object>()
                    .put("strength", "\uD83D\uDDE1" + user.getStrength())
                    .put("speed", "⚡" + user.getSpeed())
                    .put("resistance", "\uD83D\uDEE1" + user.getResistance())
                    .build());
        });
    }
}
