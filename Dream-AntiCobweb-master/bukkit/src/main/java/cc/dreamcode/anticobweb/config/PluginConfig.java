package cc.dreamcode.anticobweb.config;

import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;

@Configuration(
        child = "config.yml"
)
@Header("## AntiCobweb Config ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {
    @Comment("Czy maj pokazywac sie wiadomosc po uzyciu narzedzia")
    public boolean showUseMessage = true;

    @Comment("Zasieg usuwania pajeczyn")
    public int radius = 10;

    @Comment("Cooldown do narzedzia")
    public Duration cooldown = Duration.ofSeconds(25);

    @Comment("Czy rozdzka ma sie usuwac po uzyciu")
    public boolean removeOnUse = true;

    @Comment("Item, ktory sluzy jako rozdzka do usuwania pajeczyn")
    public ItemStack item = ItemBuilder.of(XMaterial.BLAZE_ROD.parseMaterial())
            .setName("&6AntiCobweb")
            .setLore("&5Item do usuwania cobwebow z okolicy")
            .addEnchant(Enchantment.KNOCKBACK, 1, false)
            .toItemStack();
}
