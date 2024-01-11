package cc.dreamcode.ores;

import cc.dreamcode.ores.boot.PluginBootLoader;
import cc.dreamcode.ores.component.ComponentHandler;
import cc.dreamcode.ores.config.MessageConfig;
import cc.dreamcode.ores.config.PluginConfig;
import cc.dreamcode.ores.content.OresCommand;
import cc.dreamcode.ores.content.OresEventListener;
import cc.dreamcode.ores.utilities.InventoryUtils;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.Website;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

@Plugin(name = "Dream-Ores", version = "1.0")
@Author("torobolin")
@Description("Ores plugin by DreamCode.")
@Website("DreamCode - https://discord.gg/dreamcode")
@ApiVersion(ApiVersion.Target.v1_13)

// If are you using plugin-hooks, add them here via soft-dependency as well
public final class OresPlugin extends PluginBootLoader {

    @Getter private static OresPlugin oresPlugin;
    @Getter private static cc.dreamcode.ores.OresLogger oresLogger;

    @Override
    public void load() {
        // Static content for api.
        oresPlugin = this;
        oresLogger = new OresLogger(oresPlugin.getLogger());
    }

    @Override
    public void start(@NonNull ComponentHandler componentHandler) {
        componentHandler.registerComponent(PluginConfig.class);
        componentHandler.registerComponent(MessageConfig.class);
        componentHandler.registerComponent(InventoryUtils.class);
        componentHandler.registerComponent(OresEventListener.class);
        componentHandler.registerComponent(OresCommand.class);
    }

    @Override
    public void stop() {
        // features need to be call by stop server
    }
}
