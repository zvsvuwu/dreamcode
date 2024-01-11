package cc.dreamcode.dropsmp.features.hook;

public interface PluginHook {

    PluginHookType getPluginHookType();

    boolean isInitialized();

    void tryInit();

}
