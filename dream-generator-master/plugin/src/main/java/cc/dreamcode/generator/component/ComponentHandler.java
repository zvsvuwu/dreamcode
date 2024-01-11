package cc.dreamcode.generator.component;

import cc.dreamcode.generator.PluginLogger;
import cc.dreamcode.generator.PluginMain;
import cc.dreamcode.generator.component.resolver.CommandComponentResolver;
import cc.dreamcode.generator.component.resolver.ListenerComponentResolver;
import cc.dreamcode.generator.component.resolver.RunnableComponentResolver;
import cc.dreamcode.generator.features.command.CommandUse;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public final class ComponentHandler {

    private final PluginMain pluginMain;

    public void registerComponent(Object component) {
        if (component instanceof CommandUse) {
            new CommandComponentResolver().resolve(
                    this.pluginMain,
                    this.pluginMain.getInjector(),
                    (CommandUse) component
            );
            return;
        }

        if (component instanceof Listener) {
            new ListenerComponentResolver().resolve(
                    this.pluginMain,
                    this.pluginMain.getInjector(),
                    (Listener) component
            );
            return;
        }

        if (component instanceof Runnable) {
            new RunnableComponentResolver().resolve(
                    this.pluginMain,
                    this.pluginMain.getInjector(),
                    (Runnable) component
            );
            return;
        }

        long start = System.currentTimeMillis();

        this.pluginMain.getInjector().registerInjectable(component.getClass());

        long took = System.currentTimeMillis() - start;
        PluginMain.getPluginLogger().info(
                new PluginLogger.Loader()
                        .type("Poprawnie dodano obiekt")
                        .name(component.getClass().getSimpleName())
                        .took(took)
                        .meta("methods", Arrays.stream(component.getClass().getDeclaredMethods())
                                .filter(method -> method.getAnnotation(EventHandler.class) != null)
                                .map(Method::getName)
                                .collect(Collectors.toList()))
                        .build()
        );
    }

}
