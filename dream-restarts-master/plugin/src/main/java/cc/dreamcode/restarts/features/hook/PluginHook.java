package cc.dreamcode.restarts.features.hook;

public interface PluginHook {

    PluginHookType getPluginHookType();

    boolean isInitialized();

    void tryInit();

}
