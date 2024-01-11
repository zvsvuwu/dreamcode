package cc.dreamcode.restarts.component.resolvers;

import cc.dreamcode.restarts.RestartLogger;
import cc.dreamcode.restarts.RestartPlugin;
import eu.okaeri.injector.Injector;
import lombok.NonNull;

import java.util.Map;

public abstract class ComponentObjectResolver<T> {

    public abstract boolean isAssignableFrom(@NonNull Class<T> t);
    public abstract String getComponentName();
    public abstract Map<String, Object> getMetas(@NonNull Injector injector, @NonNull T t);

    public Object process(@NonNull Injector injector, @NonNull T t) {
        long start = System.currentTimeMillis();

        injector.registerInjectable(t);

        long took = System.currentTimeMillis() - start;
        RestartPlugin.getRestartLogger().info(
                new RestartLogger.Loader()
                        .type("Added " + this.getComponentName() + " object")
                        .name(t.getClass().getSimpleName())
                        .took(took)
                        .meta(this.getMetas(injector, t))
                        .build()
        );

        return t;
    }
}
