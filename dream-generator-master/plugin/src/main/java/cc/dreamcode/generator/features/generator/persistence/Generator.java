package cc.dreamcode.generator.features.generator.persistence;

import eu.okaeri.persistence.document.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class Generator extends Document {

    private Location location;
    private int regenerationSpeed;

    public UUID getUniqueId() {
        return this.getPath().toUUID();
    }

}
