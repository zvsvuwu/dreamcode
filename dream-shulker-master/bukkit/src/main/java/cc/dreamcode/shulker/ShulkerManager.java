package cc.dreamcode.shulker;

import cc.dreamcode.menu.bukkit.base.BukkitMenu;
import lombok.NonNull;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class ShulkerManager {

    private final Map<UUID, Shulker> shulkers = new HashMap<>();

    public boolean hasOpenedShulker(@NonNull HumanEntity humanEntity) {
        return this.shulkers.containsKey(humanEntity.getUniqueId());
    }

    public Optional<Shulker> getShulkerMenu(@NonNull HumanEntity humanEntity) {
        return Optional.ofNullable(this.shulkers.get(humanEntity.getUniqueId()));
    }

    public Optional<Shulker> getShulkerMenu(@NonNull Inventory inventory) {
        return this.shulkers.values()
                .stream()
                .filter(shulker -> shulker.getShulkerMenu().getHolder() != null &&
                        Objects.equals(inventory.getHolder(), shulker.getShulkerMenu().getHolder()))
                .findAny();
    }

    public void setOpenedShulker(@NonNull HumanEntity humanEntity, @NonNull BukkitMenu bukkitMenu, @NonNull ItemStack shulkerItem) {
        this.shulkers.put(humanEntity.getUniqueId(), new Shulker(humanEntity.getUniqueId(), bukkitMenu, shulkerItem));
    }

    public void removeOpenedShulker(@NonNull HumanEntity humanEntity) {
        this.shulkers.remove(humanEntity.getUniqueId());
    }

}
