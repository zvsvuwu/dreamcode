package cc.dreamcode.leaderboard.config.storage;

import cc.dreamcode.leaderboard.storage.StorageType;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class StorageConfig extends OkaeriConfig {

    @Comment({"W jakiej formie maja byc zapisywane dane o graczu?",
            "Dostepne zapisy: (FLAT, MYSQL, MONGO, REDIS, H2)"})
    public StorageType storageType = StorageType.FLAT;

    @Comment({"Jaki prefix ustawic dla danych?",
            "Dla FLAT prefix nie jest uzywany."})
    public String prefix = "dreamleaderboard";

    @Comment("FLAT   : nie jest wymagane podawanie dodatkowych informacji")
    @Comment("REDIS  : redis://localhost")
    @Comment("MONGO  : mongodb://localhost:27017/?maxPoolSize=20&w=majority")
    @Comment("MYSQL  : jdbc:mysql://localhost:3306/db?user=root&password=1234")
    @Comment("H2     : jdbc:h2:file:./plugins/Dream-Leaderboard/storage;mode=mysql")
    public String uri = "";

}
