package xyz.ravis96.dreamkit.features.persistence;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.zaxxer.hikari.HikariConfig;
import eu.okaeri.configs.json.simple.JsonSimpleConfigurer;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import eu.okaeri.persistence.PersistencePath;
import eu.okaeri.persistence.document.DocumentPersistence;
import eu.okaeri.persistence.flat.FlatPersistence;
import eu.okaeri.persistence.jdbc.H2Persistence;
import eu.okaeri.persistence.jdbc.MariaDbPersistence;
import eu.okaeri.persistence.mongo.MongoPersistence;
import eu.okaeri.persistence.redis.RedisPersistence;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import lombok.RequiredArgsConstructor;
import xyz.ravis96.dreamkit.PluginMain;
import xyz.ravis96.dreamkit.config.subconfig.StorageConfig;

import java.io.File;

@RequiredArgsConstructor
public class Persistence {

    private final PluginMain pluginMain;

    public DocumentPersistence getDocumentPersistence(File filePath) {
        final StorageConfig storageConfig = this.pluginMain.getPluginConfig().getStorageConfig();
        PersistencePath persistencePath = PersistencePath.of(storageConfig.prefix);

        try { Class.forName("org.mariadb.jdbc.Driver"); } catch (ClassNotFoundException ignored) { }
        try { Class.forName("org.h2.Driver"); } catch (ClassNotFoundException ignored) { }

        switch (storageConfig.backendSave) {
            case FLAT:
                return new DocumentPersistence(new FlatPersistence(
                        filePath, ".yml"), YamlBukkitConfigurer::new, new SerdesBukkit());
            case MYSQL:
                HikariConfig mariadbHikari = new HikariConfig();
                mariadbHikari.setJdbcUrl(storageConfig.uri);

                return new DocumentPersistence(new MariaDbPersistence(
                        persistencePath, mariadbHikari), JsonSimpleConfigurer::new, new SerdesBukkit());
            case H2:
                HikariConfig jdbcHikari = new HikariConfig();
                jdbcHikari.setJdbcUrl(storageConfig.uri);

                return new DocumentPersistence(new H2Persistence(
                        persistencePath, jdbcHikari), JsonSimpleConfigurer::new, new SerdesBukkit());
            case REDIS:
                RedisURI redisURI = RedisURI.create(storageConfig.uri);
                RedisClient redisClient = RedisClient.create(redisURI);

                return new DocumentPersistence(new RedisPersistence(
                        persistencePath, redisClient), JsonSimpleConfigurer::new, new SerdesBukkit());
            case MONGO:
                MongoClientURI mongoUri = new MongoClientURI(storageConfig.uri);
                MongoClient mongoClient = new MongoClient(mongoUri);

                if(mongoUri.getDatabase() == null) {
                    throw new IllegalArgumentException("Mongo URI musi posiadac nazwe database: " + mongoUri.getURI());
                }

                return new DocumentPersistence(new MongoPersistence(persistencePath, mongoClient, mongoUri.getDatabase()),
                        JsonSimpleConfigurer::new, new SerdesBukkit());
            default:
                throw new RuntimeException("Nieznany typ zapisu: " + storageConfig.backendSave);
        }
    }
}
