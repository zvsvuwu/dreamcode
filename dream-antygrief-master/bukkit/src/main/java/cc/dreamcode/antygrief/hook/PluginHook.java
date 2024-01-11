package cc.dreamcode.antygrief.hook;

public interface PluginHook {

    PluginHookType getPluginHookType();

    interface Initializer {
        void onInitialize();
    }

}
