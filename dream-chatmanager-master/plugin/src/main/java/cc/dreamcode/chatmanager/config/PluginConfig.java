package cc.dreamcode.chatmanager.config;

import cc.dreamcode.chatmanager.config.annotations.Configuration;
import com.google.common.collect.ImmutableList;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

import java.util.List;

@Configuration(child = "config.yml")
@Header("## Dream-ChatManager (Main-Config) ##")
@Header("Permisja do bypassowania blokady to: \"dreamchatmanager.bypass\"")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {

    @Comment("Czy blokowac slowa z listy zabronionych slow ponizej?")
    public boolean blockCurseWords = true;

    @Comment("Lista zabronionych slow na chacie (moze sie przydac lista polskich przeklenstw: http://marcinmazurek.com.pl/polskie-wulgaryzmy)")
    public List<String> curseWordsList = new ImmutableList.Builder<String>()
            .add("bardzo")
            .add("brzydkie")
            .add("slowo")
            .build();

    @Comment("Czy wysylac wiadomosc graczowi gdy dolaczy na serwer?")
    public boolean sendMotd = true;

    @Comment("Czy blokowac wysylanie linkow na chacie?")
    public boolean blockLinks = true;

    @Comment("Czy wysylac powiadomienie do graczy z permisja \"dreamcm.admin\"")
    public boolean sendAdminNotice = true;

    @Comment("Cooldown na pisanie na chacie (w sekundach)")
    public int chatCooldown = 2;
}
