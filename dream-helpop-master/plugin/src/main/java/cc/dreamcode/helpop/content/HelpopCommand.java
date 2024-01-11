package cc.dreamcode.helpop.content;

import cc.dreamcode.helpop.HelpopPlugin;
import cc.dreamcode.helpop.builder.ListBuilder;
import cc.dreamcode.helpop.builder.MapBuilder;
import cc.dreamcode.helpop.command.CommandHandler;
import cc.dreamcode.helpop.command.annotations.RequiredPermission;
import cc.dreamcode.helpop.command.annotations.RequiredPlayer;
import cc.dreamcode.helpop.config.MessageConfig;
import cc.dreamcode.helpop.config.PluginConfig;
import cc.dreamcode.helpop.utilities.TimeUtil;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.exceptions.ErrorHandler;
import net.dv8tion.jda.api.requests.ErrorResponse;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredPlayer
@RequiredPermission(permission = "dreamhelpop.helpop")
public class HelpopCommand extends CommandHandler {
    private @Inject PluginConfig pluginConfig;
    private @Inject MessageConfig messageConfig;
    private @Inject HelpopManager helpopManager;

    public HelpopCommand() {
        super("helpop", null);
    }

    @Override
    public void handle(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        Player p = (Player) sender;
        String msg = String.join(" ", args);

        if (args.length == 0) {
            send(messageConfig.usage, p, new MapBuilder<String, Object>()
                    .put("usage", "/helpop <tresc/on/off>")
                    .build());
            return;
        }
        if (args[0].equals("on")) {
            if (!p.hasPermission("dreamhelpop.helpop.on")) {
                send(messageConfig.noPermission, p);
                return;
            }
            if (helpopManager.getHelpopList().contains(p.getUniqueId())) {
                send(messageConfig.alreadyOn, p);
                return;
            }
            helpopManager.getHelpopList().add(p.getUniqueId());
            send(messageConfig.helpopOn, p);
            return;
        }

        if (args[0].equals("off")) {
            if (!p.hasPermission("dreamhelpop.helpop.off")) {
                send(messageConfig.noPermission, p);
                return;
            }
            if (!helpopManager.getHelpopList().contains(p.getUniqueId())) {
                send(messageConfig.alreadyOff, p);
                return;
            }
            helpopManager.getHelpopList().remove(p.getUniqueId());
            send(messageConfig.helpopOff, p);
            return;
        }

        if (helpopManager.getPlayersOnCooldown().containsKey(p.getUniqueId())) {
            long timeElapsed = System.currentTimeMillis() - helpopManager.getPlayersOnCooldown().get(p.getUniqueId());
            long timeLeft = pluginConfig.helpopCooldown * 1000L - timeElapsed;
            if (timeElapsed < pluginConfig.helpopCooldown * 1000L) {
                send(messageConfig.playerIsOnCooldown, p, new MapBuilder<String, Object>()
                        .put("time", TimeUtil.convertMills(timeLeft))
                        .build());
                return;
            }
            helpopManager.getPlayersOnCooldown().remove(p.getUniqueId());
        }

        helpopManager.getPlayersOnCooldown().put(p.getUniqueId(), System.currentTimeMillis());

        helpopManager.getHelpopList().forEach(uuid -> {
            Player p1 = HelpopPlugin.getHelpopPlugin().getServer().getPlayer(uuid);
            send(messageConfig.helpopNotice, p1, new MapBuilder<String, Object>()
                    .put("gracz", p.getName())
                    .put("tresc", msg)
                    .build());
        });

        send(messageConfig.helpopSuccess, p);

        if (!pluginConfig.useBot) return;

        TextChannel channel = helpopManager.getJda().getTextChannelById(pluginConfig.channelId);

        if (channel == null) {
            HelpopPlugin.getHelpopLogger().error(messageConfig.incorrectChannelId);
        }

        if (pluginConfig.useEmbed) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(pluginConfig.embedTitle);
            embedBuilder.setDescription(pluginConfig.embedDescription
                    .replace("{gracz}", p.getName())
                    .replace("{tresc}", msg));
            embedBuilder.setColor(new Color(pluginConfig.embedRed,
                    pluginConfig.embedGreen,
                    pluginConfig.embedBlue));

            if (pluginConfig.useImage) {
                embedBuilder.setImage(pluginConfig.imageUrl);
            }

            if (pluginConfig.useThumbnail) {
                embedBuilder.setThumbnail(pluginConfig.thumbnailUrl);
            }

            if (pluginConfig.useFooter) {
                if (pluginConfig.useFooterText && pluginConfig.useFooterIcon) {
                    embedBuilder.setFooter(pluginConfig.footerText,
                            pluginConfig.footerIconUrl);
                }

                if (pluginConfig.useFooterText && !pluginConfig.useFooterIcon) {
                    embedBuilder.setFooter(pluginConfig.footerText);
                }
            }

            channel.sendMessageEmbeds(embedBuilder.build()).queue();
            return;
        }

        channel.sendMessage(pluginConfig.helpopMessage).queue();
    }

    @Override
    public List<String> tab(@NonNull Player player, @NotNull @NonNull String[] args) {
        if (args.length == 1
                && player.hasPermission("dreamhelpop.helpop.on")
                && player.hasPermission("dreamhelpop.helpop.off")) {
            List<String> arguments = new ArrayList<>();
            arguments.add("on");
            arguments.add("off");
            return arguments;
        }
        return null;
    }
}
