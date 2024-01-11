package xyz.ravis96.dreamkit.config.subconfig;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class StorageConfig extends OkaeriConfig {

    @Comment({"W jakiej formie maja byc zapisywane dane o graczu?",
            "Dostepne zapisy: (FLAT, MYSQL, H2, MONGO, REDIS)"})
    public BackendSave backendSave = BackendSave.FLAT;

    @Comment({"Jaki prefix ustawic dla danych?",
            "Dla FLAT prefix nie jest uzywany."})
    public String prefix = "dreamkit";

    @Comment({"Ustaw URI do bazy danych.",
            "FLAT   : nie musisz uzupelniac",
            "REDIS  : redis://{host}[:{port}]?db={database}&password={password}",
            "MYSQL  : jdbc:mysql://{host}[:{port}]/{database}?user={name}&password={password}",
            "MONGO  : mongodb://{name}:{password}@{host}[:{port}]/{database}?authSource=admin",
            "H2     : jdbc:h2:file:./plugins/dream-storage;mode=mysql"})
    public String uri = "flat";

    public enum BackendSave {
        FLAT, MYSQL, H2, MONGO, REDIS
    }

}
