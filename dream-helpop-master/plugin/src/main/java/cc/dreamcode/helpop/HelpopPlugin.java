package cc.dreamcode.helpop;

import cc.dreamcode.helpop.boot.PluginBootLoader;
import cc.dreamcode.helpop.component.ComponentHandler;
import cc.dreamcode.helpop.config.MessageConfig;
import cc.dreamcode.helpop.config.PluginConfig;
import cc.dreamcode.helpop.content.HelpopCommand;
import cc.dreamcode.helpop.content.HelpopEventsListener;
import cc.dreamcode.helpop.content.HelpopManager;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.annotation.permission.Permission;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.Website;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

@Plugin(name = "Dream-Helpop", version = "1.1")
@Author("torobolin")
@Description("Helpop plugin by DreamCode.")
@Website("DreamCode - https://discord.gg/dreamcode")
@ApiVersion(ApiVersion.Target.v1_13)
@Permission(name = "dreamhelpop.helpop", defaultValue = PermissionDefault.TRUE)
@Permission(name = "dreamhelpop.helpop.on")
@Permission(name = "dreamhelpop.helpop.off")
public final class HelpopPlugin extends PluginBootLoader {

    @Getter private static HelpopPlugin helpopPlugin;
    @Getter private static HelpopLogger helpopLogger;

    @Override
    public void load() {
        helpopPlugin = this;
        helpopLogger = new HelpopLogger(helpopPlugin.getLogger());
    }

    @Override
    public void start(@NonNull ComponentHandler componentHandler) {
        componentHandler.registerComponent(PluginConfig.class);
        componentHandler.registerComponent(MessageConfig.class);
        componentHandler.registerComponent(HelpopManager.class, HelpopManager::initializeBot);
        componentHandler.registerComponent(HelpopCommand.class);
        componentHandler.registerComponent(HelpopEventsListener.class);
    }

    @Override
    public void stop() {
        this.getInject(HelpopManager.class).ifPresent(helpopManager ->
                helpopManager.getJda().shutdown());
    }
}
