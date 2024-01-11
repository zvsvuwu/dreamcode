package cc.dreamcode.dropsmp.features.hook;

import cc.dreamcode.dropsmp.exception.PluginRuntimeException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractPluginHook implements PluginHook {

    private boolean initialized = false;

    @Override
    public void tryInit() {
        try {
            this.callInit();
            this.initialized = true;
        }
        catch (Exception e) {
            this.initialized = false;
            throw new PluginRuntimeException("Plugin (name=" + this.getPluginHookType().getName() + ") " +
                    "posiada blad i nie mozna go zaladowac.", e);
        }
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    public abstract void callInit();
}
