package cc.dreamcode.spawn.hook;

public interface PluginHook {

    PluginHookType getPluginHookType();

    interface Initializer {
        void onLoad();
    }
}
