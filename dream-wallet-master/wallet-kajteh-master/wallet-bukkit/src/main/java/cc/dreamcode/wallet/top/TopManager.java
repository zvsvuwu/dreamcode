package cc.dreamcode.wallet.top;

import cc.dreamcode.wallet.user.User;
import cc.dreamcode.wallet.user.UserManager;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class TopManager {
    private final UserManager userManager;

    public Optional<User> getTopUser(@NonNull TopType type, int top) {
        switch (type) {
            case MONEY:
                return this.getUserByMoneyTop(top);
            case MONEY_SPENT:
                return this.getUserByMoneySpentTop(top);
            default:
                return Optional.empty();
        }
    }

    public Optional<User> getUserByMoneyTop(int top) {
        final List<User> userList = this.userManager.getUserList();

        userList.sort(Comparator.comparingDouble(User::getMoney).reversed());

        if (top > 0 && top <= userList.size()) {
            return Optional.of(userList.get(top - 1));
        } else {
            return Optional.empty();
        }
    }

    public Optional<User> getUserByMoneySpentTop(int top) {
        final List<User> userList = this.userManager.getUserList();

        userList.sort(Comparator.comparingDouble(User::getMoneySpent).reversed());

        if (top > 0 && top <= userList.size()) {
            return Optional.of(userList.get(top - 1));
        } else {
            return Optional.empty();
        }
    }
}
