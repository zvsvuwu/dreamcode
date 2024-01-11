package com.eripe14.prestige.config.subconfig;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PrestigeConfig extends OkaeriConfig {

    @Comment("[#] Od jakiego rankingu, można zdobyć nowy prestiż.")
    public int requiredRanking = 2000;

    @Comment("[#] Na jaki ranking ma ustawiać, po odebraniu prestiżu.")
    public int rankingAfterReceivingPrestige = 500;

    @Comment("[#] Po jakim czasie ma wygasać możliwość odebrania prestiżu.")
    public String expireIn = "30s";

}
