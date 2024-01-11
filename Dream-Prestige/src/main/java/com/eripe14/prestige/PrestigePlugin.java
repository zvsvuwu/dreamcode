package com.eripe14.prestige;

import com.eripe14.prestige.commands.CommandMap;
import com.eripe14.prestige.config.CommandsConfig;
import com.eripe14.prestige.config.ConfigLoader;
import com.eripe14.prestige.config.MessageConfig;
import com.eripe14.prestige.config.PluginConfig;
import com.eripe14.prestige.config.subconfig.PrestigeConfig;
import com.eripe14.prestige.features.user.UserManager;
import com.eripe14.prestige.hook.PlaceholderApiExtension;
import com.eripe14.prestige.tasks.TaskMap;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import eu.okaeri.persistence.PersistenceCollection;
import eu.okaeri.persistence.document.DocumentPersistence;
import eu.okaeri.persistence.flat.FlatPersistence;
import eu.okaeri.persistence.repository.RepositoryDeclaration;
import lombok.Getter;
import net.dzikoysk.funnycommands.FunnyCommands;
import net.dzikoysk.funnyguilds.FunnyGuilds;
import org.bukkit.plugin.java.annotation.dependency.SoftDependency;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;
import org.panda_lang.utilities.inject.DependencyInjection;
import xyz.ravis96.dreambasis.bukkit.DreamSpigotPlugin;

import java.io.File;

@Plugin(name = "Dream-Prestige", version = "1.0.0")
@Author("eripe14")
@SoftDependency("FunnyGuilds")
@ApiVersion(ApiVersion.Target.v1_13)
@Getter
public class PrestigePlugin extends DreamSpigotPlugin {

    private final String pluginName = "Dream-Prestige";
    private final String pluginVersion = "1.0.0";

    private PluginConfig pluginConfig;
    private MessageConfig messageConfig;
    private CommandsConfig commandsConfig;

    private FunnyCommands funnyCommands;
    private TaskMap taskMap;

    private PersistenceCollection userCollection;

    private UserManager userManager;

    private DocumentPersistence persistence;

    private FunnyGuilds funnyGuilds;

    private final File dataPath = new File(this.getDataFolder(), "data");
    private final File commandsPath = new File(this.getDataFolder(), "commands.yml");

    @Override
    public void load() {
        this.messageConfig = new ConfigLoader(this.getMessagePath()).loadMessageConfig();
        this.commandsConfig = new ConfigLoader(this.getCommandsPath()).loadCommandsConfig();
    }

    @Override
    public void start() {
        this.pluginConfig = new ConfigLoader(this.getConfigPath()).loadPluginConfig();

        this.userCollection = PersistenceCollection.of(UserManager.class);
        this.userCollection.autofixIndexes(false);

        this.persistence = new DocumentPersistence(new FlatPersistence(
                dataPath, ".yml"), YamlBukkitConfigurer::new, new SerdesBukkit());
        this.persistence.registerCollection(this.userCollection);

        this.userManager = RepositoryDeclaration.of(UserManager.class)
                .newProxy(this.persistence, this.userCollection, PrestigePlugin.class.getClassLoader());

        this.funnyGuilds = FunnyGuilds.getInstance();

        this.setInjector(DependencyInjection.createInjector(resources -> {
            resources.on(PrestigePlugin.class).assignInstance(this);
            resources.on(MessageConfig.class).assignInstance(messageConfig);
            resources.on(PrestigeConfig.class).assignInstance(pluginConfig.getPrestigeConfig());
            resources.on(UserManager.class).assignInstance(userManager);
            resources.on(FunnyGuilds.class).assignInstance(funnyGuilds);
        }));

        this.funnyCommands = new CommandMap(this).init( );
        this.taskMap = new TaskMap(this);

        new PlaceholderApiExtension(userManager).register();
    }

    @Override
    public void end() {
        if(this.funnyCommands != null) {
            this.funnyCommands.dispose();
        }
    }

    @Override
    public void error() {
        if (this.funnyCommands != null) {
            this.funnyCommands.dispose( );
        }
    }

}
