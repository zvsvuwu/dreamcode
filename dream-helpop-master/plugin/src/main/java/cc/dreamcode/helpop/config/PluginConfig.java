package cc.dreamcode.helpop.config;

import cc.dreamcode.helpop.config.annotations.Configuration;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import net.dv8tion.jda.api.entities.Activity;

@Configuration(child = "config.yml")
@Header("## Dream-Helpop (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {

    @Comment("Czy uzywac bota discord do logowania zgloszen?")
    public boolean useBot = true;

    @Comment("Token bota discord")
    public String botToken = "token";

    @Comment("ID kanalu na ktorym beda logowane wiadomosci")
    public long channelId = 9999999999L;

    @Comment("Typ aktywnosci bota (dostepne: PLAYING, WATCHING, COMPETING, LISTENING)")
    public Activity.ActivityType activityType = Activity.ActivityType.PLAYING;

    @Comment("Wiadomosc statusu bota")
    public String status = "twojserwer.pl";

    @Comment("Konfiguracja panelu embed")
    @Comment("Czy uzywac embeda do panelu (jezeli false wyslana bedzie zwykla wiadomosc)")
    public boolean useEmbed = true;
    public String embedTitle = "Helpop";
    public String embedDescription = "Gracz: **{gracz}**" +
            "\nWiadomosc: *{tresc}*";
    public int embedRed = 0;
    public int embedGreen = 255;
    public int embedBlue = 255;
    public boolean useImage = false;
    public String imageUrl = "https://media.discordapp.net/attachments/1026789136616669194/1031662022145884190/COS_LOGO123x1.png";
    public boolean useThumbnail = true;
    public String thumbnailUrl = "https://media.discordapp.net/attachments/1026789136616669194/1031662022145884190/COS_LOGO123x1.png";
    public boolean useFooter = true;
    public boolean useFooterText = true;
    public String footerText = "twojserwer.pl";
    public boolean useFooterIcon = true;
    public String footerIconUrl = "https://media.discordapp.net/attachments/1026789136616669194/1031662022145884190/COS_LOGO123x1.png";

    @Comment("Wiadomosc wysylana w przypadku wylaczenia wiadomosci embed")
    public String helpopMessage = "Helpop" +
            "\nGracz: **{gracz}**" +
            "\nWiadomosc: *{tresc}*";

    @Comment("Cooldown na wysylanie komendy helpop (w sekundach)")
    public int helpopCooldown = 60;
}
