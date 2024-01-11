package cc.dreamcode.helpop.content;

import cc.dreamcode.helpop.config.PluginConfig;
import eu.okaeri.injector.annotation.Inject;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import java.util.*;

public class HelpopManager {
    private @Inject PluginConfig pluginConfig;

    @Getter private JDA jda;

    @Getter private final List<UUID> helpopList = new ArrayList<>();
    @Getter private final Map<UUID, Long> playersOnCooldown = new HashMap<>();

    public void initializeBot() {
        if (!pluginConfig.useBot) return;
        try {
            jda = JDABuilder
                    .createDefault(pluginConfig.botToken)
                    .setActivity(Activity.of(pluginConfig.activityType, pluginConfig.status))
                    .build()
                    .awaitReady();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
