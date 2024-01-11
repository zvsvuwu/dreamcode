package cc.dreamcode.ores.config;

import cc.dreamcode.ores.builder.MapBuilder;
import cc.dreamcode.ores.config.annotations.Configuration;
import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;

import java.util.Map;

@Configuration(
        child = "config.yml"
)
@Header("## Dream-Ores (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {

    @Comment("Lista blokow po ktorych zniszczeniu gracz otrzyma je od razu do ekwipunku")
    @Comment("Wzor powinien wygladac tak: NAZWA_BLOKU:NAZWA_PRZEDMIOTU")
    @Comment("Gdzie NAZWA_BLOKU to nazwa bloku po ktorego zniszczeniu wypada przedmiot NAZWA_PRZEDMIOTU")
    public Map<XMaterial, XMaterial> oresList = new MapBuilder<XMaterial, XMaterial>()
            .put(XMaterial.IRON_ORE, XMaterial.IRON_INGOT)
            .build();
}
