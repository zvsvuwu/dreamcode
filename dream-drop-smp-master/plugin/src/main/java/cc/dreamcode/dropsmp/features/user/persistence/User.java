package cc.dreamcode.dropsmp.features.user.persistence;

import eu.okaeri.persistence.document.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class User extends Document {

    private String name;
    private double strength;
    private double speed;
    private double resistance;

    public UUID getUniqueId() {
        return this.getPath().toUUID();
    }

    public Optional<Player> getPlayer() {
        return Optional.ofNullable(Bukkit.getPlayer(this.getUniqueId()));
    }
}
