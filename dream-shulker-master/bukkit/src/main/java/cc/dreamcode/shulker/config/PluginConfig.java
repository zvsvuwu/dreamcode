package cc.dreamcode.shulker.config;

import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

import java.time.Duration;

@Configuration(
        child = "config.yml"
)
@Header("## Dream-Shulker (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {
    @Comment("Jak shulker ma sie nazywac?")
    public String shulkerName = "Shulker Box";

    @Comment("Jaki ma byc cooldown na shulkera?")
    public Duration shulkerCooldown = Duration.ofSeconds(1);

    @Comment("Jakie uprawnienie omija cooldown na shulkera?")
    public String shulkerBypassPermission = "dream.shulker.bypass";

    @Comment("Czy gracz bedzie mogl trzymac shulkera w lewej rece?")
    public boolean holdInOffHand = true;

    @Comment("Czy gracz bedzie mogl otwierac shulkera z lewej reki?")
    public boolean openShulkerWithOffHand = false;
}
