package cc.dreamcode.generator.component.resolver;

import cc.dreamcode.generator.PluginMain;
import eu.okaeri.injector.Injector;

public interface ComponentResolver<T> {

    void resolve(PluginMain pluginMain, Injector injector, T resolve);

}
