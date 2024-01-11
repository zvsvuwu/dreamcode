package cc.dreamcode.zonebox.config;

import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import com.cryptomorin.xseries.XMaterial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;


@Configuration(child = "config.yml")
@Header("## Dream-BlockRestriction (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {
    @Comment("Debug pokazuje dodatkowe informacje do konsoli. Lepiej wylaczyc. :P")
    public boolean debug = true;

    @Comment("Uprawnienia do niszczenia i  wszystkich bloków")
    public String allowAll = "blockrestriction.allowAll";

    @Comment("Uprawnienia do niszczenia wszystkich bloków")
    public String allowBreak = "blockrestriction.break";

    @Comment("Uprawnienia do stawiania wszystkich bloków")
    public String allowPlace = "blockrestriction.place";

    @Comment("Czy powinno wysylac wiadomosci")
    public boolean shouldSendMessages = true;

    @Comment("Lista blokow")
    public List<XMaterial> allowedBlocks = new ArrayList<>(Arrays.asList(
            XMaterial.STONE,
            XMaterial.DIRT,
            XMaterial.COBBLESTONE
    ));
}