package cc.dreamcode.tops.user.top;

import cc.dreamcode.tops.config.PluginConfig;
import cc.dreamcode.tops.user.User;
import cc.dreamcode.tops.user.UserManager;
import eu.okaeri.injector.annotation.Inject;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class TopCalculator {

    private @Inject PluginConfig pluginConfig;
    private @Inject UserManager userManager;

    public List<UserTop> getTopList(@NonNull UserData userData) {

        final AtomicLong position = new AtomicLong(1);
        return this.userManager.getUsers()
                .stream()
                .sorted(Comparator.comparingDouble(userData.getFunction()).reversed())
                .limit(pluginConfig.topSize)
                .map(user -> new UserTop(userData, user, position.getAndIncrement()))
                .collect(Collectors.toList());
    }

    @Getter
    @RequiredArgsConstructor
    static class UserTop {

        private final UserData userData;
        private final User user;
        private final long position;

    }

}
