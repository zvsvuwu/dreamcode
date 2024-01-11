package cc.dreamcode.dropsmp.features.user;

import cc.dreamcode.dropsmp.config.MessageConfig;
import cc.dreamcode.dropsmp.config.PluginConfig;
import cc.dreamcode.dropsmp.config.subconfig.DropSmpConfig;
import cc.dreamcode.dropsmp.features.notice.Notice;
import cc.dreamcode.dropsmp.features.notice.NoticeService;
import cc.dreamcode.dropsmp.features.randomizer.DoubleRandomizer;
import cc.dreamcode.dropsmp.features.user.persistence.User;
import cc.dreamcode.dropsmp.features.user.persistence.UserRepository;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class UserDeathHandler implements Listener, NoticeService {

    private @Inject UserRepository userRepository;

    private @Inject MessageConfig messageConfig;

    private @Inject PluginConfig pluginConfig;

    @EventHandler
    public void onUserDeath(EntityDeathEvent e) {
        if (e.getEntity() instanceof Player) {
            final Player player = (Player) e.getEntity();
            final User user = userRepository.get(player, false);
            userDeath(user, null);
            if (player.getKiller() != null) {
                final Player killer = player.getKiller();
                final User uKiller = userRepository.get(killer, false);
                userDeath(user, uKiller);
            }
        }
    }

    private void userDeath(User user, User killer) {
        final DropSmpConfig config = this.pluginConfig.dropSmpConfig;
        user.setStrength(user.getStrength() - 10.0);
        user.setResistance(user.getResistance() - 10.0);
        user.setSpeed(user.getSpeed() - 10.0);
        user.getPlayer().ifPresent(player -> player.setWalkSpeed((float) ((user.getSpeed() / 1000) * 2)));
        if (user.getStrength() == 0 || user.getResistance() == 0 || user.getSpeed() == 0) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), config.command.replace("player", user.getName()));
            user.setStrength(100.0);
            user.setSpeed(100.0);
            user.setResistance(100.0);
        }
        if (killer == null) {
            Bukkit.getOnlinePlayers().forEach(player ->
                    this.send(messageConfig.userDeath, player,
                            new ImmutableMap.Builder<String, Object>()
                            .put("player", user.getName())
                            .build())
            );
            return;
        }
        if (config.giveStatssAfterKill) {
            if (config.chanceToDropChest <= DoubleRandomizer.range(0D,100D)) {
               killer.getPlayer().ifPresent(player -> player.getWorld().dropItemNaturally(player.getLocation(), config.cases));
            }
            final double procent = config.numberOfStatsAddAfterKill;
            if (killer.getStrength() < config.maxNumerOfStrengthStats) killer.setStrength(user.getStrength() + procent);
            else this.send(messageConfig.maxNumberOfStats, user.getPlayer().get(),
                    new ImmutableMap.Builder<String, Object>()
                    .put("stats", "sily")
                    .build()
            );
            if (killer.getSpeed() < config.maxNumerOfSpeedStats) killer.setSpeed(user.getSpeed() + procent);
            else this.send(messageConfig.maxNumberOfStats, user.getPlayer().get(),
                    new ImmutableMap.Builder<String, Object>()
                            .put("stats", "predkosci")
                            .build()
            );
            if (killer.getResistance() < config.maxNumerOfResistanceStats) killer.setResistance(user.getResistance() + procent);
            else this.send(messageConfig.maxNumberOfStats, user.getPlayer().get(),
                    new ImmutableMap.Builder<String, Object>()
                            .put("stats", "ochrony")
                            .build()
            );
        }
        Bukkit.getOnlinePlayers().forEach(player ->
                this.send(messageConfig.userKilledByOtherUser, user.getPlayer().get(),
                new ImmutableMap.Builder<String, Object>()
                        .put("killer", killer.getName())
                        .put("player", user.getName())
                        .build())
        );
    }
}
