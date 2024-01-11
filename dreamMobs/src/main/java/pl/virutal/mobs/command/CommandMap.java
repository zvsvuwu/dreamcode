package pl.virutal.mobs.command;

import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;
import pl.virutal.mobs.command.use.MobGuiCMD;
import pl.virutal.mobs.command.use.MobReloadCMD;
import pl.virutal.mobs.main.Main;

import java.lang.reflect.Field;
import java.util.stream.Stream;

public class CommandMap {

    private final Main plugin;
    private SimpleCommandMap scm;

    public CommandMap(Main plugin) {
        this.plugin = plugin;

        setupSimpleCommandMap();
        Stream.of(
                new MobReloadCMD("mobreload", null),
                new MobGuiCMD("mobgui", null)
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
