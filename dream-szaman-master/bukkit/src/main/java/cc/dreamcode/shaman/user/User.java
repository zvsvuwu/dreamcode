package cc.dreamcode.shaman.user;

import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import eu.okaeri.persistence.document.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class User extends Document {

    private String name;
    private int healthLvl;
    private int speedLvl;
    private int damageLvl;
    private int lifeStealLvl;
    private int fallLvl;

    public UUID getUniqueId() {
        return this.getPath().toUUID();
    }

}
