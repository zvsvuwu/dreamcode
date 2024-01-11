package cc.dreamcode.tops.user.top;

import cc.dreamcode.menu.bukkit.base.BukkitMenu;
import cc.dreamcode.tops.BukkitTopsPlugin;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.entity.HumanEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TopManager {

    private @Inject BukkitTopsPlugin bukkitTopsPlugin;
    private @Inject TopCalculator userTopCalculator;

    private List<TopCalculator.UserTop> userTopList = new ArrayList<>();
    private BukkitMenu bukkitMenu;

    public void update() {
        this.userTopList.clear();

        Arrays.stream(UserData.values())
                .filter(UserData::isShouldCountTop)
                .map(userData -> this.userTopCalculator.getTopList(userData))
                .forEach(topList -> this.userTopList.addAll(topList));
    }

    public void updateMenu(HumanEntity entity) {
        TopMenu userTopMenu = this.bukkitTopsPlugin.createInstance(TopMenu.class);
        this.bukkitMenu = userTopMenu.build(entity);
    }

    public void openMenu(HumanEntity entity) {
        if (this.bukkitMenu == null) {
            this.updateMenu(entity);
        }

        this.bukkitMenu.open(entity);
    }

}
