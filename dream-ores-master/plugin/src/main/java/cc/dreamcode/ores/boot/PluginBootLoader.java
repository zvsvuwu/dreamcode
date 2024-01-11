package cc.dreamcode.ores.boot;

import cc.dreamcode.ores.OresLogger;
import cc.dreamcode.ores.OresPlugin;
import cc.dreamcode.ores.component.ComponentHandler;
import cc.dreamcode.ores.exception.PluginRuntimeException;
import eu.okaeri.injector.Injector;
import eu.okaeri.injector.OkaeriInjector;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class PluginBootLoader extends JavaPlugin {

    @Getter private Injector injector;
    @Getter private final AtomicBoolean pluginDisabled = new AtomicBoolean(false);

    @Override
    public void onLoad() {
        this.injector = OkaeriInjector.create();
        this.injector.registerInjectable(this);

        try {
            this.load();
        }
        catch (Exception e) {
            this.getPluginDisabled().set(true);
            throw new PluginRuntimeException("An error was caught when plugin are loading...", e, this);
        }
    }

    @Override
    public void onEnable() {
        if (this.getPluginDisabled().get()) {
            return;
        }

        if (!PluginFactory.checkPlugin(this.getPluginDisabled(), this.getDescription())) {
            return;
        }

        try {
            this.start(new ComponentHandler(this.injector));
        }
        catch (Exception e) {
            this.getPluginDisabled().set(true);
            throw new PluginRuntimeException("An error was caught when plugin are starting...", e, this);
        }

        OresPlugin.getOresLogger().info(String.format("Active version: v%s - Author: %s",
                getDescription().getVersion(),
                getDescription().getAuthors()));
    }

    @Override
    public void onDisable() {
        if (this.getPluginDisabled().get()) {
            return;
        }
        try {
            this.stop();
        }
        catch (Exception e) {
            throw new PluginRuntimeException("An error was caught when plugin are stopping...", e);
        }

        OresPlugin.getOresLogger().info(String.format("Active version: v%s - Author: %s",
                getDescription().getVersion(),
                getDescription().getAuthors()));
    }

    public abstract void load();

    public abstract void start(@NonNull ComponentHandler componentHandler);

    public abstract void stop();

    public void registerInjectable(@NonNull Object object) {
        this.injector.registerInjectable(object);

        OresPlugin.getOresLogger().info(
                new OresLogger.Loader()
                        .type("Added injectable object")
                        .name(object.getClass().getSimpleName())
                        .build()
        );
    }

    public <T> T createInstance(@NonNull Class<T> type) {
        return this.injector.createInstance(type);
    }

    public <T> Optional<T> getInject(@NonNull String name, @NonNull Class<T> value) {
        return this.injector.get(name, value);
    }

    public <T> Optional<T> getInject(@NonNull Class<T> value) {
        return this.getInject("", value);
    }

}
