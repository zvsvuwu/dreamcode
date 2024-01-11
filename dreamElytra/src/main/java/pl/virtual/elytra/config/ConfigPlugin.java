package pl.virtual.elytra.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

@Header({"## dcElytra (config.yml) ##", "# Dostepne type: (CHAT, ACTIONBAR, SUBTITLE, TITLE, TITLE_SUBTITLE)"})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class ConfigPlugin extends OkaeriConfig {
    @Comment({"","Gracze bedacy w tej strefie nie moga uzywac elytry","Kordy regionu"})
    public boolean areaIntegration = true;
    public List<Cord> area = Arrays.asList(new Cord(-10,-10,70,10,10, 90));
    @Comment({"","Gracze bedacy w tej strefie nie moga uzywac elytry","Nazwa regionu w WorldGuard"})
    public boolean wolrdguardIntegration = false;
    public List<String> worldguardRegions = Arrays.asList("elytra");
    @Comment({"","Gracze bedacy na tym swiecie nie moga uzywac elytry","Nazwa swiata"})
    public boolean worldIntegration = false;
    public List<String> worldName = Arrays.asList("world");
    @Comment({"","Wiadomosc wyskakujaca przy probie latania",})
    public boolean messageToStatus = true;
    public Type messageTo = new Type("CHAT", "&cWkroczyles do bez elytry");
    @Comment({"Informacja o opuszczeniu strefy AFK"})
    public boolean messageFromStatus = true;
    public Type messageFrom = new Type("CHAT", "&cOpusciles strefe bez elytry");
    @Comment({"","Co ma byc brane pod uwage przy resetowaniu czasu"})
    public Type message = new Type("CHAT", "&cNie mozesz szybowac w tym terenie");
    @Comment({"","Permissja do bypass'a",})
    public String permissionBypass = "elytra.bypass";
}

