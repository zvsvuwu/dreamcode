package cc.dreamcode.home.user;

import cc.dreamcode.home.rawlocation.RawLocation;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import eu.okaeri.persistence.document.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class User extends Document {

    private String name;

    private Map<Integer, RawLocation> homes = new HashMap<>();

    public UUID getUniqueId() {
        return this.getPath().toUUID();
    }

}
