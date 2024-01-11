package cc.dreamcode.shopforkills;

import cc.dreamcode.shopforkills.config.PluginConfig;
import cc.dreamcode.shopforkills.user.UserManager;
import cc.dreamcode.utilities.CountUtil;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.time.Instant;
import java.util.Objects;

public class ShopKillController implements Listener {

    private @Inject PluginConfig pluginConfig;
    private @Inject UserManager userManager;
    private @Inject Tasker tasker;

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        final Player victim = e.getEntity();
        if (victim.getKiller() != null) {
            return;
        }

        final Player killer = victim.getKiller();

        if (this.pluginConfig.preventStatFarmBySameAddress) {
            final String killerAddress = killer.getAddress().getHostString();
            if (killerAddress != null && killerAddress.equalsIgnoreCase(victim.getAddress().getHostString())) {
                return;
            }
        }

        this.tasker.newSharedChain("dbops:" + killer.getUniqueId())
                .sync(() -> this.userManager.getUser(killer.getUniqueId()).orElse(null))
                .abortIfSync(Objects::isNull)
                .acceptAsync(user -> {
                    if (this.pluginConfig.preventStatFarmBySamePlayer) {
                        if (user.getLastKillerUuid().containsKey(victim.getUniqueId())) {
                            final Instant instant = user.getLastKillerUuid().get(victim.getUniqueId());

                            if (!CountUtil.isOut(CountUtil.getCountDown(instant, this.pluginConfig.allowSamePlayerToIncreaseNumberOfKillsOverTime))) {
                                user.getLastKillerUuid().put(victim.getUniqueId(), Instant.now());
                                user.save();
                                return;
                            }
                        }
                    }

                    user.getLastKillerUuid().put(victim.getUniqueId(), Instant.now());
                    user.setKills(user.getKills() + 1);
                    user.save();
                })
                .execute();
    }
}
