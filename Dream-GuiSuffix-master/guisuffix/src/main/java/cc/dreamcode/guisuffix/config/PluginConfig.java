package cc.dreamcode.guisuffix.config;

import cc.dreamcode.guisuffix.config.subconfig.MenuConfig;
import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import cc.dreamcode.platform.persistence.StorageConfig;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;

import java.time.Duration;

@Configuration(
        child = "config.yml"
)
@Headers({
        @Header("## Dream-GUISuffix Config ##"),
        @Header("# Uprawnienia: "),
        @Header("# - dreamcode.guisuffix.cooldown.bypass (brak cooldownu uzycia komend) "),
        @Header("# - dream.suffix (komenda /suffix)"),
        @Header("# - dream.usunsuffix (komenda /usunsuffix)"),
        @Header(" ")
})

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {

    @Comment("Debug pokazuje dodatkowe informacje do konsoli. Lepiej wylaczyc. :P")
    public boolean debug = true;

    @Comment("Uzupelnij ponizsze menu danymi.")
    public StorageConfig storageConfig = new StorageConfig("dreamguisuffix");

    @Comment("Jaki ma byc cooldown komendy /suffix")
    public Duration suffixCooldown = Duration.ofSeconds(30);

    @Comment("Jaki ma byc cooldown komendy /usunsuffix")
    public Duration removeSuffixCooldown = Duration.ofSeconds(15);

    public MenuConfig menuConfig = new MenuConfig();
}
