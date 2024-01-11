package cc.dreamcode.ffa.user;

import cc.dreamcode.ffa.user.combat.UserCombat;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import eu.okaeri.persistence.document.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public final class User extends Document {

    private String name;
    private UserStatistics statistics;
    private List<ItemStack> inventory;
    private List<ItemStack> armor;

    private transient UserCombat combat;

    public UUID getUniqueId() {
        return this.getPath().toUUID();
    }
}
