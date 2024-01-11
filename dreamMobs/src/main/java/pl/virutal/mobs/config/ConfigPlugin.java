package pl.virutal.mobs.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;

import java.util.Arrays;
import java.util.List;

@Header({"## dcMobs (config.yml) ##",""})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class ConfigPlugin extends OkaeriConfig {
    public boolean worldIntegration = false;
    @Comment("W jakich swiatach moby nie maja byc blokowane (tylko gdy opcja world-integration jest wlaczona)")
    public List<String> worldNameBypass = Arrays.asList("world");
    @Comment("")
    public List<String> disabledSpawnMobs = Arrays.asList("ZOMBIE");
    public List<String> disabledSpawnMobsFromEgs = Arrays.asList("SKELETON");
    public List<String> disabledSpawnMobsFromSpawner = Arrays.asList("SLIME");
    public List<String> disabledSpawnMobsFromCommand = Arrays.asList("BAT");
    public List<String> disabledSpawnMobsFromNatural = Arrays.asList("CHICKEN");
    public List<String> disabledSpawnMobsFromBreeding = Arrays.asList("SHEEP");
    @Comment({"","Komenda /mobreload"})
    public String reloadPrems = "mobs.reload";
    public Type messageReloadNoPerms = new Type("CHAT", "&cNie posiadasz uprawnien do tej komendy");
    public Type messageReloadSuccess = new Type("CHAT", "&aPoprawnie przeladowales config");
    @Comment({"","Komenda /mobreload"})
    public String guiPrems = "mobs.gui";
    public Type messageGuiNoPerms = new Type("CHAT", "&cNie posiadasz uprawnien do tej komendy");
    @Comment({"","Gui"})
    public String guiMenuName = "&4&lMOBS MENU";
    public String guiMobName = "&4&lMOBS";
    public Type guiErrorMob = new Type("CHAT", "&cTen mob nie wystepuje na tej wersji");
}

