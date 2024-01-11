package cc.dreamcode.automessage.command;

import cc.dreamcode.command.annotations.RequiredPermission;
import cc.dreamcode.command.bukkit.BukkitCommand;
import cc.dreamcode.automessage.config.MessageConfig;
import cc.dreamcode.automessage.config.PluginConfig;
import cc.dreamcode.utilities.TimeUtil;
import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.configs.exception.OkaeriException;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredPermission
public class AutoMsgCommand extends BukkitCommand {

    private @Inject MessageConfig messageConfig;
    private @Inject PluginConfig pluginConfig;

    public AutoMsgCommand() {
        super("automsg", "am");
    }

    @Override
    public void content(@NonNull CommandSender sender, @NonNull String[] args) {
        final long time = System.currentTimeMillis();
        if (args.length != 0) {
            if (args[0].equals("reload")) {
                reloadConfig(sender, time, false);
                return;
            }

            if (args[0].equals("time")) {
                if (args.length < 2) {
                    this.messageConfig.usage.send(sender, new MapBuilder<String, Object>()
                            .put("usage", "/automsg (reload, time) <czas>")
                            .build());
                    return;
                }

                try {
                    this.pluginConfig.msgInterval = Duration.parse("PT" + args[1]);

                    this.messageConfig.timeUpdated.send(sender, new MapBuilder<String, Object>()
                            .put("time", args[1])
                            .build());

                    reloadConfig(sender, time, true);
                }
                catch (Exception e) {
                    this.messageConfig.notNumber.send(sender);
                }
            }

            return;
        }

        this.messageConfig.usage.send(sender, new MapBuilder<String, Object>()
                .put("usage", "/automsg (reload, time) <czas>")
                .build());
    }

    @Override
    public List<String> tab(@NonNull Player player, @NonNull String[] args) {
        if(args.length == 1) {
            return new ArrayList<>(Arrays.asList("reload", "time"));
        }
        return null;
    }
    public void reloadConfig(CommandSender sender, long time, boolean shouldSave) {
        try {
            if (shouldSave) {
                this.pluginConfig.save();
            }
            this.messageConfig.load();
            this.pluginConfig.load();

            this.messageConfig.reloaded.send(sender, new MapBuilder<String, Object>()
                    .put("time", TimeUtil.convertMills(System.currentTimeMillis() - time))
                    .build());
        }
        catch (NullPointerException | OkaeriException e) {
            e.printStackTrace();

            this.messageConfig.reloadError.send(sender, new MapBuilder<String, Object>()
                    .put("error", e.getMessage())
                    .build());
        }
    }

}