package cc.dreamcode.ffa.user.combat;

import cc.dreamcode.ffa.config.MessageConfig;
import cc.dreamcode.ffa.user.User;
import cc.dreamcode.ffa.user.UserCache;
import cc.dreamcode.platform.bukkit.component.scheduler.Scheduler;
import cc.dreamcode.utilities.TimeUtil;
import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;

/**
 * UserCombatInfoUpdateTask.java
 * Purpose: The UserCombatInfoUpdateTask is a class that sends combat status to player.
 * * @author trueman96
 * @version 1.0-beta.1
 * @since 2023-11-25
 */
@Scheduler(delay = 20L, interval = 20L)
@RequiredArgsConstructor(onConstructor_= @Inject)
public final class UserCombatInfoUpdateTask implements Runnable {

    private final DecimalFormat decimalFormat = new DecimalFormat("##");

    private final UserCache userCache;
    private final MessageConfig messageConfig;

    @Override
    public void run() {
        final long now = System.currentTimeMillis();
        for (final Player player : Bukkit.getOnlinePlayers()) {
            final User user = this.userCache.get(player);
            if (isNull(user)) {
                return;
            }

            final UserCombat combat = user.getCombat();
            if (!combat.isInCombat()) {
                return;
            }

            final long combatTime = combat.getLastAttackTime() - now;
            final double combatPercent = ((combatTime / 1000.0) * 10.0) / 3;

            this.messageConfig.combatInfo.send(player, new MapBuilder<String, Object>()
                    .put("combat-time", TimeUtil.convertMills(combatTime))
                    .put("combat-percent", this.decimalFormat.format(combatPercent))
                    .put("combat-progress", progress((int) (100 - combatPercent / 10 - 89), (int) combatPercent / 10))
                    .build());
        }
    }

    public String progress(int green, int red) {
        return "&a" + multiply(this.messageConfig.progressSymbol, green) + "&c" + multiply(this.messageConfig.progressSymbol, red);
    }

    public String multiply(String s, int n) {
        return IntStream.range(0, n).mapToObj(i -> s).collect(Collectors.joining());
    }
}
