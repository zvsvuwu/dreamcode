package cc.dreamcode.itemchange.hook;

public interface PluginHook {

    PluginHookType getPluginHookType();

    interface Initializer {
        void onInitialize();
    }

}
