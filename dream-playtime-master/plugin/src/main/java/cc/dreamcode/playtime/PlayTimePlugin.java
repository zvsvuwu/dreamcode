package cc.dreamcode.playtime;

import cc.dreamcode.command.bukkit.BukkitCommandProvider;
import cc.dreamcode.menu.bukkit.BukkitMenuProvider;
import cc.dreamcode.menu.serdes.bukkit.okaeri.MenuBuilderSerdes;
import cc.dreamcode.notice.bukkit.BukkitNoticeProvider;
import cc.dreamcode.notice.bukkit.okaeri_serdes.BukkitNoticeSerdes;
import cc.dreamcode.platform.bukkit.DreamBukkitPlatform;
import cc.dreamcode.platform.bukkit.component.CommandComponentClassResolver;
import cc.dreamcode.platform.bukkit.component.ConfigurationComponentClassResolver;
import cc.dreamcode.platform.bukkit.component.ListenerComponentClassResolver;
import cc.dreamcode.platform.bukkit.component.RunnableComponentClassResolver;
import cc.dreamcode.platform.component.ComponentManager;
import cc.dreamcode.playtime.commands.PlayTimeCommand;
import cc.dreamcode.playtime.config.MessageConfig;
import cc.dreamcode.playtime.config.PluginConfig;
import cc.dreamcode.playtime.menus.PlayTimeMenu;
import cc.dreamcode.playtime.nms.NmsFactory;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.Website;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

@Plugin(name = "Dream-PlayTime", version = "1.0")
@Author("torobolin")
@Description("PlayTime plugin by DreamCode.")
@Website("DreamCode - https://discord.gg/dreamcode")
@ApiVersion(ApiVersion.Target.v1_13)
public final class PlayTimePlugin extends DreamBukkitPlatform {

    @Getter private static PlayTimePlugin playTimePlugin;

    @Override
    public void load(@NonNull ComponentManager componentManager) {
        playTimePlugin = this;
    }

    @Override
    public void enable(@NonNull ComponentManager componentManager) {
        this.registerInjectable(NmsFactory.getNmsAccessor());
        this.registerInjectable(BukkitMenuProvider.create(this));
        this.registerInjectable(BukkitNoticeProvider.create(this));
        this.registerInjectable(BukkitCommandProvider.create(this, this.getInjector()));

        componentManager.registerResolver(CommandComponentClassResolver.class);
        componentManager.registerResolver(ListenerComponentClassResolver.class);
        componentManager.registerResolver(RunnableComponentClassResolver.class);

        componentManager.registerResolver(ConfigurationComponentClassResolver.class);
        componentManager.registerComponent(MessageConfig.class);
        componentManager.registerComponent(PluginConfig.class);

        componentManager.registerComponent(PlayTimeMenu.class);
        componentManager.registerComponent(PlayTimeCommand.class);
    }

    @Override
    public void disable() {
        // features need to be call by stop server
    }

    @Override
    public OkaeriSerdesPack getPluginSerdesPack() {
        return registry -> {
            registry.register(new BukkitNoticeSerdes());
            registry.register(new MenuBuilderSerdes());
        };
    }
}
