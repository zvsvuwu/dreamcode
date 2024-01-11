package cc.dreamcode.nagroda.content;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class NagrodaConfig extends OkaeriConfig {

    @Comment("Token bota discord")
    public String botToken = "token";

    @Comment("Id kanalu na ktory wysylac panel")
    @Comment("Pamietaj jednak, aby kanal ten byl przeznaczony tylko i wylacznie na nagrode")
    @Comment("Przy kazdym starcie bota kanal ten bedzie czyszczony i bot bedzie wysylal panel")
    public long channelId = 99999999999L;

    @Comment("Typ aktywnosci bota (dostepne: PLAYING, WATCHING, COMPETING, LISTENING, CUSTOM_STATUS")
    public Activity.ActivityType activityType = Activity.ActivityType.PLAYING;

    @Comment("Wiadomosc statusu bota")
    public String status = "twojserwer.pl";

    @Comment("Komenda jaka plugin wykona po tym jak gracz odbierze nagrode")
    public String command = "dajnagrode {gracz}";

    @Comment("Czy wysylac dla kazdego gracza online informacje o tym ze ktos odebral nagrode?")
    public boolean broadcastReward = true;

    @Comment("Wiadomosc wysylana w panelu (dziala tylko gdy use-embed jest false)")
    public String panelMessage = "Wpisz na serwerze komende **/nagroda**, aby otrzymac kod." +
            "\nNastepnie wcisnij przycisk pod ta wiadomoscia i wpisz swoj kod!";

    @Comment("Typ przycisku (dostepne: PRIMARY, SUCCESS, SECONDARY, DANGER")
    public ButtonStyle buttonStyle = ButtonStyle.SUCCESS;
    @Comment("Wiadomosc na przycisku")
    public String buttonLabel = "Odbierz nagrode!";

    @Comment("Konfiguracja panelu embed")
    @Comment("Czy uzywac embeda do panelu (jezeli false wyslana bedzie zwykla wiadomosc, a z nia przycisk)")
    public boolean useEmbed = true;
    public String embedTitle = "Odbierz Nagrode";
    public String embedDescription = "Wpisz na serwerze komende **/nagroda**, aby otrzymac kod." +
            "\nPotem wcisnij przycisk pod ta wiadomoscia i wpisz swoj kod";
    public int embedRed = 0;
    public int embedGreen = 255;
    public int embedBlue = 255;
    public boolean useImage = true;
    public String imageUrl = "https://media.discordapp.net/attachments/1026789136616669194/1031662022145884190/COS_LOGO123x1.png";
    public boolean useThumbnail = true;
    public String thumbnailUrl = "https://media.discordapp.net/attachments/1026789136616669194/1031662022145884190/COS_LOGO123x1.png";
    public boolean useFooter = true;
    public boolean useFooterText = true;
    public String footerText = "twojserwer.pl";
    public boolean useFooterIcon = true;
    public String footerIconUrl = "https://media.discordapp.net/attachments/1026789136616669194/1031662022145884190/COS_LOGO123x1.png";

    @Comment("Konfiguracja modulu")
    public String moduleTitle = "Odbierz nagrodę";
    public String moduleInputName = "Twój wygenerowany kod";
    public String modulePlaceholder = "Np. 123";
}
