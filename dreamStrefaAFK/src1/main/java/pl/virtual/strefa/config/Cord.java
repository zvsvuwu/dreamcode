package pl.virtual.strefa.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class Cord extends OkaeriConfig {
    public final int firstX;
    public final int firstZ;
    public final int firstY;
    public final int secondX;
    public final int secondZ;
    public final int secondY;
}
