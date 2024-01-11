package cc.dreamcode.killreward.config;

import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import cc.dreamcode.utilities.TimeUtil;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.ChatUtil;
import com.cryptomorin.xseries.XSound;
import eu.okaeri.placeholders.context.Placeholder;
import eu.okaeri.placeholders.context.PlaceholderContext;
import eu.okaeri.placeholders.message.CompiledMessage;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import org.bukkit.Sound;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;

import java.time.Duration;
import java.util.*;

@Configuration(
        child = "config.yml"
)
@Header("## Dream-KillReward (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {
    @Comment("Debug pokazuje dodatkowe informacje do konsoli. Lepiej wylaczyc. :P")
    public boolean debug = true;

    @Comment("Czy nagroda za zabicie gracza ma być włączona.")
    public boolean killRewardEnabled = true;

    @Comment("Cooldown na nagrodę za zabicie gracza.")
    public Duration killRewardCooldown = Duration.ofMinutes(5);

    @Comment("Szansa na nagrodę z gracza.")
    public double killRewardChance = 0.25;

    @Comment("Komendy, które mają się wysyłać podczas odbierania nagrody za zabicie gracza.")
    public List<String> killRewardCommands = Collections.singletonList("give {killer} diamond 1");

    @Comment("Komendy, które mają się wysyłać po zabiciu danego moba przez gracza.")
    public Map<EntityType, List<String>> entityKillRewardCommands = Collections.singletonMap(EntityType.CREEPER, Collections.singletonList("give {killer} cake 1"));

    @Comment("Komendy, które mają się wysłać podczas odbieranie nagrody za zabicie gracza przez daną rangę.")
    public Map<String, List<String>> killRewardGroupCommands = Collections.singletonMap("vip", Collections.singletonList("give {killer} emerald 1"));

    @Comment("Szansa danej rangi LuckPerms na nagrodę z gracza.")
    public Map<String, Double> killRewardGroupChance = Collections.singletonMap("vip", 0.50);

    @Comment("Dźwięki po zabiciu gracza.")
    public List<XSound> killSounds = Collections.singletonList(XSound.BLOCK_ANVIL_DESTROY);

    @Comment("Tytuł BossBara, który będzie wyświetlany po nadaniu boosta.")
    public String bossbarTitle = "&fBoost szansy na nagrodę za zabójstwo jest aktywny jeszcze przez &d{time}&f z szansą &d{chance}&f!";

    @Comment("Styl BossBara.")
    public BarStyle bossbarStyle = BarStyle.SOLID;

    @Comment("Kolor BossBara.")
    public BarColor bossbarColor = BarColor.PURPLE;

    public BossBar buildBossBar(Map<String, Object> replacementMap) {
        return Bukkit.createBossBar(PlaceholderContext.of(CompiledMessage.of(ChatUtil.fixColor(this.bossbarTitle)))
                .with(replacementMap)
                .apply(), this.bossbarColor, this.bossbarStyle);
    }

    public String getBossbarTitle(Map<String, Object> replacementMap) {
        return PlaceholderContext.of(CompiledMessage.of(ChatUtil.fixColor(this.bossbarTitle))).with(replacementMap).apply();
    }
}
