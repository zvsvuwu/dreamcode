package cc.dreamcode.updatesystem.menu;

import cc.dreamcode.menu.bukkit.base.BukkitMenu;
import cc.dreamcode.menu.bukkit.setup.BukkitMenuSetup;
import cc.dreamcode.menu.serdes.bukkit.BukkitMenuBuilder;
import cc.dreamcode.updatesystem.config.PluginConfig;
import cc.dreamcode.updatesystem.message.Message;
import cc.dreamcode.updatesystem.update.UpdateChannel;
import cc.dreamcode.updatesystem.util.DateUtil;
import cc.dreamcode.updatesystem.util.WordUtil;
import cc.dreamcode.utilities.bukkit.ChatUtil;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.placeholders.context.PlaceholderContext;
import eu.okaeri.placeholders.message.CompiledMessage;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class MessageMenu implements BukkitMenuSetup {

    @Inject private PluginConfig pluginConfig;
    @Getter private BukkitMenu messageMenu;
    @Getter private long lastUpdate;

    public Optional<UpdateChannel> getUpdateChannelById(String id) {
        return Optional.ofNullable(this.pluginConfig.channels.get(id));
    }

    public void update(Collection<Message> msg) {

        List<Message> messages = new ArrayList<>(msg);
        messages.sort((o1, o2) -> Long.compare(o2.getCreatedAt(), o1.getCreatedAt()));

        this.lastUpdate = System.currentTimeMillis();

        if (messageMenu == null) {
            this.messageMenu = build();
        }
        else {
            for (int i = 0; i < this.messageMenu.getInventory().getSize(); i++) {
                if (this.messageMenu.getInventory().getItem(i) == null) {
                    continue;
                }
                if (this.pluginConfig.messageMenu.getItems().containsKey(i)) {
                    continue;
                }
                this.messageMenu.getInventory().setItem(i, null);
            }
        }
        int slot = 0;
        for (Message message : messages) {
            Optional<UpdateChannel> optional = getUpdateChannelById(message.getChannelId());
            if (!optional.isPresent()) {
                continue;
            }
            UpdateChannel updateChannel = optional.get();
            if (slot >= 54) {
                break;
            }
            if (this.messageMenu.getInventory().getItem(slot) == null) {

                String displayName = PlaceholderContext.of(CompiledMessage.of(this.pluginConfig.displayName))
                        .with("categorydisplay", updateChannel.getCategoryDisplay())
                        .with("category", updateChannel.getCategoryName())
                        .with("time", DateUtil.getDateTime(message.getCreatedAt()))
                        .with("by", message.getAuthorName())
                        .with("byid", message.getAuthorId())
                        .apply();

                List<String> messageList = WordUtil.fixWords(message.getMessage());

                List<String> displayLore = new ArrayList<>();
                for (String line : this.pluginConfig.displayLore) {
                    if (line.contains("{message}")) {
                        displayLore.add(ChatUtil.fixColor(PlaceholderContext.of(CompiledMessage.of(line))
                                .with("message", (messageList.isEmpty() ? "" : messageList.get(0)))
                                .apply()));
                        if (messageList.size() > 1) {
                            for (int i = 1; i < messageList.size(); i++) {
                                displayLore.add(ChatUtil.fixColor(this.pluginConfig.defaultPrefixPreMessageLine + messageList.get(i)));
                            }
                        }
                    }
                    else {
                        displayLore.add(ChatUtil.fixColor(
                           PlaceholderContext.of(CompiledMessage.of(line))
                                   .with("categorydisplay", updateChannel.getCategoryDisplay())
                                   .with("category", updateChannel.getCategoryName())
                                   .with("time", DateUtil.getDateTime(message.getCreatedAt()))
                                   .with("by", message.getAuthorName())
                                   .with("byid", message.getAuthorId())
                                   .apply()
                        ));
                    }
                }

                this.messageMenu.setItem(slot, new ItemBuilder(updateChannel.getMaterial().parseMaterial())
                        .setName(ChatUtil.fixColor(displayName))
                        .setLore(displayLore)
                        .toItemStack());
            }
            slot++;
        }
    }

    @Override
    public BukkitMenu build() {
        final BukkitMenuBuilder messageMenuBuilder = this.pluginConfig.messageMenu;
        return messageMenuBuilder.build();
    }

}
