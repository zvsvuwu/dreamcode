package cc.dreamcode.anvil.config;

import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

@Configuration(
        child = "config.yml"
)
@Header("## Dream-Kowadlo (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {
    @Comment("Debug pokazuje dodatkowe informacje do konsoli. Lepiej wylaczyc. :P")
    public boolean debug = true;

    @Comment("Jaki przedmiot ma byc pobierany przy naprawianiu przydmiotu?")
    public XMaterial blockForRepair = XMaterial.DIAMOND_BLOCK;

    @Comment("Jaka ilosc musi byc zabrana przy naprawianiu")
    public int repairCost = 5;
}
