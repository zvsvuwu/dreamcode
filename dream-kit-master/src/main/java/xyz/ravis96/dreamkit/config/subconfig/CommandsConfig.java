package xyz.ravis96.dreamkit.config.subconfig;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class CommandsConfig extends OkaeriConfig {

    @Comment("Mozesz zmienic ustawienia domyslne komend.")
    public FunnyCommand kitCommand = new FunnyCommand("kit",
            Arrays.asList("kits", "zestaw", "zestawy"),
            "Komenda do zestawow dla graczy.");
    public FunnyCommand kitManageCommand = new FunnyCommand("kitmanage",
            "Komenda do modyfikowania dostepnosci kitow.");

    @Getter
    @RequiredArgsConstructor
    public static class FunnyCommand {

        private final String name;
        private final List<String> aliases;
        private final String description;
        private final boolean enabled;

        public FunnyCommand(String name) {
            this.name = name;
            this.aliases = Collections.emptyList();
            this.description = "";
            this.enabled = true;
        }

        public FunnyCommand(String name, List<String> aliases) {
            this.name = name;
            this.aliases = aliases;
            this.description = "";
            this.enabled = true;
        }

        public FunnyCommand(String name, String description) {
            this.name = name;
            this.aliases = Collections.emptyList();
            this.description = description;
            this.enabled = true;
        }

        public FunnyCommand(String name, List<String> aliases, String description) {
            this.name = name;
            this.aliases = aliases;
            this.description = description;
            this.enabled = true;
        }

    }
}
