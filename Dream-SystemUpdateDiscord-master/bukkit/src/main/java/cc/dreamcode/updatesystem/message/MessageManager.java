package cc.dreamcode.updatesystem.message;

import cc.dreamcode.platform.DreamLogger;
import cc.dreamcode.platform.bukkit.exception.BukkitPluginException;
import cc.dreamcode.updatesystem.config.MessageConfig;
import cc.dreamcode.updatesystem.menu.MessageMenu;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class MessageManager {

    @Inject private MessageRepository messageRepository;
    @Inject private Tasker tasker;
    @Inject private DreamLogger dreamLogger;
    @Inject private MessageMenu messageMenu;
    @Inject private MessageConfig messageConfig;

    @Getter private final Map<String, Message> messages = new LinkedHashMap<>();

    public void sync(Consumer<Exception> exceptionConsumer) {
        final long time = System.currentTimeMillis();

        this.tasker.newChain()
                .async(() -> {
                    try {
                        return this.messageRepository.findAll();
                    }
                    catch (Exception exception) {
                        exceptionConsumer.accept(exception);
                        throw new BukkitPluginException("Cannot load message-repository data.", exception);
                    }
                })
                .acceptAsync(messages -> {
                    this.messages.clear();
                    messages.forEach(message -> this.messages.put(message.getId(), message));

                    this.dreamLogger.info(new DreamLogger.Builder()
                            .type("Synchronized database")
                            .name("messages")
                            .meta("size", this.messages.size())
                            .meta("time", System.currentTimeMillis() - time + "ms")
                            .build());
                })
                .sync(() -> this.messageMenu.update(this.messages.values()))
                .execute();
    }

    public void create(String messageId, String msg, String channelId, String authorName, String authorId, long createdAt) {
        this.tasker.newChain()
                .async(() -> {
                    final Message message = this.messageRepository.findOrCreateByPath(messageId);
                    message.setMessage(msg);
                    message.setChannelId(channelId);
                    message.setAuthorName(authorName);
                    message.setAuthorId(authorId);
                    message.setCreatedAt(createdAt);
                    return message;
                })
                .acceptAsync(message -> {
                    message.save();
                })
                .abortIfException(e -> this.dreamLogger.error("Cannot save message to database."))
                .acceptAsync(message -> {
                    this.messages.put(message.getId(), message);
                })
                .sync(() -> {
                    this.messageMenu.update(this.messages.values());
                    Bukkit.getOnlinePlayers().forEach(player -> this.messageConfig.newMessage.send(player));
                })
                .execute();
    }

    public Optional<Message> getMessage(String messageId) {
        return Optional.ofNullable(this.messages.get(messageId));
    }

    public void delete(String messageId) {
        getMessage(messageId).ifPresent(message -> {
            this.messages.remove(messageId);
            this.tasker.newChain()
                    .async(() -> {
                        this.messageRepository.deleteByPath(messageId);
                    })
                    .async(() -> this.messageMenu.update(this.messages.values()))
                    .execute();
        });
    }

}
