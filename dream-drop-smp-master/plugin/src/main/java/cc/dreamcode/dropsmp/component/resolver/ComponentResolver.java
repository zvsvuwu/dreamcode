package cc.dreamcode.dropsmp.component.resolver;

import cc.dreamcode.dropsmp.PluginMain;
import eu.okaeri.injector.Injector;

public interface ComponentResolver<T> {

    void resolve(PluginMain pluginMain, Injector injector, T resolve);

}
