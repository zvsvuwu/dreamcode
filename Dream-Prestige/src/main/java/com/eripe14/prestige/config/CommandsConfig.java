package com.eripe14.prestige.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import xyz.ravis96.dreambasis.bukkit.config.objects.FunnyCommand;

import java.util.Collections;

@Header("## DreamCode-Prestiże (Commands-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class CommandsConfig extends OkaeriConfig {

    @Comment("Możesz zmienić ustawienia domyślne komend.")
    public FunnyCommand prestigeCommand = new FunnyCommand("prestige",
            Collections.singletonList("prestiz"),
            "Komenda do prestizy.");
}
