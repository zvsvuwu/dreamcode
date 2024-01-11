package cc.dreamcode.restarts.features.hook;

import cc.dreamcode.restarts.features.Manager;

import java.util.*;

public class HookManager implements Manager<PluginHookType, PluginHook> {

    private final Map<PluginHookType, PluginHook> pluginHooks = new HashMap<>();

    @Override
    public Optional<PluginHook> getByKey(PluginHookType pluginHookType) {
        return Optional.ofNullable(this.pluginHooks.get(pluginHookType));
    }

    @Override
    public Set<PluginHook> getSet() {
        return new HashSet<>(this.pluginHooks.values());
    }

    @Override
    public PluginHook add(PluginHook pluginHook) {
        return this.pluginHooks.put(pluginHook.getPluginHookType(), pluginHook);
    }

    @Override
    public void remove(PluginHookType pluginHookType) {
        this.pluginHooks.remove(pluginHookType);
    }
}
