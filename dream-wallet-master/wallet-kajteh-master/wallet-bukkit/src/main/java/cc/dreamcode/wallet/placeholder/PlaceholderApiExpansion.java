package cc.dreamcode.wallet.placeholder;

import cc.dreamcode.utilities.CountUtil;
import cc.dreamcode.utilities.TimeUtil;
import cc.dreamcode.wallet.config.PluginConfig;
import cc.dreamcode.wallet.shared.MoneyUtil;
import cc.dreamcode.wallet.top.TopManager;
import cc.dreamcode.wallet.user.User;
import cc.dreamcode.wallet.user.UserManager;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.OfflinePlayer;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PlaceholderApiExpansion extends PlaceholderExpansionWrapper {

    private final PluginConfig pluginConfig;
    private final UserManager userManager;
    private final TopManager topManager;

    @Inject
    public PlaceholderApiExpansion(PluginConfig pluginConfig, UserManager userManager, TopManager topManager) {
        super("Dream-Wallet", "Ravis96", "1.0.1");
        this.pluginConfig = pluginConfig;
        this.userManager = userManager;
        this.topManager = topManager;
    }

    @Override
    public String onWrappedRequest(OfflinePlayer player, String params) {
        if (params.equalsIgnoreCase("money")) {
            final Optional<User> optionalUser = this.userManager.getUserByUuid(player.getUniqueId());
            if (!optionalUser.isPresent()) {
                return "user-null";
            }

            final User user = optionalUser.get();
            return MoneyUtil.formatMoney(this.pluginConfig.moneySuffix, user.getMoney());
        }

        if (params.equalsIgnoreCase("daily-reward")) {
            final Optional<User> optionalUser = this.userManager.getUserByUuid(player.getUniqueId());
            if (!optionalUser.isPresent()) {
                return "user-null";
            }

            final User user = optionalUser.get();
            if (user.getReward() != null) {
                final Duration coolDown = this.pluginConfig.rewardCoolDown;
                final Duration countDown = CountUtil.getCountDown(user.getReward(), coolDown);
                if (!CountUtil.isOut(countDown)) {
                    return TimeUtil.convertDurationMills(countDown);
                }
            }

            return "0ms";
        }

        for(int i = 1; i <= this.pluginConfig.topsAmount; i++) {
            if(params.equalsIgnoreCase("player-top-money-" + i)) {
                final Optional<User> optionalUser = this.topManager.getUserByMoneyTop(i);
                if (!optionalUser.isPresent()) {
                    return "Brak";
                }

                final User user = optionalUser.get();

                return user.getName();
            }

            if(params.equalsIgnoreCase("player-top-money-spent-" + i)) {
                final Optional<User> optionalUser = this.topManager.getUserByMoneySpentTop(i);
                if (!optionalUser.isPresent()) {
                    return "Brak";
                }

                final User user = optionalUser.get();

                return user.getName();
            }

            if(params.equalsIgnoreCase("money-top-money-" + i)) {
                final Optional<User> optionalUser = this.topManager.getUserByMoneyTop(i);
                if (!optionalUser.isPresent()) {
                    return MoneyUtil.formatMoney(this.pluginConfig.moneySuffix, 0);
                }

                final User user = optionalUser.get();

                return MoneyUtil.formatMoney(this.pluginConfig.moneySuffix, user.getMoney());
            }

            if(params.equalsIgnoreCase("money-top-money-spent-" + i)) {
                final Optional<User> optionalUser = this.topManager.getUserByMoneySpentTop(i);
                if (!optionalUser.isPresent()) {
                    return MoneyUtil.formatMoney(this.pluginConfig.moneySuffix, 0);
                }

                final User user = optionalUser.get();

                return MoneyUtil.formatMoney(this.pluginConfig.moneySuffix, user.getMoneySpent());
            }
        }

        return null;
    }
}
