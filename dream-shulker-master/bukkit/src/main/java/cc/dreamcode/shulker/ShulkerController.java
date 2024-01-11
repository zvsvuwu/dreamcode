package cc.dreamcode.shulker;

import cc.dreamcode.menu.bukkit.BukkitMenuProvider;
import cc.dreamcode.menu.bukkit.base.BukkitMenu;
import cc.dreamcode.shulker.config.MessageConfig;
import cc.dreamcode.shulker.config.PluginConfig;
import cc.dreamcode.shulker.cooldown.Cooldown;
import cc.dreamcode.shulker.cooldown.CooldownManager;
import cc.dreamcode.shulker.cooldown.CooldownType;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.ChatUtil;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class ShulkerController implements Listener {

    private @Inject PluginConfig pluginConfig;
    private @Inject MessageConfig messageConfig;
    private @Inject BukkitMenuProvider bukkitMenuProvider;
    private @Inject ShulkerManager shulkerManager;
    private @Inject CooldownManager cooldownManager;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        final Player player = e.getPlayer();
        final ItemStack shulkerBoxItem = e.getItem();

        if (!e.getAction().equals(Action.RIGHT_CLICK_AIR) &&
                !e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }

        if (shulkerBoxItem == null ||
                !shulkerBoxItem.getType().name().contains("SHULKER") ||
                !(shulkerBoxItem.getItemMeta() instanceof BlockStateMeta)) {
            return;
        }

        final BlockStateMeta blockStateMeta = (BlockStateMeta) shulkerBoxItem.getItemMeta();
        if (!(blockStateMeta.getBlockState() instanceof ShulkerBox)) {
            return;
        }

        e.setCancelled(true);

        if (!this.pluginConfig.openShulkerWithOffHand && !e.getHand().equals(EquipmentSlot.HAND)) {
            this.messageConfig.shulkerInOffHand.send(player);
            return;
        }

        if (this.shulkerManager.hasOpenedShulker(player)) {
            this.messageConfig.hasOpenedShulker.send(player);
            return;
        }

        if (!player.hasPermission(this.pluginConfig.shulkerBypassPermission)) {
            final Optional<Cooldown> optionalCooldown = this.cooldownManager.getCooldown(
                    player.getUniqueId(),
                    CooldownType.SHULKER
            );

            if (optionalCooldown.isPresent()) {
                final Cooldown cooldown = optionalCooldown.get();

                this.messageConfig.shulkerCooldown.send(player, new MapBuilder<String, Object>()
                        .put("time", cooldown.getFormattedTime())
                        .build());
                return;
            }

            this.cooldownManager.addCooldown(player.getUniqueId(), CooldownType.SHULKER, this.pluginConfig.shulkerCooldown);
        }

        final ShulkerBox shulkerBox = (ShulkerBox) blockStateMeta.getBlockState();
        final BukkitMenu bukkitMenu = this.bukkitMenuProvider.createDefault(
                ChatUtil.fixColor(this.pluginConfig.shulkerName),
                3,
                false,
                true
        );

        final AtomicInteger atomicIndex = new AtomicInteger();
        Arrays.stream(shulkerBox.getInventory().getContents()).forEach(itemStack -> {
            int index = atomicIndex.getAndIncrement();

            if (itemStack == null) {
                return;
            }

            bukkitMenu.setItem(index, itemStack);
        });

        this.shulkerManager.setOpenedShulker(player, bukkitMenu, shulkerBoxItem);
        bukkitMenu.open(player);

        this.messageConfig.openShulker.send(player);
    }

    @EventHandler
    public void onPlayerItemSwapHand(PlayerSwapHandItemsEvent e) {
        final Player player = e.getPlayer();
        if (this.pluginConfig.holdInOffHand) {
            final Optional<Shulker> optionalBukkitMenu = this.shulkerManager.getShulkerMenu(player);

            if (!optionalBukkitMenu.isPresent()) {
                return;
            }
        }

        if (e.getMainHandItem() != null &&
                e.getMainHandItem().getType().name().contains("SHULKER")) {
            e.setCancelled(true);
            return;
        }

        if (e.getOffHandItem() != null &&
                e.getOffHandItem().getType().name().contains("SHULKER")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        final HumanEntity player = e.getWhoClicked();
        final Optional<Shulker> optionalBukkitMenu = this.shulkerManager.getShulkerMenu(player);

        if (!optionalBukkitMenu.isPresent()) {
            return;
        }

        if (e.getAction().equals(InventoryAction.HOTBAR_SWAP)) {
            e.setCancelled(true);
            return;
        }

        if (e.getCursor() != null &&
                e.getCursor().getType().name().contains("SHULKER")) {
            e.setCancelled(true);
            return;
        }

        if (e.getCurrentItem() != null &&
                e.getCurrentItem().getType().name().contains("SHULKER")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryMoveItem(InventoryMoveItemEvent e) {
        final HumanEntity player = (HumanEntity) e.getSource().getHolder();
        final Optional<Shulker> optionalBukkitMenu = this.shulkerManager.getShulkerMenu(player);

        if (!optionalBukkitMenu.isPresent()) {
            return;
        }

        if (e.getItem() != null &&
                e.getItem().getType().name().contains("SHULKER")) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryClose(InventoryCloseEvent e) {
        final HumanEntity player = e.getPlayer();
        final Optional<Shulker> optionalBukkitMenu = this.shulkerManager.getShulkerMenu(player);

        if (!optionalBukkitMenu.isPresent()) {
            return;
        }

        final Inventory inventory = e.getInventory();
        final Shulker shulker = optionalBukkitMenu.get();
        if (!Objects.equals(inventory.getHolder(), shulker.getShulkerMenu().getHolder())) {
            return;
        }

        final BlockStateMeta blockStateMeta = (BlockStateMeta) shulker.getShulkerItem().getItemMeta();
        if (!(blockStateMeta.getBlockState() instanceof ShulkerBox)) {
            return;
        }

        final ShulkerBox shulkerBox = (ShulkerBox) blockStateMeta.getBlockState();
        for (int index = 0; index < inventory.getSize(); index++) {
            ItemStack itemStack = inventory.getItem(index);
            shulkerBox.getInventory().setItem(index, itemStack);
        }

        blockStateMeta.setBlockState(shulkerBox);
        shulker.getShulkerItem().setItemMeta(blockStateMeta);

        this.shulkerManager.removeOpenedShulker(player);

        this.messageConfig.closeShulker.send(player);
    }
}
