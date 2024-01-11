package cc.dreamcode.spawn.hook;

import cc.dreamcode.spawn.hook.worldguard.WorldGuardHook;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PluginHookType {
    WORLD_GUARD("WorldGuard", "com.sk89q.worldguard.WorldGuard", WorldGuardHook.class);

    private final String name;
    private final String classPackageName;
    private final Class<? extends PluginHook> pluginHookClass;
}
