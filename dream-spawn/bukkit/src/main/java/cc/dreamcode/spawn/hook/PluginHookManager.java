package cc.dreamcode.spawn.hook;

import cc.dreamcode.platform.DreamLogger;
import cc.dreamcode.platform.bukkit.exception.BukkitPluginException;
import cc.dreamcode.spawn.SpawnPlugin;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PluginHookManager {

    @Inject SpawnPlugin spawnPlugin;

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
            }
            catch (ClassNotFoundException e) {
                this.spawnPlugin.getDreamLogger().warning(pluginHookType.getName() + " not found! The plugin works with limitations.");
                return;
            }

            try {
                if (!this.spawnPlugin.getDescription().getSoftDepend().contains(pluginHookType.getName())) {
                    throw new BukkitPluginException("Plugin (name=" + pluginHookType.getName() + ") is not a soft-depend. Add plugin name to plugin.yml");
                }

                final long time = System.currentTimeMillis();

                final PluginHook pluginHook = this.spawnPlugin.createInstance(pluginHookType.getPluginHookClass());
                if (!pluginHook.getPluginHookType().equals(pluginHookType)) {
                    throw new BukkitPluginException(pluginHookType + " has different hook-type. (enum-class)");
                }

                if (pluginHook instanceof PluginHook.Initializer) {
                    ((PluginHook.Initializer) pluginHook).onLoad();
                }

                this.pluginHookMap.put(pluginHookType, pluginHook);

                this.spawnPlugin.getDreamLogger().info(
                        new DreamLogger.Builder()
                                .type("Added plugin soft-depend")
                                .name(pluginHookType.getName())
                                .took(System.currentTimeMillis() - time)
                                .build()
                );
            }
            catch (Exception e) {
                throw new BukkitPluginException("Cannot register soft-depend plugin " + pluginHookType.getName(), e);
            }
        }
    }

}
