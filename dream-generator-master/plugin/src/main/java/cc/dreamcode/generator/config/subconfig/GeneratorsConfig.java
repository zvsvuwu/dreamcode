package cc.dreamcode.generator.config.subconfig;

import cc.dreamcode.generator.builder.ItemBuilder;
import cc.dreamcode.generator.features.generator.template.GeneratorTemplate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import org.bukkit.Material;
import java.util.List;

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class GeneratorsConfig extends OkaeriConfig {

    @Comment({"Poniżej znajduję się pełna konfiguracja generatorów",
    "Możesz dodawać nowe generatory według schematu, zmienić prędkość regeneracji, crafting itp"})
    public List<GeneratorTemplate> generatorList = new ImmutableList.Builder<GeneratorTemplate>()
            .add(new GeneratorTemplate(
                    new ItemBuilder(Material.STONE)
                            .setName("&FWolna stoniarka")
                            .setLore("&7Postaw na ziemi aby utworzyc stoniarke")
                            .toItemStack(),
                    5,
                    "AAA,AXA,AAA",
                    new ImmutableMap.Builder<Character, Material>()
                            .put('A', Material.STONE)
                            .put('X', Material.DIAMOND)
                            .build()
            ))
            .add(new GeneratorTemplate(
                    new ItemBuilder(Material.STONE)
                            .setName("&aSzybka stoniarka")
                            .setLore("&7Postaw na ziemi aby utworzyc stoniarke")
                            .toItemStack(),
                    3,
                    "AAA,AXA,AAA",
                    new ImmutableMap.Builder<Character, Material>()
                            .put('A', Material.STONE)
                            .put('X', Material.EMERALD)
                            .build()
            )).build();
}
