package cc.dreamcode.home.menu;

import cc.dreamcode.home.HomePlugin;
import cc.dreamcode.home.config.MessageConfig;
import cc.dreamcode.home.config.PluginConfig;
import cc.dreamcode.home.manager.HomeManager;
import cc.dreamcode.home.manager.UserManager;
import cc.dreamcode.home.rawlocation.RawLocation;
import cc.dreamcode.home.user.User;
import cc.dreamcode.menu.bukkit.base.BukkitMenu;
import cc.dreamcode.menu.bukkit.setup.BukkitMenuPlayerSetup;
import cc.dreamcode.menu.serdes.bukkit.BukkitMenuBuilder;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import cc.dreamcode.utilities.optional.CustomOptional;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.ClickType;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

public class HomeMenu implements BukkitMenuPlayerSetup {
    private @Inject HomePlugin homePlugin;
    private @Inject PluginConfig pluginConfig;
    private @Inject MessageConfig messageConfig;
    private @Inject HomeManager homeManager;
    private @Inject UserManager userManager;

    @Setter User user;

    @Override
    public BukkitMenu build(@NonNull HumanEntity humanEntity) {
        BukkitMenuBuilder menuBuilder = this.pluginConfig.homeMenuBuilder;
        BukkitMenu bukkitMenu = menuBuilder.build();

        AtomicInteger count = new AtomicInteger(1);

        menuBuilder.getItems().forEach((integer, itemStack) -> {
            CustomOptional.ofNullable(this.pluginConfig.homeLimits.get(integer)).ifPresentOrElse(permission -> {
                bukkitMenu.setItem(
                        integer,
                        new ItemBuilder(humanEntity.hasPermission(permission)
                                ? this.pluginConfig.availableHome
                                : this.pluginConfig.notAvailableHome)
                        .fixColors(Collections.singletonMap("number", count.get()))
                        .toItemStack(),
                        event -> {
                            if (!humanEntity.hasPermission(permission)) return;
                            if (event.getClick() == ClickType.LEFT) {
                                if (!this.user.getHomes().containsKey(integer)) {
                                    humanEntity.closeInventory();
                                    this.messageConfig.homeNotSet.send(humanEntity);
                                    return;
                                }

                                this.pluginConfig.teleportationTime.entrySet()
                                        .stream()
                                        .filter(stringIntegerEntry -> humanEntity.hasPermission(stringIntegerEntry.getKey()))
                                        .findFirst()
                                        .ifPresent(stringIntegerEntry -> {
                                            this.homeManager.getTimeMap().put(
                                                    humanEntity.getUniqueId(),
                                                    stringIntegerEntry.getValue()
                                            );
                                        });

                                this.homeManager.getLocationMap().put(
                                        humanEntity.getUniqueId(),
                                        this.user.getHomes().get(integer).toLocation()
                                );

                                humanEntity.closeInventory();
                            }
                            if (event.getClick() == ClickType.RIGHT) {
                                this.user.getHomes().put(integer, new RawLocation(humanEntity.getLocation()));
                                this.homePlugin.runAsync(this.user::save);

                                this.userManager.getUserMap().put(humanEntity.getUniqueId(), this.user);
                                humanEntity.closeInventory();
                                this.messageConfig.homeSetSuccess.send(humanEntity);
                            }
                        });

                count.getAndIncrement();
            }, () -> bukkitMenu.setItem(integer, itemStack));
        });

        return bukkitMenu;
    }
}
