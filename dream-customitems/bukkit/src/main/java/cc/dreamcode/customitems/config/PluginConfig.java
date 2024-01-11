package cc.dreamcode.customitems.config;

import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import com.cryptomorin.xseries.XEnchantment;
import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration(
        child = "config.yml"
)
@Header("## Dream-CustomItems (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {
    @Comment("Debug pokazuje dodatkowe informacje do konsoli. Lepiej wylaczyc. :P")
    public boolean debug = true;

    @Comment("Pajęczy granat.")
    public ItemStack spiderGrenade = new ItemBuilder(XMaterial.SPLASH_POTION.parseMaterial())
            .setName("&f&lPajęczy granat")
            .setLore(Collections.singletonList("&ePo rzuceniu tworzy na ziemi pajęczą sieć na szerokość 3 kratek."))
            .addFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_ATTRIBUTES)
            .toItemStack();

    public ItemStack buildSpiderGrenade() {
        return ItemBuilder.of(spiderGrenade).fixColors().toItemStack();
    }

    @Comment("Cooldown na używanie pajęczego granatu.")
    public Duration spiderGrenadeCooldown = Duration.ofMinutes(5);

    @Comment("Wysokość sieci pajęczego granatu.")
    public int spiderGrenadeHeight = 1;

    @Comment("Szerokość sieci pajęczego granatu.")
    public int spiderGrenadeWidth = 3;

    @Comment("Kość ogłuszenia.")
    public ItemStack bone = new ItemBuilder(XMaterial.BONE.parseMaterial())
            .setName("&f&lKość ogłuszenia")
            .setLore(Collections.singletonList("&ePo uderzeniu przeciwnika jest 25% szans na ogłuszenie go."))
            .toItemStack();

    public ItemStack buildBone() {
        return ItemBuilder.of(bone).fixColors().toItemStack();
    }

    @Comment("Efekty, które mają być nadane po ogłuszeniu gracza. Duration to liczba tickow. 1 sekunda to 20 tickow.")
    public List<PotionEffect> boneEffects = Collections.singletonList(new PotionEffect(PotionEffectType.SLOW, 100, 1));

    @Comment("Cooldown na używanie kości ogłuszenia.")
    public Duration boneCooldown = Duration.ofMinutes(1);

    @Comment("Szansa na ogłuszenie przeciwnika.")
    public double bonePercent = 0.25;

    @Comment("Kusza przyciągania.")
    public ItemStack crossbow = new ItemBuilder(XMaterial.CROSSBOW.parseMaterial())
            .setName("&6&lKusza przyciągania")
            .setLore(Arrays.asList("&eGdy trafisz gracza z tej kuszy ", "&emasz 25% szans na to, że zostanie on do ciebie przyciągnięty."))
            .addEnchant(XEnchantment.DURABILITY.getEnchant(), 32767)
            .addFlags(ItemFlag.HIDE_ENCHANTS)
            .toItemStack();

    public ItemStack buildCrossbow() {
        return ItemBuilder.of(crossbow).fixColors().toItemStack();
    }

    @Comment("Cooldown na przyciąganie gracza kuszą przyciągania.")
    public Duration crossbowCooldown = Duration.ofMinutes(1);

    @Comment("Procent na przyciągnięcie gracza kuszą przyciągania.")
    public double crossbowPercent = 0.25;

    @Comment("Łuk podrzutu.")
    public ItemStack bow = new ItemBuilder(XMaterial.BOW.parseMaterial())
            .setName("&6&lŁuk podrzutu")
            .setLore(Arrays.asList("&eGdy trafisz graca z tego łuku ", "&emasz 25% szans, że podrzucisz go na 10 kratek."))
            .addEnchant(XEnchantment.DURABILITY.getEnchant(), 32767)
            .addFlags(ItemFlag.HIDE_ENCHANTS)
            .toItemStack();

    public ItemStack buildBow() {
        return ItemBuilder.of(bow).fixColors().toItemStack();
    }

    @Comment("Cooldown na używanie łuku podrzutu.")
    public Duration bowCooldown = Duration.ofMinutes(1);

    @Comment("Ilość kratek podrzucenia po trafieniu gracza łukiem podrzucenia.")
    public int bowHeight = 10;

    @Comment("Szansa na podrzucenie gracza łukiem podrzucenia.")
    public double bowPercent = 0.25;

    @Comment("Kij Bejsbolowy.")
    public ItemStack baseballBat = new ItemBuilder(XMaterial.STICK.parseMaterial())
            .setName("&6&lKij Bejsbolowy")
            .setLore(Collections.singletonList("&ePo uderzeniu przeciwnik zostanie odrzucony mocą 4."))
            .addFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_ATTRIBUTES)
            .addEnchant(Enchantment.KNOCKBACK, 4)
            .toItemStack();

    public ItemStack buildBaseballBat() {
        return ItemBuilder.of(baseballBat).fixColors().toItemStack();
    }

    @Comment("Płyta obrażeń.")
    public ItemStack damagePlate = new ItemBuilder(XMaterial.HEAVY_WEIGHTED_PRESSURE_PLATE.parseMaterial())
            .setName("&6&lPłyta obrażeń")
            .setLore(Arrays.asList("&eGdy gracz stanie na tej płycie", "&edostanie natychmiastowo obrażenia."))
            .toItemStack();

    public ItemStack buildDamagePlate() {
        return ItemBuilder.of(damagePlate).fixColors().toItemStack();
    }

    @Comment("Cooldown na stawianie płyty obrażeń.")
    public Duration damagePlateCooldown = Duration.ofMinutes(1);

    @Comment("Obrażenia zadawane po nadepnięciu na płytę obrażeń.")
    public double damagePlateDamage = 6.0;

    @Comment("Totem ekwipunku.")
    public ItemStack totem = new ItemBuilder(XMaterial.TOTEM_OF_UNDYING.parseMaterial())
            .setName("&6&lTotem ekwipunku")
            .setLore("&ePo śmierci z tym totemem w drugiej ręce ", "&etwoje przedmioty zostają zachowane.")
            .toItemStack();

    public ItemStack buildTotem() {
        return ItemBuilder.of(totem).fixColors().toItemStack();
    }

    @Comment("Śnieżka teleportacji.")
    public ItemStack snowball = new ItemBuilder(XMaterial.SNOWBALL.parseMaterial())
            .setName("&f&lŚnieżka teleportacji")
            .setLore(Arrays.asList("&eGdy trafisz gracza tą śnieżką masz ", "&e25% szans, że zamienisz się z nim pozycją."))
            .toItemStack();

    public ItemStack buildSnowball() {
        return ItemBuilder.of(snowball).fixColors().toItemStack();
    }

    @Comment("Cooldown na śnieżkę teleportacji.")
    public Duration snowballCooldown = Duration.ofMinutes(1);

    @Comment("Szansa na zamiane pozycji śnieżką teleportacji.")
    public double snowballPercent = 0.25;
}
