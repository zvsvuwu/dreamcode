package cc.dreamcode.antygrief.hook;

import cc.dreamcode.antygrief.hook.funnyguilds.FunnyGuildsHook;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PluginHookType {
    FUNNY_GUILDS("FunnyGuilds", "net.dzikoysk.funnyguilds.FunnyGuilds", FunnyGuildsHook.class);

    private final String name;
    private final String classPackageName;
    private final Class<? extends PluginHook> pluginHookClass;
}
