package cc.dreamcode.leaderboard.component.resolvers;

import cc.dreamcode.leaderboard.LeaderboardLogger;
import cc.dreamcode.leaderboard.LeaderboardPlugin;
import eu.okaeri.injector.Injector;
import lombok.NonNull;

import java.util.Map;

@SuppressWarnings("rawtypes")
public abstract class ComponentClassResolver<T extends Class> {

    public abstract boolean isAssignableFrom(@NonNull T t);
    public abstract String getComponentName();
    public abstract Map<String, Object> getMetas(@NonNull Injector injector, @NonNull T t);
    public abstract Object resolve(@NonNull Injector injector, @NonNull T t);

    public Object process(@NonNull Injector injector, @NonNull T t) {
        long start = System.currentTimeMillis();

        final Object object = this.resolve(injector, t);
        injector.registerInjectable(object);

        long took = System.currentTimeMillis() - start;
        LeaderboardPlugin.getLeaderboardLogger().info(
                new LeaderboardLogger.Loader()
                        .type("Added " + this.getComponentName() + " component")
                        .name(t.getSimpleName())
                        .took(took)
                        .meta(this.getMetas(injector, t))
                        .build()
        );

        return object;
    }

}
