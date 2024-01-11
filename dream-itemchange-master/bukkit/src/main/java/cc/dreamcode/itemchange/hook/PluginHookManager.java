package cc.dreamcode.itemchange.hook;

import cc.dreamcode.itemchange.ItemChangePlugin;
import cc.dreamcode.platform.DreamLogger;
import cc.dreamcode.platform.bukkit.exception.BukkitPluginException;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PluginHookManager {

    private @Inject ItemChangePlugin itemChangePlugin;

    private final Map<PluginHookType, PluginHook> pluginHookMap = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T extends PluginHook> Optional<T> get(@NonNull Class<T> pluginHookClass) {
        return (Optional<T>) this.pluginHookMap.entrySet()
                .stream()
                .filter(entry -> entry.getKey().getPluginHookClass().isAssignableFrom(pluginHookClass))
                .map(Map.Entry::getValue)
                .findFirst();
    }

    public void registerHooks() {
        for (PluginHookType pluginHookType : PluginHookType.values()) {
            try {
                Class.forName(pluginHookType.getClassPackageName());
            } catch (ClassNotFoundException e) {
                this.itemChangePlugin.getDreamLogger().warning(pluginHookType.getName() + " not found! The plugin works with limitations.");
                return;
            }

            try {
                if (!this.itemChangePlugin.getDescription().getSoftDepend().contains(pluginHookType.getName())) {
                    throw new BukkitPluginException("Plugin (name=" + pluginHookType.getName() + ") is not a soft-depend. Add plugin name to plugin.yml");
                }

                final long time = System.currentTimeMillis();

                final PluginHook pluginHook = this.itemChangePlugin.createInstance(pluginHookType.getPluginHookClass());
                if (!pluginHook.getPluginHookType().equals(pluginHookType)) {
                    throw new BukkitPluginException(pluginHookType + " has different hook-type. (enum-class)");
                }

                if (pluginHook instanceof PluginHook.Initializer) {
                    ((PluginHook.Initializer) pluginHook).onInitialize();
                }

                this.pluginHookMap.put(pluginHookType, pluginHook);

                this.itemChangePlugin.getDreamLogger().info(
                        new DreamLogger.Builder()
                                .type("Added plugin soft-depend")
                                .name(pluginHookType.getName())
                                .took(System.currentTimeMillis() - time)
                                .build()
                );
            } catch (Exception e) {
                throw new BukkitPluginException("Cannot register soft-depend plugin " + pluginHookType.getName(), e);
            }
        }
    }

}
