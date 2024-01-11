package cc.dreamcode.chatmanager;

import cc.dreamcode.chatmanager.content.ChatManagerCommand;
import cc.dreamcode.chatmanager.content.ChatManagerEventsListener;
import cc.dreamcode.chatmanager.boot.PluginBootLoader;
import cc.dreamcode.chatmanager.component.ComponentHandler;
import cc.dreamcode.chatmanager.config.MessageConfig;
import cc.dreamcode.chatmanager.config.PluginConfig;
import cc.dreamcode.chatmanager.content.ChatManager;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.annotation.permission.Permission;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.Website;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

@Plugin(name = "Dream-ChatManager", version = "1.0")
@Author("torobolin")
@Description("ChatManager plugin by DreamCode.")
@Website("DreamCode - https://discord.gg/dreamcode")
@ApiVersion(ApiVersion.Target.v1_13)
@Permission(name = "dreamcm.admin", defaultValue = PermissionDefault.OP)

public final class ChatManagerPlugin extends PluginBootLoader {

    @Getter private static ChatManagerPlugin chatManagerPlugin;
    @Getter private static ChatManagerLogger chatManagerLogger;

    @Override
    public void load() {
        chatManagerPlugin = this;
        chatManagerLogger = new ChatManagerLogger(chatManagerPlugin.getLogger());
    }

    @Override
    public void start(@NonNull ComponentHandler componentHandler) {
        componentHandler.registerComponent(PluginConfig.class);
        componentHandler.registerComponent(MessageConfig.class);
        componentHandler.registerComponent(ChatManager.class);
        componentHandler.registerComponent(ChatManagerEventsListener.class);
        componentHandler.registerComponent(ChatManagerCommand.class);
    }

    @Override
    public void stop() {
    }
}
