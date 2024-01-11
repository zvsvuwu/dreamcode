package pl.virtual.death.manager;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class User {

    private final UUID uuid;
    private final String name;

    private ItemStack[] inventory;
}


