package cc.dreamcode.pickaxe.config;

import cc.dreamcode.pickaxe.region.Region;
import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import cc.dreamcode.platform.persistence.StorageConfig;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

@Configuration(
        child = "config.yml"
)
@Header("## Dream-Pickaxe (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {
    @Comment("Opcja debugowania, wyłącz w produkcji!")
    public boolean debug = true;

    @Comment("Różdżka regionów.")
    public ItemStack regionWand =
            ItemBuilder.of(Material.GOLDEN_AXE)
                    .setName("&f&lMagiczna Różdżka")
                    .setLore(
                            "",
                            " &7Oznacz rogi &fzaznaczonego terenu",
                            " &7aby &austawić &7region generatorów!",
                            "",
                            " &7Kliknij &fprawym &7jeden róg regionu.",
                            " &7Kliknij &flewym &7drugi róg regionu.")
                    .toItemStack();

    @Comment("Regiony")
    public Set<Region> regions = new HashSet<>();

    @Comment("Czy po zniszczeniu bloku ma zamienić się w bedrock na np. 2 sekundy i wrócić do poprzedniego stanu?")
    public boolean setBedrock = false;

    @Comment("Czas w sekundach, po którym blok zostaje zamieniony na stan poprzedni.")
    public int delayInSeconds = 2;

    @Comment("Konfiguracja przechowywania danych")
    public StorageConfig storageConfig = new StorageConfig("dream_pickaxe");
}
