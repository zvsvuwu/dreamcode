package cc.dreamcode.spawn.config;

import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffectType;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration(
        child = "config.yml"
)
@Header("## Dream-Spawn (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {
    @Comment("Debug pokazuje dodatkowe informacje do konsoli. Lepiej wylaczyc. :P")
    public boolean debug = true;

    @Comment("Czas teleportacji w sekundach.")
    public Duration teleportTime = Duration.ofSeconds(5);

    @Comment("Lokalizacja spawna.")
    public Location spawnLocation = new Location(Bukkit.getWorld("world"), 0.0, 0.0, 0.0, 0, 0);

    @Comment("Dodawanie efektów podczas teleportacji.")
    public boolean teleportEffectsEnabled = true;

    @Comment("Efekty, które będą nadawane podczas teleportacji.")
    public List<PotionEffectType> teleportEffects = Collections.singletonList(
            PotionEffectType.BLINDNESS
    );

    @Comment("Dodawanie dźwięków podczas teleportacji.")
    public boolean teleportSoundsEnabled = true;

    @Comment("Dźwięki, które będą odtwarzane podczas teleportacji.")
    public List<Sound> teleportSounds = Collections.singletonList(Sound.BLOCK_BAMBOO_STEP);

    @Comment("Wsparcie dla WorldGuard.")
    public boolean regionCooldownEnabled = false;

    @Comment("Cooldown teleportacji danego regionu WorldGuard.")
    public List<Map<String, Duration>> regionCooldowns = Collections.singletonList(
            new HashMap<String, Duration>(){{ put("spawn", Duration.ZERO);}}
    );

    @Comment("Cooldown teleportacji dla danej rangi Luckperms.")
    public List<Map<String, Duration>> groupCooldowns = Collections.singletonList(
            new HashMap<String, Duration>(){{ put("vip", Duration.ofSeconds(3)); }}
    );

    @Comment("Po włączeniu gracz będzie teleportowany na spawna po śmierci.")
    public boolean teleportAfterDeath = true;

    @Comment("Po włączeniu gracz nie będzie musiał klikać przycisku respawn po śmierci.")
    public boolean autoRespawn = true;

    @Comment("Po włączeniu gracz będzie teleportowany na spawna podczas każdego wejścia na serwer.")
    public boolean teleportAfterJoin = false;

    @Comment("Po włączeniu gracz będzie teleportowany na spawna po swoim pierwszym wejściu na serwer.")
    public boolean teleportAfterFirstJoin = true;

    @Comment("Permisja, która pozwala na teleportowanie się na spawna bez cooldownu.")
    public String bypassPermission = "dream-spawn.bypass";

    @Comment("Permisja administratora do użycia komendy: /spawnplugin reload, /setspawn.")
    public String adminPermission = "dream-spawn.admin";
}
