package cc.dreamcode.restarts;

import cc.dreamcode.restarts.boot.PluginBootLoader;
import cc.dreamcode.restarts.component.ComponentHandler;
import cc.dreamcode.restarts.config.MessageConfig;
import cc.dreamcode.restarts.config.PluginConfig;
import cc.dreamcode.restarts.features.hook.HookFactory;
import cc.dreamcode.restarts.features.hook.HookManager;
import cc.dreamcode.restarts.features.hook.plugins.FunnyGuildsHook;
import cc.dreamcode.restarts.features.menu.MenuActionHandler;
import cc.dreamcode.restarts.features.nms.NmsFactory;
import cc.dreamcode.restarts.features.user.UserController;
import cc.dreamcode.restarts.features.user.UserRepository;
import cc.dreamcode.restarts.features.user.UserService;
import eu.okaeri.persistence.document.DocumentPersistence;
import eu.okaeri.tasker.bukkit.BukkitTasker;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.plugin.java.annotation.dependency.SoftDependency;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.Website;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Plugin(name = "Dream-Restarts", version = "1.0-SNAPSHOT")
@Author("Ravis96")
@Description("Restarts plugin by DreamCode.")
@Website("DreamCode - https://discord.gg/dreamcode")
@ApiVersion(ApiVersion.Target.v1_13)

// If are you using plugin-hooks, add them here via soft-dependency as well
@SoftDependency("FunnyGuilds")
public final class RestartPlugin extends PluginBootLoader {

    @Getter private static RestartPlugin restartPlugin;
    @Getter private static RestartLogger restartLogger;

    @Override
    public void load() {
        // Static content for api.
        restartPlugin = this;
        restartLogger = new RestartLogger(restartPlugin.getLogger());
    }

    @Override
    public void start(@NonNull ComponentHandler componentHandler) {
        // Component system inspired by okaeri-platform
        // These simple structure can register all content of this plugin. (A-Z)
        componentHandler.registerObject(BukkitTasker.newPool(this));
        componentHandler.registerObject(NmsFactory.getNmsAccessor());
        componentHandler.registerComponent(PluginConfig.class);
        componentHandler.registerComponent(MessageConfig.class);
        componentHandler.registerComponent(DocumentPersistence.class);
        componentHandler.registerComponent(UserRepository.class);
        componentHandler.registerComponent(UserService.class);
        componentHandler.registerComponent(HookManager.class, hookManager ->
                this.createInstance(HookFactory.class).tryLoadAllDepends(Stream.of(
                        FunnyGuildsHook.class
                ).collect(Collectors.toList()), hookManager));
        componentHandler.registerComponent(UserController.class);
        componentHandler.registerComponent(MenuActionHandler.class);
    }

    @Override
    public void stop() {
        // features need to be call by stop server
    }
}
