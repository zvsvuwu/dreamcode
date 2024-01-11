package cc.dreamcode.sprawdzanie.config;

import cc.dreamcode.sprawdzanie.config.annotations.Configuration;
import com.google.common.collect.ImmutableList;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

@Configuration(child = "config.yml")
@Header("## Dream-Sprawdzanie (Main-Config) ##")
@Header("Jedyna permisja obslugiwana przez ten plugin to \"dreamspr.admin\"")
@Header("Daje dostep do komend: /sprawdz, /czysty")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {

    @Comment("Czy uzyc komendy z logout-command po przyznaniu sie do cheatow przez danego gracza?")
    public boolean sendLogoutCommand = true;

    @Comment("Czy uzyc komendy z admit-command po przyznaniu sie do cheatow przez danego gracza?")
    public boolean sendAdmitCommand = true;

    @Comment("Czy wysylac wiadomosc o wylogowaniu sie gracza podczas sprawdzania kazdemu graczowi na serwerze?")
    @Comment("Jezeli false ta wiadomosc otrzymaja tylko gracze z permisja dreamspr.admin")
    public boolean broadcastLogoutMessage = false;

    @Comment("Czy wysylac wiadomosc o oczyszczeniu gracza kazdemu graczowi na serwerze?")
    @Comment("Jezeli false ta wiadomosc otrzymaja tylko gracze z permisja dreamspr.admin")
    public boolean broadcastClearMessage = false;

    @Comment("Czy wysylac wiadomosc o przyznaniu sie gracza kazdemu graczowi na serwerze?")
    @Comment("Jezeli false ta wiadomosc otrzymaja tylko gracze z permisja dreamspr.admin")
    public boolean broadcastAdmitMessage = false;

    @Comment("Czy wysylac wiadomosc o sprawdzaniu gracza kazdemu graczowi na serwerze?")
    @Comment("Jezeli false ta wiadomosc otrzymaja tylko gracze z permisja dreamspr.admin")
    public boolean broadcastCheckMessage = false;

    @Comment("Czy wysylac title dla gracza ktory jest sprawdzany?")
    public boolean sendTitle = true;

    @Comment("Czy sprawdzany gracz ma miec mozliwosc ruszania sie?")
    public boolean moveIfBeingChecked = false;

    @Comment("Czy sprawdzany gracz moze ruszac myszka?")
    public boolean mouseMovementIfBeingChecked = true;

    @Comment("Komenda ktora serwer wykona, gdy gracz opusci serwer podczas sprawdzania (bez /)")
    public String logoutCommand = "ban {gracz} Wylogowanie sie podczas sprawdzania";

    @Comment("Komenda ktora serwer wykona, gdy gracz opusci serwer podczas sprawdzania (bez /)")
    public String admitCommand = "ban {gracz} &cPrzyznanie sie do cheatow";

    @Comment("Czy blokowac uzywanie komend przez sprawdzanego gracza?")
    public boolean blockCommands = true;

    @Comment("Lista komend, ktorych moze uzywac sprawdzany gracz (bez /) (dziala tylko gdy block-commands jest true")
    public List<String> commandList = new ImmutableList.Builder<String>()
            .add("przyznajesie")
            .build();

    @Comment("Czy dodawac efekty gdy gracz jest sprawdzany?")
    public boolean addEffects = true;

    @Comment("Czy usuwac aktywne efekty gracza przed sprawdzaniem?")
    public boolean clearEffectBefore = true;

    @Comment("Lista efektow ktore plugin nada graczowi, gdy jest sprawdzany")
    @Comment("Jezeli nie chcesz nadawac efektow zamien te liste w {}")
    @Comment("Czas ustawiony na 999999 oznacza brak okreslonego czasu efektu (zostanie usuniety w przypadku przyznania sie lub oczyszczenia)")
    public List<PotionEffect> effectsToAdd = new ImmutableList.Builder<PotionEffect>()
            .add(new PotionEffect(PotionEffectType.CONFUSION, 999999, 1, true, false))
            .build();

    @Comment("Czy gdy gracz jest sprawdzany powinien byc teleportowany do checkroomu?")
    public boolean useCheckroom = true;

    @Comment("Lokalizacja checkroomu")
    @Comment("Wartosc yaw i pitch okreslaja kierunek w ktorym po teleportacji skierowana bedzie glowa gracza")
    @Comment("Znajdziesz je pod f3 po lewej stronie zatytuowane Facing w nawiasie pod koniec linijki")
    @Comment("Przyklad: https://cdn.discordapp.com/attachments/925006637893320737/1033031915399303259/instrukcja_facing.png")
    public String world = "world";
    public double x = 0.5;
    public double y = 63;
    public double z = 0.5;
    public float yaw = 0.0f;
    public float pitch = 0.0f;

    @Comment("Czy teleportowac gracza spowrotem na miejsce z ktorego zostal przeteleportowany do checkroomu, gdy okaze sie czysty?")
    public boolean tpBack = true;

    @Comment("Czy sprawdzany gracz moze otrzymywac obrazenia?")
    public boolean damage = false;

    @Comment("Czy sprawdzany gracz moze niszczyc bloki?")
    public boolean breakBlocks = false;

    @Comment("Czy sprawdzany gracz moze stawiac bloki?")
    public boolean placeBlocks = false;
}
