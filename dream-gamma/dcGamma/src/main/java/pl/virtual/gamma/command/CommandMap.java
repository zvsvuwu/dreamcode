package pl.virtual.gamma.command;

import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;
import pl.virtual.gamma.command.use.GammaCMD;
import pl.virtual.gamma.command.use.GammaReloadCMD;
import pl.virtual.gamma.main.Main;

import java.lang.reflect.Field;
import java.util.stream.Stream;

public class CommandMap {

    private final Main plugin;
    private SimpleCommandMap scm;

    public CommandMap(Main plugin) {
        this.plugin = plugin;

        setupSimpleCommandMap();
        Stream.of(
                new GammaCMD("gamma", null),
                new GammaReloadCMD("gammareload", null)
        ).forEach(this::registerCommands);
    }

    private void registerCommands(CommandUse cmd) {
        scm.register(this.plugin.getDescription().getName(), cmd);
    }

    private void setupSimpleCommandMap() {
        SimplePluginManager spm = (SimplePluginManager) this.plugin.getServer().getPluginManager();
        Field f = null;
        try {
            f = SimplePluginManager.class.getDeclaredField("commandMap");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert f != null;
        f.setAccessible(true);
        try {
            scm = (SimpleCommandMap) f.get(spm);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
