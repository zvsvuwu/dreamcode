package xyz.ravis96.dreamfreeze.features.plugin;

import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.file.YamlConfiguration;
import xyz.ravis96.dreamfreeze.PluginMain;
import xyz.ravis96.dreamfreeze.features.IManagerLoader;

import java.io.File;

@RequiredArgsConstructor
public class PluginLoader implements IManagerLoader {

    private final PluginMain plugin;
    private final File file;

    @Override
    public void load() {
        final long time = System.currentTimeMillis();
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(this.file);

        final PluginStorage pluginStorage = new PluginStorage();
        if(yaml.contains("freeze")) {
            pluginStorage.setFreeze(yaml.getBoolean("freeze"));
        }

        this.plugin.setPluginStorage(pluginStorage);
        PluginMain.getPluginLogger().get().info("Finished loading: PluginStorage. " +
                "(" + (System.currentTimeMillis() - time) + "ms)");
    }

    @Override
    public void save() {
        final long time = System.currentTimeMillis();
        final PluginStorage pluginStorage = this.plugin.getPluginStorage();
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(this.file);

        yaml.set("freeze", pluginStorage.isFreeze());
        try {
            yaml.save(this.file);
            PluginMain.getPluginLogger().get().info("Finished saving: PluginStorage. (" + (System.currentTimeMillis() - time) + "ms)");
        } catch (Exception e) {
            PluginMain.getPluginLogger().get().error("Wystapil problem z zapisem PluginStorage. Plugin zaladuje ostatni zapis.", e);
        }
    }
}
