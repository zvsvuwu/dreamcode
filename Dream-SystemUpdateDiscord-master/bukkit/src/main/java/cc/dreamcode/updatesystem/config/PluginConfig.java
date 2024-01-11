package cc.dreamcode.updatesystem.config;

import cc.dreamcode.menu.serdes.bukkit.BukkitMenuBuilder;
import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import cc.dreamcode.platform.persistence.StorageConfig;
import cc.dreamcode.updatesystem.update.UpdateChannel;
import cc.dreamcode.utilities.builder.ListBuilder;
import cc.dreamcode.utilities.builder.MapBuilder;
import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import net.dv8tion.jda.api.entities.Activity;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

@Configuration(
        child = "config.yml"
)
@Header("## Dream-SystemUpdateDiscord (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {

    @Comment("Debug pokazuje dodatkowe informacje do konsoli. Lepiej wylaczyc. :P")
    public boolean debug = true;

    @Comment("Uzupelnij ponizsze menu danymi.")
    public StorageConfig storageConfig = new StorageConfig("dreamsystemupdatediscord");

    @Comment("Token bota:")
    public String botToken = "";

    @Comment("Typ aktywnosci bota: https://ci.dv8tion.net/job/JDA5/javadoc/net/dv8tion/jda/api/entities/Activity.ActivityType.html")
    public Activity.ActivityType activityType = Activity.ActivityType.PLAYING;

    @Comment("Status bota")
    public String botStatus = "dreamcode.cc";

    @Comment("Menu wiadomosci: ")
    public BukkitMenuBuilder messageMenu = new BukkitMenuBuilder(
            "&8Discord",
            6,
            true,
            new MapBuilder<Integer, ItemStack>().build());

    @Comment("Wyswietalnie ogloszenia: ")
    public String displayName = "{categorydisplay}";
    public List<String> displayLore = new ListBuilder<String>()
            .add("&fTresc: &b{message}")
            .add("&fPrzez: &b{by}")
            .add("&fKategoria: &c{category}")
            .add("&fDodano: &8{time}")
            .build();
    public String defaultPrefixPreMessageLine = "  &b";


    @Comment("Lista kanałów: ")
    public Map<String, UpdateChannel> channels = new MapBuilder<String, UpdateChannel>()
            .put("1092415376035422301", new UpdateChannel(XMaterial.ANVIL, "Zmiany", "&b&LZmiana"))
            .put("1092415386701529164", new UpdateChannel(XMaterial.EMERALD, "Nowosci", "&a&lNowosc"))
            .put("1092415396381986943", new UpdateChannel(XMaterial.NETHER_STAR, "Konkursy", "&6&lKonkurs"))
            .build();
}
