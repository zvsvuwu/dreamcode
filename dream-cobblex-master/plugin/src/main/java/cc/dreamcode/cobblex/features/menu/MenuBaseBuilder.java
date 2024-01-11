package cc.dreamcode.cobblex.features.menu;

import lombok.NonNull;
import org.bukkit.entity.Player;

public interface MenuBaseBuilder {

    MenuBase buildMenu(@NonNull Player player);

}
