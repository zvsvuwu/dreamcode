package cc.dreamcode.template.config;

import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import cc.dreamcode.platform.bukkit.persistence.StorageConfig;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import org.bukkit.Material;

import java.util.*;

@Configuration(child = "config.yml")
@Header("## Dream-Template (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {
    @Comment("Debug pokazuje dodatkowe informacje do konsoli. Lepiej wylaczyc. :P")
    public boolean debug = true;

    @Comment("Uzupelnij ponizsze menu danymi.")
    public StorageConfig storageConfig = new StorageConfig("dreamtemplate");

    @Comment("Uprawnienia do niszczenia i  wszystkich bloków")
    public String allowAll = "blockrestriction.allowAll";

    @Comment("Uprawnienia do niszczenia wszystkich bloków")
    public String allowBreak = "blockrestriction.break";

    @Comment("Uprawnienia do stawiania wszystkich bloków")
    public String allowPlace = "blockrestriction.place";

    @Comment("Czy powinno wysylac wiadomosci")
    public boolean shouldSendMessages = true;

    @Comment("Lista blokow")
    public Set<Material> allowedBlocks = new HashSet<>(Arrays.asList(
            Material.STONE,
            Material.DIRT,
            Material.COBBLESTONE
    ));
}