package cc.dreamcode.pickaxe.user;

import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import eu.okaeri.persistence.document.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bukkit.Location;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class User extends Document {

    private String name;

    private String regionName;

    private Location firstCorner, secondCorner;

    public UUID getUniqueId() {
        return this.getPath().toUUID();
    }

    public boolean isRegionReady() {
        return firstCorner != null && secondCorner != null;
    }
}
