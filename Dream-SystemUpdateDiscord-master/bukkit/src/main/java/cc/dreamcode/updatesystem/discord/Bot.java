package cc.dreamcode.updatesystem.discord;

import cc.dreamcode.platform.DreamLogger;
import cc.dreamcode.updatesystem.config.PluginConfig;
import cc.dreamcode.updatesystem.message.MessageManager;
import eu.okaeri.injector.annotation.Inject;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Bot {

    @Inject private PluginConfig pluginConfig;
    @Inject private MessageManager messageManager;
    @Inject private DreamLogger dreamLogger;

    @Getter private JDA jda;

    public void init() {
        JDABuilder jdaBuilder = JDABuilder.createDefault(this.pluginConfig.botToken);
        jdaBuilder.enableIntents(GatewayIntent.MESSAGE_CONTENT);
        jdaBuilder.addEventListeners(new DiscordListeners(this.messageManager, this.pluginConfig));
        jdaBuilder.setActivity(Activity.of(this.pluginConfig.activityType, this.pluginConfig.botStatus));

        try {
            this.jda = jdaBuilder.build();
        }
        catch (IllegalArgumentException e){
            this.dreamLogger.error("Token bota podany w pliku konfiguracyjnym jest nieprawidlowy!", e);
        }

    }

}
