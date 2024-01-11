package xyz.ravis96.dreamfreeze.config.subconfig;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import xyz.ravis96.dreamfreeze.helpers.TitleBuilder;

import java.util.Arrays;
import java.util.List;

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class FreezeConfig extends OkaeriConfig {

    @Comment("Podaj liste komend dozwolonych podczas freeze.")
    public List<String> legalCommandList = Arrays.asList("helpop", "pomoc");

    @Comment("Podaj, jak ma wygladac title podczas freeze'a?")
    public TitleBuilder freezeTitle = new TitleBuilder("&8[&9FREEZE&8]", "&7Caly serwer zostal zamrozony!", 5, 10, 5);
}
