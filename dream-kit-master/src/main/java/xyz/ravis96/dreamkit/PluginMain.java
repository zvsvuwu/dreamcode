package xyz.ravis96.dreamkit;

import eu.okaeri.persistence.PersistenceCollection;
import eu.okaeri.persistence.document.DocumentPersistence;
import eu.okaeri.persistence.repository.RepositoryDeclaration;
import eu.okaeri.tasker.bukkit.BukkitTasker;
import eu.okaeri.tasker.core.Tasker;
import lombok.Getter;
import lombok.Setter;
import net.dzikoysk.funnycommands.FunnyCommands;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.Website;
import org.bukkit.plugin.java.annotation.plugin.author.Author;
import org.panda_lang.utilities.inject.DependencyInjection;
import org.panda_lang.utilities.inject.Injector;
import xyz.ravis96.dreamkit.commands.CommandInitializer;
import xyz.ravis96.dreamkit.config.ConfigLoader;
import xyz.ravis96.dreamkit.config.MessageConfig;
import xyz.ravis96.dreamkit.config.PluginConfig;
import xyz.ravis96.dreamkit.features.persistence.Persistence;
import xyz.ravis96.dreamkit.features.plugin.PluginLoader;
import xyz.ravis96.dreamkit.features.plugin.PluginStorage;
import xyz.ravis96.dreamkit.features.user.UserManager;
import xyz.ravis96.dreamkit.listeners.ListenerMap;
import xyz.ravis96.dreamkit.nms.NmsAccessor;
import xyz.ravis96.dreamkit.tasks.TaskMap;
import xyz.ravis96.dreamkit.utils.optional.NullClass;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

@Plugin(name = "Dream-Kit", version = "1.1-BETA")
@Author("Ravis96")
@Description("Plugin na zestawy.")
@Website("DreamCode - https://discord.gg/G8aFUSyfFh")
@ApiVersion(ApiVersion.Target.v1_13)
public final class PluginMain extends JavaPlugin {

    @Getter private final String pluginName = "Dream-Kit";
    @Getter private final String pluginVersion = "1.1-BETA";

    @Getter private static NullClass<PluginMain> pluginMain = new NullClass<>();
    @Getter private static NullClass<PluginLogger> pluginLogger = new NullClass<>();

    @Getter private PluginConfig pluginConfig;
    @Getter private MessageConfig messageConfig;

    @Getter private DocumentPersistence persistence;

    @Getter private PersistenceCollection userCollection;
    @Getter private UserManager userManager;

    @Getter @Setter private PluginStorage pluginStorage;

    @Getter private Tasker tasker;
    @Getter private Injector injector;

    @Getter private NmsAccessor nmsAccessor;

    @Getter private FunnyCommands funnyCommands;
    @Getter private ListenerMap listenerMap;
    @Getter private TaskMap taskMap;

    private final File configPath = new File(this.getDataFolder(), "config.yml");
    private final File messagePath = new File(this.getDataFolder(), "message.yml");
    private final File dataPath = new File(this.getDataFolder(), "data");
    private final File storagePath = new File(this.dataPath, "storage.yml");

    @Getter private final AtomicBoolean pluginEnabled = new AtomicBoolean(true);

    @Override
    public void onEnable() {
        final long pluginStart = System.currentTimeMillis();
        pluginLogger = new NullClass<>(new PluginLogger(this.getLogger()));
        pluginMain = new NullClass<>(this);

        try {
            this.pluginConfig = new ConfigLoader(this.configPath).loadPluginConfig();
            this.messageConfig = new ConfigLoader(this.messagePath).loadMessageConfig();

            this.userCollection = PersistenceCollection.of(UserManager.class);
            this.userCollection.autofixIndexes(false);

            this.persistence = new Persistence(this).getDocumentPersistence(this.dataPath);
            this.persistence.registerCollection(this.userCollection);

            this.userManager = RepositoryDeclaration.of(UserManager.class)
                    .newProxy(this.persistence, this.userCollection, PluginMain.class.getClassLoader());

            new PluginLoader(this, this.storagePath).load();

            this.tasker = BukkitTasker.newPool(this);

            this.nmsAccessor = new NmsAccessor();

            this.injector = DependencyInjection.createInjector(resources -> {
                resources.on(PluginMain.class).assignInstance(this);
                resources.on(PluginLogger.class).assignInstance(PluginMain.getPluginLogger().get());
                resources.on(PluginConfig.class).assignInstance(this.pluginConfig);
                resources.on(MessageConfig.class).assignInstance(this.messageConfig);
                resources.on(UserManager.class).assignInstance(this.userManager);
                resources.on(PluginStorage.class).assignInstance(this.pluginStorage);
                resources.on(Tasker.class).assignInstance(this.tasker);
            });

            this.funnyCommands = new CommandInitializer(this).create();
            this.listenerMap = new ListenerMap(this);
            this.taskMap = new TaskMap(this);

        } catch (Exception e) {
            this.disableErrorPlugin(e);
            return;
        }

        final long loadTime = System.currentTimeMillis() - pluginStart;
        pluginLogger.get().info(String.format("Finished and runned plugin! (%sms)", loadTime));
    }

    @Override
    public void onDisable() {
        if(!this.pluginEnabled.get()) return;
        final long time = System.currentTimeMillis();

        this.funnyCommands.dispose();
        new PluginLoader(this, this.storagePath).save();
        PluginMain.getPluginLogger().get().info("Data has been saved! (" + (System.currentTimeMillis() - time) + "ms)");

        pluginMain = new NullClass<>();
        pluginLogger = new NullClass<>();
    }

    private void disableErrorPlugin(Throwable throwable) {
        pluginLogger.get().error("Plugin napotkal problem, przez ktory nie moze kontynuowac pracy:", throwable);
        this.getPluginEnabled().set(false);
        this.getServer().getPluginManager().disablePlugin(this);

        if(this.funnyCommands != null) {
            this.funnyCommands.dispose();
        }

        pluginMain = new NullClass<>();
        pluginLogger = new NullClass<>();
    }
}
