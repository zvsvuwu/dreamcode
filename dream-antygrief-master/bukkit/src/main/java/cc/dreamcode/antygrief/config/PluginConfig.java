package cc.dreamcode.antygrief.config;

import cc.dreamcode.antygrief.region.Region;
import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import cc.dreamcode.utilities.builder.ListBuilder;
import com.google.common.collect.ImmutableList;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import org.bukkit.Material;

import java.util.List;

@Configuration(child = "config.yml")
@Header("## Dream-AntyGrief (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {

    @Comment("Permisja do przeladowania configu to \"dream-antigrief.reload\"")
    @Comment("Czy uzywac permisji \"dream-antigrief.bypass\", aby bypassowac dzialanie pluginu")
    public boolean usePermissionBypass = true;

    @Comment("Czy plugin ma ignorowac bloki postawione na terenie gildii (FunnyGuilds wymagane)")
    public boolean ignoreGuild = true;

    @Comment("Lista blokow ktore beda uznawane przez plugin jako grief i beda usuwane")
    public List<Material> blocksList = new ImmutableList.Builder<Material>()
            .add(Material.STONE)
            .add(Material.BEACON)
            .build();

    @Comment("Powyzej jakiego poziomu Y bloki maja byc usuwane")
    public int yLevel = 63;

    @Comment("Czas po jakim plugin bedzie usuwal blok (w sekundach)")
    public int removeTime = 15;

    @Comment("Czy wysylac graczowi informacje o tym za ile plugin usunie jego postawiony blok?")
    public boolean sendNotice = true;

    @Comment("Cooldown na wysylanie wiadomosci o planowanym usunieciu bloku (w sekundach)")
    public int messageCooldown = 3;

    @Comment("Czy uzywac ponizszej listy regionow")
    public boolean useRegions = false;

    @Comment("Lista koordynatow regionow w ktorych dzialanie pluginu jest ignorowane")
    public List<Region> regions = new ListBuilder<Region>()
            .add(new Region(
                    "world",
                    -20,
                    0,
                    -20,
                    20,
                    319,
                    20
            ))
            .build();

}
