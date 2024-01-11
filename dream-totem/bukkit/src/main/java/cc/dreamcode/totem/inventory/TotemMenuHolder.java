package cc.dreamcode.totem.inventory;

import cc.dreamcode.menu.bukkit.base.BukkitMenu;
import cc.dreamcode.totem.TotemPlugin;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.entity.HumanEntity;

public class TotemMenuHolder {
    @Inject private TotemPlugin totemPlugin;
    private BukkitMenu totemMenu;

    public void update() {
        TotemMenuSetup totemMenuSetup = this.totemPlugin.createInstance(TotemMenuSetup.class);
        this.totemMenu = totemMenuSetup.setup();
    }

    public void open(@NonNull HumanEntity humanEntity) {
        if (this.totemMenu == null) this.update();

        this.totemMenu.open(humanEntity);
    }
}
