package cc.dreamcode.restarts.features.user;

import cc.dreamcode.restarts.persistence.document.AbstractDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class User extends AbstractDocument {

    private String name; // nick of this user

    private String value = "default values -- add data";

    public UUID getUniqueId() {
        return this.getPath().toUUID();
    }

}
