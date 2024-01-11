package cc.dreamcode.itemchange.config;

import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import cc.dreamcode.utilities.builder.MapBuilder;
import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;

import java.util.Map;

@Configuration(child = "config.yml")
@Header("## Dream-ItemChange (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {

    @Comment("Czy plugin ma wspierac ekonomie z pluginu Vault")
    public boolean useVault = false;

    @Comment("Jezeli wsparcie ekonomii Vault jest wlaczone jaka cene ma miec komenda /wymiana")
    public double price = 500.0;

    @Comment("Lista przedmiotow, ktore mozna wymieniac i material na ktory zostana zmienione")
    public Map<XMaterial, XMaterial> itemsToChange = new MapBuilder<XMaterial, XMaterial>()
            .put(XMaterial.NETHERITE_HELMET, XMaterial.DIAMOND_HELMET)
            .put(XMaterial.NETHERITE_CHESTPLATE, XMaterial.DIAMOND_CHESTPLATE)
            .put(XMaterial.NETHERITE_LEGGINGS, XMaterial.DIAMOND_LEGGINGS)
            .put(XMaterial.NETHERITE_BOOTS, XMaterial.DIAMOND_BOOTS)
            .put(XMaterial.NETHERITE_SWORD, XMaterial.DIAMOND_SWORD)
            .put(XMaterial.NETHERITE_PICKAXE, XMaterial.DIAMOND_PICKAXE)
            .put(XMaterial.NETHERITE_SHOVEL, XMaterial.DIAMOND_SHOVEL)
            .build();
}
