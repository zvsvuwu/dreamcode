package xyz.ravis96.dreamkit.features.user;

import eu.okaeri.persistence.document.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.ravis96.dreamkit.features.kit.Kit;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class User extends Document {

    private String name;

    private Map<Kit, Long> kitCoolDownMap = Collections.synchronizedMap(new HashMap<>());

    public UUID getUniqueId() {
        return this.getPath().toUUID();
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.getUniqueId());
    }

}
