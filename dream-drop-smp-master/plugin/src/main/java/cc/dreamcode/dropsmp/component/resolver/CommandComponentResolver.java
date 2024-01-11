package cc.dreamcode.dropsmp.component.resolver;

import cc.dreamcode.dropsmp.PluginLogger;
import cc.dreamcode.dropsmp.PluginMain;
import cc.dreamcode.dropsmp.features.command.CommandUse;
import eu.okaeri.injector.Injector;
import org.bukkit.Server;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;

public class CommandComponentResolver implements ComponentResolver<CommandUse> {

    @Override
    public void resolve(PluginMain plugin, Injector injector, CommandUse commandUse) {
        long start = System.currentTimeMillis();

        SimpleCommandMap simpleCommandMap = this.getSimpleCommandMap(plugin.getServer());

        if (simpleCommandMap == null) {
            return;
        }

        simpleCommandMap.register(plugin.getDescription().getName(),
                injector.createInstance(commandUse.getClass()));

        long took = System.currentTimeMillis() - start;
        PluginMain.getPluginLogger().info(
                new PluginLogger.Loader()
                        .type("Poprawnie dodano command")
                        .name(commandUse.getClass().getSimpleName())
                        .took(took)
                        .meta("name", commandUse.getName())
                        .meta("aliases", commandUse.getAliases())
                        .build()
        );
    }

    private SimpleCommandMap getSimpleCommandMap(Server server) {
        SimplePluginManager spm = (SimplePluginManager) server.getPluginManager();

        Field f = null;
        try {
            f = SimplePluginManager.class.getDeclaredField("commandMap");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        assert f != null;
        f.setAccessible(true);

        try {
            return (SimpleCommandMap) f.get(spm);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
