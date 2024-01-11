package pl.virtual.strefa.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

@Header({"## dcStrefaAfk (config.yml) ##", "# Dostepne type: (CHAT, ACTIONBAR, SUBTITLE, TITLE, TITLE_SUBTITLE)"})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class ConfigPlugin extends OkaeriConfig {
    @Comment({"","Komenda przeladowuje config (/strefareload)"})
    public String reloadPerms = "dreamcode.strefa.reload";
    public Type messageReloadNoPerms = new Type("CHAT", "&cNie posiadasz uprawnien do tej komendy");
    public Type messageReloadSuccess = new Type("CHAT", "&aPoprawnie przeladowales config");
    @Comment({"","Dzieki tej permissji nie zostaniesz wyrzucny z serwera przez plugin"})
    public String antyAfkPerms = "dreamcode.antyafk";
    @Comment("Czas afk podany w sekundach")
    public int afkTime = 300;
    @Comment("Powod wyrzucenia")
    public boolean kickStatus = true;
    public String kickReason = "&cByles zbyt dlugo afk";
    @Comment({"","Gracze bedacy w tej strefie nie zostaja wyrzuceni","Kordy strefy AFK"})
    public List<Cord> afkArea = Arrays.asList(new Cord(-10,-10,70,10,10, 90));
    @Comment({"","Gracze bedacy w tej strefie nie zostaja wyrzuceni","Nazwa regionu w WorldGuard"})
    public boolean wolrdguardIntegration = false;
    public List<String> worldguardRegions = Arrays.asList("afk");
    @Comment({"","Informacja o wejsciu do strefy AFK"})
    public boolean messageToStatus = true;
    public Type messageTo = new Type("CHAT", "&cWkroczyles do strefy AFK");
    @Comment({"Informacja o opuszczeniu strefy AFK"})
    public boolean messageFromStatus = true;
    public Type messageFrom = new Type("CHAT", "&cOpusciles strefe AFK");
    @Comment({"","Co ma byc brane pod uwage przy resetowaniu czasu"})
    public boolean moveEvent = true;
    public boolean breakEvent = true;
    public boolean placeEvent = true;
    public boolean damageEvent = true;
    @Comment({"","Nagroda za spedzanie czasu w strefie afk"})
    @Comment("Czas afk podany w sekundach")
    public boolean awardStatus = true;
    public int awardTime = 3600;
    public List<String> commandAward = Arrays.asList("say {PLAYER} Dostal nagrode za czas afk");
    public List<ItemStack> itemAward = Arrays.asList(new ItemStack(Material.DIAMOND_SWORD));
    @Comment("Informacja na o nagrodzie")
    public boolean awardMessageStatus = true;
    public String awardType = "ACTIONBAR";
    public String awardMessage = "&cZa {TIME} otrzymasz nagrode";
    @Comment("Informacja o otrzymaniu nagrody")
    public boolean awardAddMessageStatus = true;
    public String awardAddType = "TITLE";
    public String awardAddMessage = "&cGratulacje otrzymales nagrode";
}

