package cc.dreamcode.enderchest.menu;

import cc.dreamcode.enderchest.EnderchestPlugin;
import cc.dreamcode.enderchest.config.MessageConfig;
import cc.dreamcode.enderchest.config.PluginConfig;
import cc.dreamcode.enderchest.ec.EnderchestType;
import cc.dreamcode.enderchest.ec.EnderchestViewer;
import cc.dreamcode.enderchest.user.User;
import cc.dreamcode.menu.bukkit.BukkitMenuBuilder;
import cc.dreamcode.menu.bukkit.BukkitMenuProvider;
import cc.dreamcode.menu.bukkit.base.BukkitMenu;
import cc.dreamcode.menu.bukkit.setup.BukkitMenuPlayerSetup;
import eu.okaeri.injector.annotation.Inject;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EnderchestMenu implements BukkitMenuPlayerSetup {

    private @Inject EnderchestPlugin enderchestPlugin;
    private @Inject PluginConfig pluginConfig;
    private @Inject BukkitMenuProvider bukkitMenuProvider;
    private @Inject MessageConfig messageConfig;
    private final @Getter Map<UUID, EnderchestViewer> enderchestViewerMap = new HashMap<>();

    @Override
    public BukkitMenu build(@NonNull HumanEntity humanEntity) {
        return null;
    }

    public BukkitMenu build(@NonNull HumanEntity humanEntity, User user, int ecSlot) {
        BukkitMenuBuilder bukkitMenuBuilder = new BukkitMenuBuilder(
                this.pluginConfig.enderchestMenu.getName() + " " + ecSlot,
                this.pluginConfig.enderchestMenu.getRows(),
                this.pluginConfig.enderchestMenu.isDisabledActions(),
                this.pluginConfig.enderchestMenu.isDisposeWhenClose(),
                this.pluginConfig.enderchestMenu.getItems()
        );
        BukkitMenu bukkitMenu = bukkitMenuBuilder.buildEmpty();

        ItemStack[] items = user.getItems().getOrDefault(ecSlot, new ItemStack[0]);
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null || items[i].getType().equals(Material.AIR)) {
                continue;
            }
            bukkitMenu.setItem(i, items[i]);
        }
        bukkitMenuBuilder.getItems().forEach((bukkitMenu::setItem));
        bukkitMenu.setItem(this.pluginConfig.previousSlot, bukkitMenuBuilder.getItems().get(this.pluginConfig.previousSlot), event -> {
            event.setCancelled(true);
            if (ecSlot == 1) {
                return;
            }
            EnderchestType enderchestType = this.pluginConfig.enderchestTypes.get(ecSlot - 1);
            if (enderchestType.getPermission() != null && !humanEntity.hasPermission(enderchestType.getPermission())) {
                this.messageConfig.enderchestPermission.send(humanEntity);
                return;
            }
            humanEntity.closeInventory();
            BukkitMenu prevoius = build(humanEntity, user, ecSlot - 1);
            prevoius.open(humanEntity);
        });
        bukkitMenu.setItem(this.pluginConfig.nextSlot, bukkitMenuBuilder.getItems().get(this.pluginConfig.nextSlot), event -> {
            event.setCancelled(true);
            if (ecSlot == this.pluginConfig.enderchestTypes.size()) {
                return;
            }
            EnderchestType enderchestType = this.pluginConfig.enderchestTypes.get(ecSlot);
            if (enderchestType.getPermission() != null && !humanEntity.hasPermission(enderchestType.getPermission())) {
                this.messageConfig.enderchestPermission.send(humanEntity);
                return;
            }
            humanEntity.closeInventory();
            BukkitMenu next = build(humanEntity, user, ecSlot + 1);
            next.open(humanEntity);
        });
        getEnderchestViewerMap().put(user.getUniqueId(), new EnderchestViewer(ecSlot, bukkitMenu.getInventory().getHolder()));
        return bukkitMenu;
    }
}
