package cc.dreamcode.report.command.report;

import cc.dreamcode.report.ReportPlugin;
import cc.dreamcode.report.command.ArgumentHandler;
import cc.dreamcode.report.command.annotations.RequiredPermission;
import cc.dreamcode.report.config.MessageConfig;
import cc.dreamcode.report.config.PluginConfig;
import cc.dreamcode.report.utilities.TimeUtil;
import com.google.common.collect.ImmutableMap;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@RequiredPermission(permission = "rpl.report.panel")
public class ReloadArgument extends ArgumentHandler {

    private @Inject ReportPlugin reportPlugin;
    private @Inject PluginConfig pluginConfig;
    private @Inject MessageConfig messageConfig;

    public ReloadArgument() {
        super("reload", 1);
    }

    @Override
    public void handle(@NonNull CommandSender sender, @NonNull String[] args) {
        final long time = System.currentTimeMillis();

        this.messageConfig.load();
        this.pluginConfig.load();

        this.send(this.messageConfig.reloaded, sender, new ImmutableMap.Builder<String, Object>()
                .put("time", TimeUtil.convertMills(System.currentTimeMillis() - time))
                .build());
    }

    @Override
    public List<String> tab(@NonNull Player player, @NonNull String[] args) {
        return null;
    }
}
