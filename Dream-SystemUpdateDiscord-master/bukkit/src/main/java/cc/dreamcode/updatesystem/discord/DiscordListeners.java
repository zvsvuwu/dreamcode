package cc.dreamcode.updatesystem.discord;

import cc.dreamcode.updatesystem.config.PluginConfig;
import cc.dreamcode.updatesystem.message.MessageManager;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DiscordListeners extends ListenerAdapter {

    private final MessageManager messageManager;
    private final PluginConfig pluginConfig;

    public DiscordListeners(MessageManager messageManager, PluginConfig pluginConfig) {
        this.messageManager = messageManager;
        this.pluginConfig = pluginConfig;
    }

    @Override
    public void onMessageDelete(MessageDeleteEvent event) {
        this.messageManager.getMessage(event.getMessageId())
                .ifPresent(message -> this.messageManager.delete(event.getMessageId()));
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }
        if (!this.pluginConfig.channels.containsKey(event.getChannel().getId())) {
            return;
        }
        this.messageManager.create(event.getMessageId(),
                event.getMessage().getContentRaw(),
                event.getChannel().getId(),
                event.getAuthor().getName(),
                event.getAuthor().getId(),
                System.currentTimeMillis());
    }
}
