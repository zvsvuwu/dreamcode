package cc.dreamcode.leaderboard.component.classes;

import cc.dreamcode.leaderboard.LeaderboardPlugin;
import cc.dreamcode.leaderboard.builder.MapBuilder;
import cc.dreamcode.leaderboard.component.resolvers.ComponentClassResolver;
import cc.dreamcode.leaderboard.config.PluginConfig;
import cc.dreamcode.leaderboard.storage.StorageSerdesPack;
import cc.dreamcode.leaderboard.config.storage.StorageConfig;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.zaxxer.hikari.HikariConfig;
import eu.okaeri.configs.json.simple.JsonSimpleConfigurer;
import eu.okaeri.configs.serdes.commons.SerdesCommons;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import eu.okaeri.injector.Injector;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.persistence.PersistencePath;
import eu.okaeri.persistence.document.DocumentPersistence;
import eu.okaeri.persistence.flat.FlatPersistence;
import eu.okaeri.persistence.jdbc.H2Persistence;
import eu.okaeri.persistence.jdbc.MariaDbPersistence;
import eu.okaeri.persistence.mongo.MongoPersistence;
import eu.okaeri.persistence.redis.RedisPersistence;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import lombok.NonNull;

import java.util.Map;

public class DocumentPersistenceComponentClassResolver extends ComponentClassResolver<Class<DocumentPersistence>> {

    private @Inject LeaderboardPlugin leaderboardPlugin;
    private @Inject PluginConfig pluginConfig;

    @Override
    public boolean isAssignableFrom(@NonNull Class<DocumentPersistence> documentPersistenceClass) {
        return DocumentPersistence.class.isAssignableFrom(documentPersistenceClass);
    }

    @Override
    public String getComponentName() {
        return "persistence";
    }

    @Override
    public Map<String, Object> getMetas(@NonNull Injector injector, @NonNull Class<DocumentPersistence> documentPersistenceClass) {
        final StorageConfig storageConfig = this.pluginConfig.storageConfig;
        return new MapBuilder<String, Object>()
                .put("type", storageConfig.storageType.getName())
                .put("prefix", storageConfig.prefix)
                .build();
    }

    @Override
    public Object resolve(@NonNull Injector injector, @NonNull Class<DocumentPersistence> documentPersistenceClass) {
        final StorageConfig storageConfig = this.pluginConfig.storageConfig;
        final PersistencePath persistencePath = PersistencePath.of(storageConfig.prefix);

        try {
            Class.forName("org.mariadb.jdbc.Driver");
        }
        catch (ClassNotFoundException ignored) { }
        try {
            Class.forName("org.h2.Driver");
        }
        catch (ClassNotFoundException ignored) { }

        switch (storageConfig.storageType) {
            case FLAT:
                return new DocumentPersistence(
                        new FlatPersistence(
                                this.leaderboardPlugin.getDataFolder(),
                                ".yml"
                        ),
                        YamlBukkitConfigurer::new,
                        new SerdesBukkit(),
                        new SerdesCommons(),
                        new StorageSerdesPack()
                );
            case MYSQL:
                HikariConfig mariadbHikari = new HikariConfig();
                mariadbHikari.setJdbcUrl(storageConfig.uri);

                return new DocumentPersistence(
                        new MariaDbPersistence(
                                persistencePath,
                                mariadbHikari
                        ),
                        JsonSimpleConfigurer::new,
                        new SerdesBukkit(),
                        new SerdesCommons(),
                        new StorageSerdesPack()
                );
            case H2:
                HikariConfig jdbcHikari = new HikariConfig();
                jdbcHikari.setJdbcUrl(storageConfig.uri);

                return new DocumentPersistence(
                        new H2Persistence(
                                persistencePath,
                                jdbcHikari
                        ),
                        JsonSimpleConfigurer::new,
                        new SerdesBukkit(),
                        new SerdesCommons(),
                        new StorageSerdesPack()
                );
            case REDIS:
                RedisURI redisURI = RedisURI.create(storageConfig.uri);
                RedisClient redisClient = RedisClient.create(redisURI);

                return new DocumentPersistence(
                        new RedisPersistence(
                                persistencePath,
                                redisClient
                        ),
                        JsonSimpleConfigurer::new,
                        new SerdesBukkit(),
                        new SerdesCommons(),
                        new StorageSerdesPack()
                );
            case MONGO:
                MongoClient mongoClient = MongoClients.create(storageConfig.uri);

                return new DocumentPersistence(
                        new MongoPersistence(
                                persistencePath,
                                mongoClient,
                                storageConfig.prefix
                        ),
                        JsonSimpleConfigurer::new,
                        new SerdesBukkit(),
                        new SerdesCommons(),
                        new StorageSerdesPack()
                );
            default:
                throw new RuntimeException("Unknown data type: " + storageConfig.storageType);
        }
    }
}
