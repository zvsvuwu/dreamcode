package cc.dreamcode.amulets.config;

import cc.dreamcode.amulets.amulet.Amulet;
import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import cc.dreamcode.utilities.builder.ListBuilder;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XPotion;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import org.bukkit.potion.PotionEffect;

import java.util.List;

@Configuration(
        child = "config.yml"
)
@Header("## Dream-Amulets (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {

    @Comment("Lista amuletów, które ma plugin zarejestrować")
    public List<Amulet> amulets = new ListBuilder<Amulet>()
            .add(
                    new Amulet(
                            "fire",
                            "Amulet Ognia",
                            ItemBuilder.of(XMaterial.FIRE_CHARGE.parseItem())
                                    .setName("&6Amulet Ognia")
                                    .toItemStack(),
                            true,
                            new ListBuilder<PotionEffect>()
                                    .add(XPotion.FIRE_RESISTANCE.buildPotionEffect(5000, 1))
                                    .build()
                    )
            )
            .build();
}
