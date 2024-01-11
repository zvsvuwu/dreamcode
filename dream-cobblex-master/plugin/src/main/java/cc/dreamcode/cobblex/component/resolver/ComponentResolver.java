package cc.dreamcode.cobblex.component.resolver;

import cc.dreamcode.cobblex.PluginMain;
import eu.okaeri.injector.Injector;

public interface ComponentResolver<T> {

    void resolve(PluginMain pluginMain, Injector injector, T resolve);

}
