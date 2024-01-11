package cc.dreamcode.nagroda.content;

import cc.dreamcode.nagroda.NagrodaLogger;
import cc.dreamcode.nagroda.config.MessageConfig;
import cc.dreamcode.nagroda.config.PluginConfig;
import com.avaje.ebean.validation.Range;
import eu.okaeri.injector.annotation.Inject;
import lombok.Getter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.exceptions.ErrorHandler;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.requests.ErrorResponse;

import java.awt.*;
import java.util.*;

public class NagrodaManager {

    private @Inject PluginConfig pluginConfig;
    private @Inject MessageConfig messageConfig;
    private @Inject NagrodaLogger nagrodaLogger;

    @Getter private JDA jda;

    public final HashMap<UUID, Integer> codes = new HashMap<>();
    public final ArrayList<Integer> usedCodes = new ArrayList<>();

    public void createCode(UUID uuid) {
        int random = new Random().nextInt(999);
        while(usedCodes.contains(random)) {
            random = new Random().nextInt(999);
        }
        usedCodes.add(random);
        codes.put(uuid, random);
    }

    public <K, V> K getKey(Map<K, V> map, V value) {
        for (K key : map.keySet()) {
            if (value.equals(map.get(key))) return key;
        }
        return null;
    }

    public void initializeBot() {
        final NagrodaConfig nagrodaConfig = this.pluginConfig.nagrodaConfig;
        try {
            this.jda = JDABuilder
                    .createDefault(nagrodaConfig.botToken)
                    .setActivity(Activity.of(nagrodaConfig.activityType, nagrodaConfig.status))
                    .build()
                    .awaitReady();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        TextChannel channel = this.jda.getTextChannelById(nagrodaConfig.channelId);

        Button button = Button.of(
                nagrodaConfig.buttonStyle, "reward-button",
                nagrodaConfig.buttonLabel);

        if (channel == null) {
            nagrodaLogger.warning(this.messageConfig.incorrectChannelId);
            return;
        }

        MessageHistory history = MessageHistory.getHistoryFromBeginning(channel).complete();
        history.getRetrievedHistory().forEach(message -> message.delete().queue(null,
                new ErrorHandler().ignore(Arrays.asList(ErrorResponse.values()))));

        if (nagrodaConfig.useEmbed) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(nagrodaConfig.embedTitle);
            embedBuilder.setDescription(nagrodaConfig.embedDescription);
            embedBuilder.setColor(new Color(nagrodaConfig.embedRed,
                    nagrodaConfig.embedGreen,
                    nagrodaConfig.embedBlue));

            if (nagrodaConfig.useImage) {
                embedBuilder.setImage(nagrodaConfig.imageUrl);
            }

            if (nagrodaConfig.useThumbnail) {
                embedBuilder.setThumbnail(nagrodaConfig.thumbnailUrl);
            }

            if (nagrodaConfig.useFooter) {
                if (nagrodaConfig.useFooterText && nagrodaConfig.useFooterIcon) {
                    embedBuilder.setFooter(nagrodaConfig.footerText,
                            nagrodaConfig.footerIconUrl);
                }

                if (nagrodaConfig.useFooterText && !nagrodaConfig.useFooterIcon) {
                    embedBuilder.setFooter(nagrodaConfig.footerText);
                }
            }
            channel.sendMessageEmbeds(embedBuilder.build()).addActionRow(button).queue(null,
                    new ErrorHandler().ignore(Arrays.asList(ErrorResponse.values())));
            return;
        }
        channel.sendMessage(nagrodaConfig.panelMessage).addActionRow(button).queue(null,
                new ErrorHandler().ignore(Arrays.asList(ErrorResponse.values())));
    }
}
