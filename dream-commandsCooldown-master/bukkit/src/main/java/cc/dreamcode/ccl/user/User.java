package cc.dreamcode.ccl.user;

import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import eu.okaeri.persistence.document.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = false)
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class User extends Document {

    private Map<String, Long> cool_down;
    private long globalCool_down;

    public void invalidate() {
        cool_down = cool_down.entrySet()
                .stream()
                .filter(stringLongEntry -> stringLongEntry.getValue() > System.currentTimeMillis())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }

    public UUID getUniqueId() {
        return this.getPath().toUUID();
    }

}
