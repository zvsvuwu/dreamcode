package cc.dreamcode.dropsmp.features.menu;

import lombok.NonNull;
import org.bukkit.entity.Player;

public interface MenuBaseBuilder {

    MenuBase buildMenu(@NonNull Player player);

}
