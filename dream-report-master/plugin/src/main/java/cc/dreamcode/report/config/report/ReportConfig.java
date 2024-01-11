package cc.dreamcode.report.config.report;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

import java.time.Duration;

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class ReportConfig extends OkaeriConfig {

    @Comment("Jaki ma byc cooldown na wysylanie wiadomosci?")
    public Duration coolDownDuration = Duration.ofMinutes(3);
}
