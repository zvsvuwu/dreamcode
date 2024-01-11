package cc.dreamcode.alerts;

import cc.dreamcode.alerts.content.AlertCommand;
import cc.dreamcode.alerts.boot.PluginBootLoader;
import cc.dreamcode.alerts.component.ComponentHandler;
import cc.dreamcode.alerts.config.MessageConfig;
import cc.dreamcode.alerts.config.PluginConfig;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.Website;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

@Plugin(name = "Dream-Alerts", version = "1.0-SNAPSHOT")
@Author("torobolin")
@Description("Alerts plugin by DreamCode.")
@Website("DreamCode - https://discord.gg/dreamcode")
@ApiVersion(ApiVersion.Target.v1_13)

public final class AlertsPlugin extends PluginBootLoader {

    @Getter private static AlertsPlugin alertsPlugin;
    @Getter private static AlertsLogger alertsLogger;

    @Override
    public void load() {
        alertsPlugin = this;
        alertsLogger = new AlertsLogger(alertsPlugin.getLogger());
    }

    @Override
    public void start(@NonNull ComponentHandler componentHandler) {
        componentHandler.registerComponent(PluginConfig.class);
        componentHandler.registerComponent(MessageConfig.class);
        componentHandler.registerComponent(AlertCommand.class);
    }

    @Override
    public void stop() {
    }
}
