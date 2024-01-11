package cc.dreamcode.nagroda.component.classes;

import cc.dreamcode.nagroda.component.resolvers.ComponentClassResolver;
import cc.dreamcode.nagroda.content.NagrodaManager;
import com.google.common.collect.ImmutableMap;
import eu.okaeri.injector.Injector;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class JdaListenerComponentClassResolver extends ComponentClassResolver<Class<? extends ListenerAdapter>> {

    private @Inject NagrodaManager nagrodaManager;

    @Override
    public boolean isAssignableFrom(@NonNull Class<? extends ListenerAdapter> listenerClass) {
        return ListenerAdapter.class.isAssignableFrom(listenerClass);
    }

    @Override
    public String getComponentName() {
        return "jda-listener (event)";
    }

    @Override
    public Map<String, Object> getMetas(@NonNull Injector injector, @NonNull Class<? extends ListenerAdapter> listenerClass) {
        return new ImmutableMap.Builder<String, Object>()
                .put("events", Arrays.stream(listenerClass.getDeclaredMethods())
                        .filter(method -> method.getAnnotation(Override.class) != null)
                        .map(Method::getName)
                        .collect(Collectors.joining(", ")))
                .build();
    }

    @Override
    public Object resolve(@NonNull Injector injector, @NonNull Class<? extends ListenerAdapter> listenerClass) {
        final ListenerAdapter listener = injector.createInstance(listenerClass);

        this.nagrodaManager.getJda().addEventListener(listener);

        return listener;
    }
}
